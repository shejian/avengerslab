package com.avengers.appwakanda.ui.indexmain.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import android.arch.paging.PagedList
import com.avengers.appwakanda.db.room.entity.ContextItemEntity
import com.avengers.appwakanda.ui.indexmain.data.PagedListBoundaryCallback
import com.avengers.appwakanda.ui.indexmain.vm.ItemResult
import com.avengers.zombiebase.aacbase.BaseCoreResult
import com.avengers.zombiebase.aacbase.IBeanResponse
import com.avengers.zombiebase.aacbase.IReqParam
import com.avengers.zombiebase.aacbase.NetworkState

/**
 * @author Jervis
 * @date 2018-07-17
 * Repository作用的抽象类
 * haveCache 默认具备缓存能力
 * assembleResult 是一切的开始，它负责组装驱动所有事件发生的关键对数据对象
 * 最低颗粒度的情况下包含：一个请求回来的结果，一个请求状态，一个重试或者叫刷新的函数对象
 *
 */
abstract class PageListRepository<V : IReqParam, T : IBeanResponse>(
        private var haveCache: Boolean = true) {

    companion object {
        const val DB_PAGE_SIZE = 20
        const val VISIBLE_THRESHOLD = 5
    }

    var refreshState = MutableLiveData<NetworkState>()

    private lateinit var dataSource: LiveData<T>

    /**
     * 组装一个界面主要数据的模型BaseVMResult
     */
    fun assembleResult(args: V): ItemResult<T> {

        var callback = getCallBack(args)

        val dataSourceFactory = getDataSourceFactory()

        val liveDataPagedList = getLiveDataPagedList(dataSourceFactory, callback)

        return ItemResult(liveDataPagedList, callback.networkState, refreshState,
                { superRefresh(args) },
                { callback.helper.retryAllFailed() })
    }


    abstract fun getLiveDataPagedList(factory: DataSource.Factory<Int, T>, callback: PagedListBoundaryCallback<T>): LiveData<PagedList<T>>

    abstract fun getDataSourceFactory(): DataSource.Factory<Int, T>

    abstract fun getCallBack(args: V): PagedListBoundaryCallback<T>

    private fun superRefresh(args: V) {
        refreshState.postValue(NetworkState.LOADING)
        refresh(args)
    }

    /**
     * 请求刷新数据
     */
    abstract fun refresh(args: V)

    /**
     * 保存数据。有缓存是需要子类自己实现，不要缓存时直接设置到LiveData中
     */
    fun saveData(t: T) {
        when {
            haveCache -> addToDb(t)
            else -> (dataSource as MutableLiveData).postValue(t)
        }
    }


    /**
     * 获取LiveData，有缓存时取数据库，不要缓存时实例化一个空的LiveData
     */
    private fun getLiveData(haveCache: Boolean, args: V): LiveData<T> {
        return when {
        //  haveCache -> queryFromDb(args)!!
            else -> MutableLiveData<T>()
        }
    }

    /**
     * 有缓存时需要自己实现插入数据库的函数
     */
    open fun addToDb(t: T) {

    }

    /**
     * 有缓存时需要自己实现查询数据库的函数
     */
    open fun queryFromDb(args: V): DataSource.Factory<Int, T>? {
        return null
    }


}