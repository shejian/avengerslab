package com.avengers.templete.viewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations.map
import android.arch.lifecycle.Transformations.switchMap
import android.arch.lifecycle.ViewModel
import com.avengers.templete.repository.Repository
import com.bty.retrofit.net.bean.JsonBeanRequest

/**
 * Created by duo.chen on 2018/7/13
 */
open class BaseViewModel<T : JsonBeanRequest,V>(repo: Repository<T,V>) :ViewModel() {

    val request: MutableLiveData<T> = MutableLiveData()

    val stating: LiveData<Stating<V>> = map(request) {
        repo.request(it)
    }

    val networkState: LiveData<NetworkState>? = switchMap(stating) {
        it.netWorkState
    }

    val response: LiveData<V> = switchMap(stating) { it.response }

    val refreshState: LiveData<NetworkState>? = switchMap(stating) { it.refreshState }

    fun request(value: T) {
        request.value = value
    }

    fun refresh() {
        stating.value?.refresh?.invoke()
    }

    fun retry() {
        stating.value?.refresh?.invoke()
    }

}