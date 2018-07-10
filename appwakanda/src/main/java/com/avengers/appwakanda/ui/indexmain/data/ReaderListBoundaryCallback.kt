package com.avengers.appwakanda.ui.indexmain.data

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PagedList
import android.util.Log
import com.avengers.appwakanda.WakandaModule
import com.avengers.appwakanda.bean.IndexReaderListBean
import com.avengers.appwakanda.db.room.dao.IndexDataCache
import com.avengers.appwakanda.db.room.entity.ContextItemEntity
import com.avengers.appwakanda.ui.indexmain.repository.NetworkState
import com.avengers.appwakanda.webapi.PagingRequestHelper
import com.avengers.appwakanda.webapi.SmartisanApi
import com.avengers.appwakanda.webapi.createStatusLiveData
import com.avengers.zombiebase.ApplicationInitBase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//分页的边界回调类
class ReaderListBoundaryCallback(
        private val query: String,
        private val service: SmartisanApi,
        private val cache: IndexDataCache,
        //处理网络返回数据的匿名函数
        private val handleResponse: (IndexReaderListBean) -> Unit
) : PagedList.BoundaryCallback<ContextItemEntity>() {


    //实例化PagingRequestHelper
    val helper = PagingRequestHelper(ApplicationInitBase.getInstanceExecutors().diskIO())

    //在该函数中，实现了对三种请求状态的数据监听，其结果在内部做了监控
    val networkState = helper.createStatusLiveData()

    public companion object {
        const val NETWORK_PAGE_SIZE = 20
    }

    private var lastRequestedPage = 0
    // keep the last requested page.

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false

/*
    private fun reqAndSaveData() {
        if (isRequestInProgress) {
            return
        }
        isRequestInProgress = true
        _netWorkState.postValue(NetworkState.LOADING)
        service.indexMainData(Api.getSmartApi(), query, NETWORK_PAGE_SIZE, lastRequestedPage, {
            WakandaModule.appExecutors!!.diskIO()?.execute {
                it.let {
                    RoomHelper.getWakandaDb().runInTransaction {
                        val lastIndex = cache.queryMaxIndex().toLong()
                        var newlist = it.mapIndexed { index, contextItemEntity ->
                            contextItemEntity.setMid(lastIndex + index)
                            contextItemEntity
                        }
                        Log.d("shejian", "插入数据库完成$lastRequestedPage")
                        cache.insert(newlist) {
                            isRequestInProgress = false
                        }
                    }
                }
                _netWorkState.postValue(NetworkState.LOADED)
            }


        }, {
            Log.d("shejian", "失败" + it)
            //_networkErrors.postValue("fail" + System.currentTimeMillis())
            _netWorkState.postValue(NetworkState.error(it))
            isRequestInProgress = false
        })
    }*/


    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        Log.d("shejian", "onZeroItemsLoaded")
        //reqAndSaveData()
    }

    override fun onItemAtEndLoaded(itemAtEnd: ContextItemEntity) {
        super.onItemAtEndLoaded(itemAtEnd)
        //取出数据库中最后一个时计算页码
        Log.d("shejian", "onItemAtEndLoaded")
        val pageNum = (itemAtEnd.getMid()?.plus(1))?.div(NETWORK_PAGE_SIZE)
        lastRequestedPage = pageNum?.toInt()!!
        helper.runIfNotRunning(PagingRequestHelper.RequestType.AFTER) {
            service.getSmtIndex(query, NETWORK_PAGE_SIZE, lastRequestedPage).enqueue(createWebserviceCallback(it))
        }
    }


    /**
     * API请求的callback处理函数
     */
    private fun createWebserviceCallback(
            it: PagingRequestHelper.Request.Callback
    ): Callback<IndexReaderListBean> {
        return object : Callback<IndexReaderListBean> {
            override fun onFailure(call: Call<IndexReaderListBean>, t: Throwable) {
                it.recordFailure(t)
            }

            override fun onResponse(call: Call<IndexReaderListBean>, response: Response<IndexReaderListBean>) {
                //插入到数据库
                insertItemsIntoDb(response, it)
            }
        }
    }

    //插入数据库
    private fun insertItemsIntoDb(response: Response<IndexReaderListBean>, it: PagingRequestHelper.Request.Callback) {
        WakandaModule.appExecutors!!.diskIO()?.execute {
            handleResponse(response.body()!!)
            it.recordSuccess()
        }
    }

}