package com.avengers.appwakanda.ui.indexmain.repository

import android.arch.lifecycle.LiveData
import android.arch.paging.DataSource
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.avengers.appwakanda.bean.IndexReaderListBean
import com.avengers.appwakanda.db.room.RoomHelper
import com.avengers.appwakanda.db.room.dao.IndexDataCache
import com.avengers.appwakanda.db.room.entity.ContextItemEntity
import com.avengers.appwakanda.ui.indexmain.data.PagedListBoundaryCallback
import com.avengers.appwakanda.ui.indexmain.data.PagedListBoundaryCallback.Companion.NETWORK_PAGE_SIZE
import com.avengers.appwakanda.ui.indexmain.data.ReaderListBoundaryCallback2
import com.avengers.appwakanda.webapi.SmartisanApi
import com.avengers.zombiebase.AppExecutors
import com.avengers.zombiebase.aacbase.IReqParam
import com.avengers.zombiebase.aacbase.NetworkState
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
    : PageListRepository<IReqParam, ContextItemEntity>() {

    override fun getLiveDataPagedList(factory: DataSource.Factory<Int, ContextItemEntity>,
                                      callback: PagedListBoundaryCallback<ContextItemEntity>): LiveData<PagedList<ContextItemEntity>> {
        return LivePagedListBuilder(factory,
                PagedList.Config.Builder()
                        .setPageSize(DB_PAGE_SIZE)
                        .setEnablePlaceholders(true)
                        .setPrefetchDistance(VISIBLE_THRESHOLD)
                        .build())
                .setBoundaryCallback(callback)
                .build()
    }

    override fun getDataSourceFactory(): DataSource.Factory<Int, ContextItemEntity> {
        return cache.queryIndexList()
    }

    override fun getCallBack(args: IReqParam): PagedListBoundaryCallback<ContextItemEntity> {
        return ReaderListBoundaryCallback2(
                args,
                service,
                cache,
                this::insertResultIntoDb)
    }

    override fun refresh(args: IReqParam) {
        service.getSmtIndex("", 0, NETWORK_PAGE_SIZE).enqueue(object : Callback<IndexReaderListBean> {
            override fun onFailure(call: Call<IndexReaderListBean>?, t: Throwable?) {
                refreshState.value = NetworkState.error(t?.message)
            }

            override fun onResponse(call: Call<IndexReaderListBean>?, response: Response<IndexReaderListBean>) {
                appExecutors.diskIO().execute {
                    response.body()?.let {
                        RoomHelper.getWakandaDb().runInTransaction {
                            cache.cleanData {
                                insertResultIntoDb(it)
                                refreshState.postValue(NetworkState.LOADED)
                            }
                        }
                    }
                }
            }
        })
    }

    override fun queryFromDb(args: IReqParam): DataSource.Factory<Int, ContextItemEntity>? {
        return cache.queryIndexList()
    }

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


}