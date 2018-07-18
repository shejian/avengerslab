package com.avengers.appwakanda.ui.detail.vm

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.avengers.appwakanda.db.room.entity.NewsDetailEntity
import com.avengers.appwakanda.ui.detail.repository.NewsDetailRepositoryX
import com.avengers.zombiebase.accbase.BaseVM
import com.avengers.zombiebase.accbase.IReqParam

class NewsDetailVMX(repository: NewsDetailRepositoryX) : BaseVM<IReqParam, NewsDetailEntity>(repository) {

    class VMFactory(private var resp: NewsDetailRepositoryX) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NewsDetailVMX::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return NewsDetailVMX(resp) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}