package com.avengers.appwakanda.ui.indexmain.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.avengers.appwakanda.WakandaModule
import com.avengers.appwakanda.bean.IndexReaderListBean
import com.avengers.appwakanda.db.room.RoomHelper
import com.avengers.appwakanda.db.room.dao.IndexDataCache
import com.avengers.appwakanda.db.room.entity.ContextItemEntity
import com.avengers.appwakanda.ui.indexmain.data.ReaderListBoundaryCallback
import com.avengers.appwakanda.webapi.SmartisanApi
import com.avengers.zombiebase.aacbase.ItemCoreResult
import com.avengers.zombiebase.aacbase.NetworkState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * 处理数据逻辑
 */
class IndexRepository(
        private val service: SmartisanApi,
        private val cache: IndexDataCache) {
    companion object {
        private const val DB_PAGE_SIZE = 20
    }

    /**
     * 初始化BoundaryCallback，dataSourceFactory，LivePagedListBuilder，以及一些网络状态
     */
    fun getIndexListData(query: String): ItemCoreResult<ContextItemEntity> {
        //设置边界回调
        val callback = ReaderListBoundaryCallback(
                query,
                service,
                cache,
                this::insertResultIntoDb)

        //缓存数据工厂类
        val dataSourceFactory = cache.queryIndexList()

        val mLiveData = LivePagedListBuilder(dataSourceFactory,
                PagedList.Config.Builder()
                        .setPageSize(DB_PAGE_SIZE)
                        .setEnablePlaceholders(true)
                        .setPrefetchDistance(2)
                        .build())
                .setBoundaryCallback(callback)
                .build()

        var netWorkFun = MutableLiveData<Unit>()

        var refreshState = Transformations.switchMap(netWorkFun) {
            refresh(query)
        }

        //数据封装
        return ItemCoreResult(
                mLiveData,
                callback.networkState,
                refreshState,
                {
                    netWorkFun.value = null
                },
                {
                    //重试全部失败掉的请求
                    callback.helper.retryAllFailed()
                })
    }

    private var lastRequestedPage = 0

    /**
     * 下拉刷新数据，清空旧数据
     */
    private fun refresh(query: String): LiveData<NetworkState> {
        val netWorkState = MutableLiveData<NetworkState>()
        netWorkState.postValue(NetworkState.LOADING)
        service.getSmtIndex(query, lastRequestedPage, ReaderListBoundaryCallback.NETWORK_PAGE_SIZE)
                .enqueue(object : Callback<IndexReaderListBean> {
                    override fun onFailure(call: Call<IndexReaderListBean>?, t: Throwable?) {
                        netWorkState.value = NetworkState.error(t?.message)
                    }

                    override fun onResponse(call: Call<IndexReaderListBean>?, response: Response<IndexReaderListBean>) {
                        WakandaModule.appExecutors!!.diskIO()?.execute {
                            response.body()?.let {
                                RoomHelper.getWakandaDb().runInTransaction {
                                    cache.cleanData {
                                        insertResultIntoDb(it)
                                        netWorkState.postValue(NetworkState.LOADED)
                                    }
                                }
                            }
                        }
                    }
                })
        return netWorkState
    }

/*

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
                                refreshState.postValue(NetworkState.LOADED)
                            }
                        }
                    }
                }
            }
        }, {
            Log.d("shejian", "失败" + it)
            refreshState.postValue(NetworkState.error(it))
            isRequestInProgress = false
        })*/


    private fun insertResultIntoDb(response: IndexReaderListBean) {
        response.data?.list?.let {
            RoomHelper.getWakandaDb().runInTransaction {
            //    val lastIndex = cache.queryMaxIndex().toLong()
               // var newlist = it.mapIndexed { index, contextItemEntity ->
                 //   contextItemEntity._mid = lastIndex + index contextItemEntity
                //}
             //   cache.insert(newlist) {}
            }
        }

    }


}