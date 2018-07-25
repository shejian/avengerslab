package com.avengers.appwakanda.ui.indexmain.data

import com.avengers.appwakanda.WakandaModule
import com.avengers.appwakanda.bean.IndexReaderListBean
import com.avengers.appwakanda.db.room.RoomHelper
import com.avengers.appwakanda.db.room.dao.IndexDataCache
import com.avengers.appwakanda.db.room.entity.ContextItemEntity
import com.avengers.appwakanda.webapi.PagingRequestHelper
import com.avengers.appwakanda.webapi.SmartisanApi
import com.avengers.zombiebase.aacbase.IReqParam
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//分页的边界回调类
class ReaderListBoundaryCallback2(
        private val query: IReqParam,
        private val service: SmartisanApi,
        private val cache: IndexDataCache,
        //处理网络返回数据的匿名函数
        private val handleResponse: (IndexReaderListBean) -> Unit
) : PagedListBoundaryCallback<ContextItemEntity>() {

    override fun loadInit() {
    }

    private var lastRequestedPage = 0

    override fun loadMore(itemAtEnd: ContextItemEntity, callBack: PagingRequestHelper.Request.Callback?) {
        //取出数据库中最后一个时计算页码，也可以取索引
        val pageNum = (itemAtEnd._mid?.plus(1))?.div(NETWORK_PAGE_SIZE)
        lastRequestedPage = pageNum?.toInt()!!
        service.getSmtIndex("", lastRequestedPage, NETWORK_PAGE_SIZE)
                .enqueue(createWebserviceCallback(callBack!!))
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
                                handleResponse(response.body()!!)
                                itccc.recordSuccess()
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
        WakandaModule.appExecutors!!.diskIO()?.execute {
            handleResponse(response.body()!!)
            it.recordSuccess()
        }
    }

}