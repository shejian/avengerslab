package com.avengers.appwakanda.ui.common

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations.*
import android.arch.lifecycle.ViewModel

open class BaseVM<K : IReqParam, T : IBeanResponse>(private val repository: AbsRepository<T>) : ViewModel() {

    val queryParam = MutableLiveData<K>()//"请求条件"


    private val result = map(queryParam) { repository.assemblyVMResult(it) }

    val liveData = switchMap(result) { it.data }!!

    val netWorkState = switchMap(result) { it.netWorkState }!!

    fun refresh() {
        result.value?.refresh?.invoke()
    }


}