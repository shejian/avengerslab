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


    companion object {
        private const val VISIBLE_THRESHOLD = 5
    }

    //   var indexListLiveData: LiveData<PagedList<ContextItemEntity>>? = null


    // var indexpeository: IndexRepository? = null
/*
    fun init() {
        val cache = IndexDataCache(RoomHelper.indexDataDao(), ApplicationInitBase.getInstanceExecutors().diskIO())
        indexpeository = IndexRepository(SmartisanService(), cache)
    }*/

    /**
     * 设置请求参数，
     */
    fun getIndexData(queryString: String) {
        queryLiveData.postValue(queryString)
    }

    //获取参数值
    fun lastQueryValue(): String? = queryLiveData.value

    private val queryLiveData = MutableLiveData<String>()//"line/show"


    //queryLiveData 为参数 通过函数 转化为结果，Transformations将绑定queryLiveData与匿名函数的触发
    private var result: LiveData<ItemResult> = Transformations.map(queryLiveData) {

        repository.getIndexListData(it)

    }


    //列表数据
    var items: LiveData<PagedList<ContextItemEntity>> = Transformations.switchMap(result) {
        it.data
    }


    var errorInfo: LiveData<String>? = Transformations.switchMap(result) {
        it.newworkError
    }

    fun refresh() {
        repository.refresh(this.lastQueryValue()!!, result.value!!)
    }


    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {
        if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount) {
            val immutableQuery = lastQueryValue()
            if (immutableQuery != null) {
                repository.getIndexListData(immutableQuery)
            }
        }
    }

}