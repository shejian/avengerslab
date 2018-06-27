package com.avengers.appwakanda.ui.indexmain.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PagedList
import android.util.Log
import com.avengers.appwakanda.db.room.dao.IndexDataCache
import com.avengers.appwakanda.db.room.entity.ContextItemEntity
import com.avengers.appwakanda.webapi.SmartisanService
import com.bty.retrofit.Api

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
    private var lastRequestedPage = 1

    private val _networkErrors = MutableLiveData<String>()
    // LiveData of network errors.
    val networkErrors: LiveData<String>
        get() = _networkErrors

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false


    fun reqAndSaveData() {
        service.indexMainData(Api.getSmartApi(), "line/show", NETWORK_PAGE_SIZE, lastRequestedPage, {
            cache.insert(it) {
                lastRequestedPage++
                isRequestInProgress = false
                Log.d("shejian", "插入数据库完成" + lastRequestedPage)
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