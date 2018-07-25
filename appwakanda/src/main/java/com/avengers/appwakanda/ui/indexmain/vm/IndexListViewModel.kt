package com.avengers.appwakanda.ui.indexmain.vm

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.avengers.appwakanda.bean.NewsListReqParam
import com.avengers.appwakanda.db.room.entity.ContextItemEntity
import com.avengers.appwakanda.ui.indexmain.repository.IndexRepository2

class IndexListViewModel(private val repository: IndexRepository2)
    : PagedListViewModel<NewsListReqParam, ContextItemEntity>(repository) {

    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {
        if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount) {
            val immutableQuery = lastQueryValue()
            if (immutableQuery != null) {
                repository.assembleResult(immutableQuery)
            }
        }
    }


    class IndexViewModelFactory(private val repository: IndexRepository2) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(IndexListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return IndexListViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }


}