package com.avengers.appwakanda.ui.indexmain.repository

import android.arch.paging.LivePagedListBuilder
import com.avengers.appwakanda.db.room.dao.IndexDataCache
import com.avengers.appwakanda.ui.indexmain.data.ReaderListBoundaryCallback
import com.avengers.appwakanda.ui.indexmain.vm.ItemResult
import com.avengers.appwakanda.webapi.SmartisanService

class IndexRepository(
        private val service: SmartisanService,
        private val cache: IndexDataCache
) {
    companion object {
        private const val DB_PAGE_SIZE = 10
    }


    fun getIndexListData(query:String): ItemResult {

        val callback = ReaderListBoundaryCallback(query, service, cache)

        val dataSourceFactory = cache.queryIndexList()

        val networkErrors = callback.networkErrors

        val mLiveData = LivePagedListBuilder(dataSourceFactory, DB_PAGE_SIZE)
                .setBoundaryCallback(callback)
                .build()

        return ItemResult(mLiveData, networkErrors)
    }


}