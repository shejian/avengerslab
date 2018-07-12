package com.avengers.appwakanda.ui.detail.vm

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.avengers.appwakanda.ui.detail.repository.NewsDetailRepository

class NewsDetailVM(private val repository: NewsDetailRepository) : ViewModel() {

    val queryFrom = MutableLiveData<String>()//"查询条件"


    fun initData() {


    }


}