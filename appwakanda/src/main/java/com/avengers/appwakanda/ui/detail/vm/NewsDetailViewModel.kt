package com.avengers.appwakanda.ui.detail.vm

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.avengers.appwakanda.bean.NewsDetailBean
import com.avengers.appwakanda.ui.detail.repository.NewsDetailRepositoryX
import com.avengers.zombiebase.aacbase.BaseViewModel

class NewsDetailViewModel(repository: NewsDetailRepositoryX)
    : BaseViewModel<IReqDetailParam, NewsDetailBean>(repository) {

    var statusData = MutableLiveData<String>()


}