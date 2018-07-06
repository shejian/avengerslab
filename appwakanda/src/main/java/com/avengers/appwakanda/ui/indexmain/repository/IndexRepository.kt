package com.avengers.appwakanda.ui.indexmain.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
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
                        .setEnablePlaceholders(true)
                        .setPrefetchDistance(2)
                        .build())
                .setBoundaryCallback(callback)
                .build()

        var netWorkFun = MutableLiveData<Unit>()

        var newworkState = Transformations.switchMap(netWorkFun) {
            refresh(query)
        }

        //数据封装
        return ItemResult(mLiveData, networkErrors, newworkState) {
            netWorkFun.value = null
        }
    }

    private var lastRequestedPage = 0
    private var isRequestInProgress = false
    private fun refresh(query: String): LiveData<String> {
        val networkState = MutableLiveData<String>()
        if (isRequestInProgress) {
            return networkState
        }
        isRequestInProgress = true
        service.indexMainData(Api.getSmartApi(), query, ReaderListBoundaryCallback.NETWORK_PAGE_SIZE, lastRequestedPage, {
            WakandaModule.appExecutors!!.diskIO()?.execute {
                it.let {
                    RoomHelper.getWakandaDb().runInTransaction {
                        if (it.isNotEmpty()) {
                            cache.cleanData {
                                val lastIndex = cache.queryMaxIndex().toLong()
                                val newlist = it.mapIndexed { index, contextItemEntity ->
                                    contextItemEntity.setMid(lastIndex + index)
                                    contextItemEntity
                                }
                                cache.insert(newlist) {
                                    isRequestInProgress = false
                                }
                                networkState.postValue("ok" + System.currentTimeMillis())
                            }
                        }
                    }
                }
            }
        }, {
            Log.d("shejian", "失败" + it)
            networkState.postValue(it)
            isRequestInProgress = false
        })
        return networkState
    }


}