package com.avengers.appwakanda.ui.indexmain.data

import android.arch.paging.PagedList
import com.avengers.appwakanda.WakandaModule
import com.avengers.appwakanda.bean.IndexReaderListBean
import com.avengers.appwakanda.db.room.RoomHelper
import com.avengers.appwakanda.db.room.dao.IndexDataCache
import com.avengers.appwakanda.db.room.entity.ContextItemEntity
import com.avengers.appwakanda.webapi.PagingRequestHelper
import com.avengers.appwakanda.webapi.SmartisanApi
import com.avengers.appwakanda.webapi.createStatusLiveData
import com.avengers.zombiebase.ApplicationInitBase
import com.avengers.zombiebase.LogU
import com.avengers.zombiebase.aacbase.IReqParam
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//分页的边界回调类
abstract class PagedListBoundaryCallback<T> : PagedList.BoundaryCallback<T>() {
    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }

    //实例化PagingRequestHelper
    val helper = PagingRequestHelper(ApplicationInitBase.getInstanceExecutors().diskIO())

    //在该函数中，实现了对三种请求状态的数据监听，其结果在内部做了监控
    val networkState = helper.createStatusLiveData()

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        helper.runIfNotRunning(PagingRequestHelper.RequestType.INITIAL) {
            loadInit()
        }
    }

    override fun onItemAtFrontLoaded(itemAtFront: T) {
        // ignored, since we only ever append to what's in the DB
    }

    override fun onItemAtEndLoaded(itemAtEnd: T) {
        super.onItemAtEndLoaded(itemAtEnd)
        helper.runIfNotRunning(PagingRequestHelper.RequestType.AFTER) {
            loadMore(itemAtEnd, it)
        }
    }


    abstract fun loadInit()
    abstract fun loadMore(itemAtEnd: T, callBack: PagingRequestHelper.Request.Callback?)

}