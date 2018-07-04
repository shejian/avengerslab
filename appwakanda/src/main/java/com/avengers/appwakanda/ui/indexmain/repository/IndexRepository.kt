package com.avengers.appwakanda.ui.indexmain.repository

import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.util.Log
import com.avengers.appwakanda.WakandaModule
import com.avengers.appwakanda.db.room.RoomHelper
import com.avengers.appwakanda.db.room.dao.IndexDataCache
import com.avengers.appwakanda.ui.indexmain.data.ReaderListBoundaryCallback
import com.avengers.appwakanda.ui.indexmain.vm.ItemResult
import com.avengers.appwakanda.webapi.Api
import com.avengers.appwakanda.webapi.SmartisanService

/**
 * 处理数据逻辑
 */
class IndexRepository(
        private val service: SmartisanService,
        private val cache: IndexDataCache
) {
    companion object {
        private const val DB_PAGE_SIZE = 20
    }


    fun getIndexListData(query: String): ItemResult {
        //设置边界回调
        val callback = ReaderListBoundaryCallback(query, service, cache)
        //缓存数据工厂类
        val dataSourceFactory = cache.queryIndexList()

        val networkErrors = callback.networkErrors

        val mLiveData = LivePagedListBuilder(dataSourceFactory,
                PagedList.Config.Builder()
                        .setPageSize(DB_PAGE_SIZE)
                        .setEnablePlaceholders(false)
                        .setPrefetchDistance(2)
                        .build())
                .setBoundaryCallback(callback)
                .build()

        //数据封装
        return ItemResult(mLiveData, networkErrors)
    }

    private var lastRequestedPage = 0
    private var isRequestInProgress = false
    fun refresh(query: String) {
        if (isRequestInProgress) {
            return
        }
        isRequestInProgress = true
        service.indexMainData(Api.getSmartApi(), query, ReaderListBoundaryCallback.NETWORK_PAGE_SIZE, lastRequestedPage, {
            WakandaModule.appExecutors!!.diskIO()?.execute {
                it.let {
                    RoomHelper.getWakandaDb().runInTransaction {
                        val lastIndex = cache.queryMaxIndex().toLong()
                        var newlist = it.mapIndexed { index, contextItemEntity ->
                            contextItemEntity.setMid(lastIndex + index)
                            contextItemEntity
                        }
                        cache.cleanData {
                            lastRequestedPage = 0
                            cache.insert(newlist) {
                                lastRequestedPage++
                                isRequestInProgress = false
                            }
                        }
                    }
                }
            }


        }, {
            Log.d("shejian", "失败" + it)
            isRequestInProgress = false
        })
    }


}