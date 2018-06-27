package com.avengers.appwakanda.ui.indexmain.vm

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import com.avengers.appwakanda.db.room.entity.ContextItemEntity
import com.avengers.appwakanda.db.room.RoomHelper
import com.avengers.appwakanda.db.room.dao.IndexDataCache
import com.avengers.appwakanda.ui.indexmain.repository.IndexRepository
import com.avengers.appwakanda.webapi.SmartisanService
import com.avengers.zombiebase.ApplicationInitBase

class IndexListViewModel(private val repository: IndexRepository) : ViewModel() {

    var indexListLiveData: LiveData<PagedList<ContextItemEntity>>? = null


    var indexpeository: IndexRepository? = null

    fun init() {
        val cache = IndexDataCache(RoomHelper.indexDataDao(), ApplicationInitBase.getInstanceExecutors().diskIO())
        indexpeository = IndexRepository(SmartisanService(), cache)
    }

    /**
     * Search a repository based on a query string.
     */
    fun getIndexData(queryString: String) {
        queryLiveData.postValue(queryString)
    }

    fun lastQueryValue(): String? = queryLiveData.value

    private val queryLiveData = MutableLiveData<String>()//"line/show"


    var result: LiveData<ItemResult> = Transformations.map(queryLiveData) {
        repository.getIndexListData(it)
    }


    var items: LiveData<PagedList<ContextItemEntity>> = Transformations.switchMap(result) {
        it.data
    }


    var errorInfo: LiveData<String>? = Transformations.switchMap(result) {
        it.newworkError
    }


}