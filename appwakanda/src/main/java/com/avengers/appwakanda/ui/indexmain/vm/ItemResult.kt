package com.avengers.appwakanda.ui.indexmain.vm

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import com.avengers.appwakanda.db.room.entity.ContextItemEntity
import com.avengers.zombiebase.aacbase.NetworkState

data class ItemResult<T>(
        val data: LiveData<PagedList<T>>,
        val netWorkState: LiveData<NetworkState>,
        val refreshState: LiveData<NetworkState>,
        var refresh: () -> Unit,
        var retry: () -> Unit
)



