package com.avengers.appwakanda.ui.indexmain.data

import com.avengers.appwakanda.WakandaModule
import com.avengers.appwakanda.bean.IndexReaderListBean
import com.avengers.appwakanda.bean.NewsListReqParam
import com.avengers.appwakanda.db.room.RoomHelper
import com.avengers.appwakanda.db.room.dao.IndexDataCache
import com.avengers.appwakanda.db.room.entity.ContextItemEntity
import com.avengers.appwakanda.webapi.SmartisanApi
import com.avengers.zombiebase.AppExecutors
import com.avengers.zombiebase.aacbase.paging.PagedListBoundaryCallback
import com.avengers.zombiebase.aacbase.paging.PagingRequestHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//分页的边界回调类
class ReaderListBoundaryCallback2(
        private val query: NewsListReqParam,
        private val service: SmartisanApi,
        private val cache: IndexDataCache,
        //处理网络返回数据的匿名函数
        private val handleResponse: (IndexReaderListBean, insertFinished: () -> Unit) -> Unit
) : PagedListBoundaryCallback<ContextItemEntity>() {


    override fun loadInit() {
        //放弃在此处做初始化，自己实现刷新会更方便控制
    }

    var lastRequestedPage = 0

    override fun loadMore(itemAtEnd: ContextItemEntity, callBack: PagingRequestHelper.Request.Callback?) {
        //取出数据库中最后一个时计算页码，也可以取索引

        WakandaModule.appExecutors.diskIO().execute {
            var total = cache.queryTotal()
            val pageNum = total.div(NETWORK_PAGE_SIZE)
            WakandaModule.appExecutors.mainThread().execute {
                service.getSmtIndex(query.keyWord!!, pageNum, NETWORK_PAGE_SIZE)
                        .enqueue(createWebserviceCallback(callBack!!))
            }
        }
        // val pageNum = (itemAtEnd.oid)?.div(NETWORK_PAGE_SIZE)
        // lastRequestedPage = pageNum?.toInt()!!

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


    private fun createWebserviceCallbackRefresh(
            itccc: PagingRequestHelper.Request.Callback
    ): Callback<IndexReaderListBean> {
        return object : Callback<IndexReaderListBean> {
            override fun onFailure(call: Call<IndexReaderListBean>, t: Throwable) {
                itccc.recordFailure(t)
            }

            override fun onResponse(call: Call<IndexReaderListBean>, response: Response<IndexReaderListBean>) {
                WakandaModule.appExecutors!!.diskIO()?.execute {
                    response.body()?.let {
                        RoomHelper.getWakandaDb().runInTransaction {
                            cache.cleanData {
                                handleResponse(response.body()!!) {
                                    itccc.recordSuccess()
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    //插入数据库
    private fun insertItemsIntoDb(response: Response<IndexReaderListBean>,
                                  it: PagingRequestHelper.Request.Callback) {
        handleResponse(response.body()!!) {
            it.recordSuccess()
        }

    }

}