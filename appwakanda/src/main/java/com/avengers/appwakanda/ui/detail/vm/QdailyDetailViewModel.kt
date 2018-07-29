package com.avengers.appwakanda.ui.detail.vm

import com.avengers.appwakanda.bean.QdailyDetailBean
import com.avengers.appwakanda.bean.QdailyDetailReqParam
import com.avengers.appwakanda.ui.detail.repository.QdailyDetailRepository
import com.avengers.zombiebase.aacbase.BaseViewModel

class QdailyDetailViewModel(repository: QdailyDetailRepository)
    : BaseViewModel<QdailyDetailReqParam, QdailyDetailBean>(repository) {

}