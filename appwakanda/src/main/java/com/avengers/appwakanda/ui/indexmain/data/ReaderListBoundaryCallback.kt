package com.avengers.appwakanda.ui.indexmain.data

import android.arch.paging.PagedList
import android.util.Log
import com.avengers.appwakanda.WakandaModule
import com.avengers.appwakanda.bean.IndexReaderListBean
import com.avengers.appwakanda.db.room.dao.IndexDataCache
import com.avengers.appwakanda.db.room.entity.ContextItemEntity
import com.avengers.appwakanda.webapi.SmartisanApi
import com.avengers.appwakanda.webapi.createStatusLiveData
import com.avengers.zombiebase.ApplicationInitBase
import com.avengers.zombiebase.LogU
import com.avengers.zombiebase.aacbase.paging.PagingRequestHelper
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


    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        LogU.d("shejian", "onZeroItemsLoaded")
        //reqAndSaveData()
    }

    override fun onItemAtEndLoaded(itemAtEnd: ContextItemEntity) {
        super.onItemAtEndLoaded(itemAtEnd)
        //取出数据库中最后一个时计算页码
        Log.d("shejian", "onItemAtEndLoaded")
        val pageNum = (itemAtEnd._mid?.plus(1))?.div(NETWORK_PAGE_SIZE)
        lastRequestedPage = pageNum?.toInt()!!
        helper.runIfNotRunning(PagingRequestHelper.RequestType.AFTER) {
            service.getSmtIndex(query, lastRequestedPage, NETWORK_PAGE_SIZE).enqueue(createWebserviceCallback(it))
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