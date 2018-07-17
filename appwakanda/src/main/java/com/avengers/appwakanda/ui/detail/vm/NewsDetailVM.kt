package com.avengers.appwakanda.ui.detail.vm

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations.map
import android.arch.lifecycle.Transformations.switchMap
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.avengers.appwakanda.ui.detail.repository.NewsDetailRepositoryX

class NewsDetailVM(private val repository: NewsDetailRepositoryX) : ViewModel() {

    val queryFrom = MutableLiveData<String>()//"查询条件"

    private val result = map(queryFrom) {
        repository.assemblyVMResult(it,123)
    }

    //这是一次性的不会反复触发
    val detail = switchMap(result) {
        it.data
    }

    val netWorkState = switchMap(result) {
        it.netWorkState
    }

    fun refresh() {
        result.value?.refresh?.invoke()
    }


    class VMfactory(var resp: NewsDetailRepositoryX) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NewsDetailVM::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return NewsDetailVM(resp) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}