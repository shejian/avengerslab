package com.avengers.appwakanda.ui.detail.vm

import com.avengers.appwakanda.bean.NewsDetailBean
import com.avengers.appwakanda.ui.detail.repository.NewsDetailRepositoryX
import com.avengers.zombiebase.aacbase.BaseViewModel

class NewsDetailViewModel(repository: NewsDetailRepositoryX)
    : BaseViewModel<IReqDetailParam,NewsDetailBean>(repository)