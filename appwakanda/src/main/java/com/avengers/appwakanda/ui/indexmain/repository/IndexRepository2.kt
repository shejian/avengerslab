package com.avengers.appwakanda.ui.indexmain.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.Transformations.switchMap
import android.arch.paging.DataSource
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.avengers.appwakanda.bean.IndexReaderListBean
import com.avengers.appwakanda.bean.NewsListReqParam
import com.avengers.appwakanda.db.room.RoomHelper
import com.avengers.appwakanda.db.room.dao.IndexDataCache
import com.avengers.appwakanda.db.room.entity.ContextItemEntity
import com.avengers.appwakanda.ui.indexmain.data.ReaderListBoundaryCallback2
import com.avengers.appwakanda.webapi.SmartisanApi
import com.avengers.zombiebase.AppExecutors
import com.avengers.zombiebase.LogU
import com.avengers.zombiebase.aacbase.NetworkState
import com.avengers.zombiebase.aacbase.paging.PagedListRepository
import com.avengers.zombiebase.aacbase.paging.PagedListBoundaryCallback
import com.avengers.zombiebase.aacbase.paging.PagedListBoundaryCallback.Companion.NETWORK_PAGE_SIZE
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * 处理数据逻辑
 */
class IndexRepository2(
        private val service: SmartisanApi,
        private val cache: IndexDataCache,
        private val appExecutors: AppExecutors)
    : PagedListRepository<NewsListReqParam, ContextItemEntity>(haveCache = false) {


    override fun getRetryFun(factory: DataSource.Factory<Int, ContextItemEntity>,
                             callback: PagedListBoundaryCallback<ContextItemEntity>?): () -> Unit {
        return when {
            haveCache -> {
                { callback?.helper?.retryAllFailed() }
            }
            else -> {
                { (factory as SubIndexListDataSourceFactory).sourceLiveData.value?.retryAllFailed() }
            }
        }

    }

    override fun getNetworkState(factory: DataSource.Factory<Int, ContextItemEntity>,
                                 callback: PagedListBoundaryCallback<ContextItemEntity>?):
            LiveData<NetworkState> {
        return when {
            haveCache -> {
                callback?.networkState!!
            }
            else -> switchMap((factory as SubIndexListDataSourceFactory).sourceLiveData) {
                it.networkState
            }
        }
    }


    /**
     * 构建LivePagedList
     */
    override fun buildLiveDataPagedList(factory: DataSource.Factory<Int, ContextItemEntity>,
                                        callback: PagedListBoundaryCallback<ContextItemEntity>?)
            : LiveData<PagedList<ContextItemEntity>> {
        return when {
            haveCache -> LivePagedListBuilder(factory,
                    PagedList.Config.Builder()
                            .setPageSize(DB_PAGE_SIZE)
                            .setEnablePlaceholders(true)
                            .setPrefetchDistance(VISIBLE_THRESHOLD)
                            .build())
                    .setBoundaryCallback(callback)
                    .build()
            else -> LivePagedListBuilder(factory, 20)
                    // provide custom executor for network requests, otherwise it will default to
                    // Arch Components' IO pool which is also used for disk access
                    .setFetchExecutor(appExecutors.networkIO())
                    .build()
        }
    }

    /**
     * 查询数据库，获取DataSource
     */
    override fun getDataSourceFactory(args: NewsListReqParam): DataSource.Factory<Int, ContextItemEntity> {
        return when {
            haveCache -> cache.queryIndexList()
            else -> {

                var ddd = SubIndexListDataSourceFactory(service, args, appExecutors.networkIO())

                return ddd
            }
        }
    }

    override fun getRefreshState(factory: DataSource.Factory<Int, ContextItemEntity>): LiveData<NetworkState> {
        return switchMap((factory as SubIndexListDataSourceFactory).sourceLiveData) {
            it.initialLoad
        }

    }

    /**
     * 边界回调
     */
    override fun getCallBack(args: NewsListReqParam): PagedListBoundaryCallback<ContextItemEntity> {
        return ReaderListBoundaryCallback2(
                args,
                service,
                cache,
                this::insertResultIntoDb)
    }

    /**
     * 请求Api，刷新数据
     */
    override fun refresh(args: NewsListReqParam, factory: DataSource.Factory<Int, ContextItemEntity>) {
        LogU.d("shejian", "refresh(args: NewsListReqParam)")
        when {
            haveCache ->
                service.getSmtIndex(args.keyWord!!, 0, NETWORK_PAGE_SIZE).enqueue(object : Callback<IndexReaderListBean> {
                    override fun onFailure(call: Call<IndexReaderListBean>?, t: Throwable?) {
                      //  refreshState.value = NetworkState.error(t?.message)
                    }

                    override fun onResponse(call: Call<IndexReaderListBean>?, response: Response<IndexReaderListBean>) {
                        appExecutors.diskIO().execute {
                            response.body()?.let {
                                RoomHelper.getWakandaDb().runInTransaction {
                                    cache.cleanData {
                                        insertResultIntoDb(it)
                        //                refreshState.postValue(NetworkState.LOADED)
                                    }
                                }
                            }
                        }
                    }
                })

            else -> (factory as SubIndexListDataSourceFactory).sourceLiveData.value?.invalidate()

        }


    }

    /**
     * 插入数据
     */
    private fun insertResultIntoDb(response: IndexReaderListBean) {
        response.data?.list?.let {
            RoomHelper.getWakandaDb().runInTransaction {
                val lastIndex = cache.queryMaxIndex().toLong()
                var newlist = it.mapIndexed { index, contextItemEntity ->
                    contextItemEntity._mid = lastIndex + index
                    contextItemEntity
                }
                cache.insert(newlist) {}
            }
        }

    }


    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: IndexRepository2? = null

        fun getInstance(service: SmartisanApi,
                        cache: IndexDataCache,
                        appExecutors: AppExecutors) =
                instance ?: synchronized(this) {
                    instance ?: IndexRepository2(service, cache, appExecutors).also {
                        instance = it
                    }
                }
    }


}