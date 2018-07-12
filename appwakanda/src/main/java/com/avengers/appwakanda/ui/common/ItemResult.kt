package com.avengers.appwakanda.ui.common

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import com.avengers.appwakanda.db.room.entity.ContextItemEntity
import com.avengers.appwakanda.ui.indexmain.repository.NetworkState

data class ItemResult<T>(
        override var data: LiveData<PagedList<T>>,
        override var netWorkState: LiveData<NetworkState>,
        val refreshState: LiveData<NetworkState>,
        override var refresh: () -> Unit,
        val retry: () -> Unit
) : BaseVMResult<PagedList<T>>(data, refreshState, refresh)



