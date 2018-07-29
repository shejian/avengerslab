package com.avengers.appwakanda.ui.detail.vm

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.avengers.appwakanda.bean.NewsDetailBean
import com.avengers.appwakanda.bean.QdailyDetailReqParam
import com.avengers.appwakanda.ui.detail.repository.NewsDetailRepositoryV2
import com.avengers.zombiebase.aacbase.BaseViewModel

class NewsDetailViewModelV2(repository: NewsDetailRepositoryV2)
    : BaseViewModel<IReqDetailParam, NewsDetailBean>(repository) {


    val qdqueryParam = MutableLiveData<QdailyDetailReqParam>()

    private val qdresult = Transformations.map(qdqueryParam) {
        repository.qdRepository.assembleResult(it)
    }

    val qdliveData = Transformations.switchMap(qdresult) { it.data }!!

//    val qdnetWorkState = Transformations.switchMap(qdresult) { it.netWorkState }!!


}