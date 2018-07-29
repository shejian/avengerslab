package com.avengers.appwakanda.ui.detail.vm

import android.arch.lifecycle.MutableLiveData
import com.avengers.appwakanda.bean.NewsDetailBean
import com.avengers.appwakanda.ui.detail.repository.NewsDetailRepositoryV1
import com.avengers.zombiebase.aacbase.BaseViewModel

class NewsDetailViewModel(repository: NewsDetailRepositoryV1)
    : BaseViewModel<IReqDetailParam, NewsDetailBean>(repository) {

    var statusData = MutableLiveData<String>()


}