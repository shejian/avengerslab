package com.avengers.appwakanda.ui.detail.vm

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations.map
import android.arch.lifecycle.Transformations.switchMap
import android.arch.lifecycle.ViewModel
import com.avengers.appwakanda.ui.detail.repository.NewsDetailRepository

class NewsDetailVM(private val repository: NewsDetailRepository) : ViewModel() {

    val queryFrom = MutableLiveData<String>()//"查询条件"

    private val result = map(queryFrom) {
        repository.getDetailData(it)
    }

    val detail = switchMap(result) {
        it.data
    }

    val netWorkState = switchMap(result) {
        it.netWorkState
    }

    fun refresh() {
        result.value?.refresh?.invoke()
    }



}