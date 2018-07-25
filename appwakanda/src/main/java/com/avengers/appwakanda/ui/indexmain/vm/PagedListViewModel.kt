package com.avengers.appwakanda.ui.indexmain.vm

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations.map
import android.arch.lifecycle.Transformations.switchMap
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import com.avengers.zombiebase.aacbase.*
import com.avengers.zombiebase.aacbase.paging.PageListRepository

open
class PagedListViewModel<I : IReqParam, O : IBeanResponse>(private val repository: PageListRepository<I, O>) : ViewModel() {

    companion object {
        const val VISIBLE_THRESHOLD = 5
    }


    /**
     * 设置请求参数，
     */
    fun getPagedListData(queryString: I) {
        queryLiveData.postValue(queryString)
    }

    //获取参数值
    fun lastQueryValue() = queryLiveData.value

    private val queryLiveData = MutableLiveData<I>()

    //queryLiveData 为参数 通过函数 转化为结果，Transformations将绑定queryLiveData与匿名函数的触发
    //列表数据

    private var result: LiveData<ItemCoreResult<O>> = map(queryLiveData) {
        val assembleResult = repository.assembleResult(it)
        //首次打开时主动触发刷新事件
        assembleResult.refresh.invoke()
        assembleResult
    }

    var items: LiveData<PagedList<O>> = switchMap(result) { it.data }

    var netWorkState: LiveData<NetworkState> = switchMap(result) { it.netWorkState }

    var refreshState: LiveData<NetworkState> = switchMap(result) { it.refreshState }

    fun retry() {
        result.value?.retry?.invoke()
    }

    fun refresh() {
        result.value?.refresh?.invoke()
    }


}