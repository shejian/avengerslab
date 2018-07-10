package com.avengers.appwakanda.ui.indexmain.vm

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import com.avengers.appwakanda.db.room.entity.ContextItemEntity
import com.avengers.appwakanda.ui.indexmain.repository.NetworkState

data class ItemResult(
        val data: LiveData<PagedList<ContextItemEntity>>,
        val netWorkState: LiveData<NetworkState>,
        val refreshState: LiveData<NetworkState>,
        var refresh: () -> Unit,
        var retry: () -> Unit
)



