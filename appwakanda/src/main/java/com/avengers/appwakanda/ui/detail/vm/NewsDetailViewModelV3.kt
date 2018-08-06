package com.avengers.appwakanda.ui.detail.vm

import android.arch.lifecycle.ViewModel
import com.avengers.appwakanda.ui.detail.repository.NewsDetailRepositoryV3
import com.avengers.zombiebase.aacbase.BaseViewModel

class NewsDetailViewModelV3(repository: NewsDetailRepositoryV3)
    : ViewModel() {

    var detailVm =
            BaseViewModel(repository.detailRepository)


    var qdVm =
            BaseViewModel(repository.qdRepository)





}