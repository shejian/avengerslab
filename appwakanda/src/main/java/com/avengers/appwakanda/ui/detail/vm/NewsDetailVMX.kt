package com.avengers.appwakanda.ui.detail.vm

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.avengers.appwakanda.db.room.entity.NewsDetailEntity
import com.avengers.appwakanda.ui.common.BaseVM
import com.avengers.appwakanda.ui.common.ReqParam
import com.avengers.appwakanda.ui.detail.repository.NewsDetailRepositoryX

class NewsDetailVMX(private val repository: NewsDetailRepositoryX) :
        BaseVM<ReqParam, NewsDetailEntity>(repository) {

    class VMfactory(var resp: NewsDetailRepositoryX) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NewsDetailVMX::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return NewsDetailVM(resp) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}