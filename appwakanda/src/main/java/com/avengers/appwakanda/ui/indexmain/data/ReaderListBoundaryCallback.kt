package com.avengers.appwakanda.ui.indexmain.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PagedList
import android.util.Log
import com.avengers.appwakanda.WakandaModule
import com.avengers.appwakanda.db.room.RoomHelper
import com.avengers.appwakanda.db.room.dao.IndexDataCache
import com.avengers.appwakanda.db.room.entity.ContextItemEntity
import com.avengers.appwakanda.webapi.Api
import com.avengers.appwakanda.webapi.SmartisanService

//分页的边界回调类
class ReaderListBoundaryCallback(
        private val query: String,
        private val service: SmartisanService,
        private val cache: IndexDataCache
) : PagedList.BoundaryCallback<ContextItemEntity>() {

    companion object {
        private const val NETWORK_PAGE_SIZE = 20
    }

    // keep the last requested page.
// When the request is successful, increment the page number.
    private var lastRequestedPage = 0

    private val _networkErrors = MutableLiveData<String>()
    // LiveData of network errors.
    val networkErrors: LiveData<String>
        get() = _networkErrors

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false


    fun reqAndSaveData() {
        if (isRequestInProgress) {
            return
        }
        isRequestInProgress = true
        service.indexMainData(Api.getSmartApi(), query, NETWORK_PAGE_SIZE, lastRequestedPage, {
            WakandaModule.appExecutors!!.diskIO()?.execute{
                it.let {
                    RoomHelper.getWakandaDb().runInTransaction {
                        val lastIndex = cache.queryMaxIndex().toLong()
                        var newlist = it.mapIndexed { index, contextItemEntity ->
                            contextItemEntity.setMid(lastIndex + index)
                            contextItemEntity
                        }
                        Log.d("shejian", "插入数据库完成" + lastRequestedPage)
                        cache.insert(newlist) {
                            lastRequestedPage++
                            isRequestInProgress = false
                        }
                    }
                }
            }


        }, {
            Log.d("shejian", "失败" + it)
            _networkErrors.postValue(it)
            isRequestInProgress = false
        })
    }


    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        reqAndSaveData()
    }

    override fun onItemAtEndLoaded(itemAtEnd: ContextItemEntity) {
        super.onItemAtEndLoaded(itemAtEnd)
        reqAndSaveData()
    }
}