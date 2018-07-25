package com.avengers.appwakanda.ui.detail.vm

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.avengers.appwakanda.db.room.entity.NewsDetailEntity
import com.avengers.appwakanda.ui.detail.repository.NewsDetailRepositoryX
import com.avengers.zombiebase.aacbase.BaseViewModel
import com.avengers.zombiebase.aacbase.IReqParam

class NewsDetailVMX(repository: NewsDetailRepositoryX)
    : BaseViewModel<IReqParam, NewsDetailEntity>(repository) {

    class VMFactory(private var repository: NewsDetailRepositoryX) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NewsDetailVMX::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return NewsDetailVMX(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}