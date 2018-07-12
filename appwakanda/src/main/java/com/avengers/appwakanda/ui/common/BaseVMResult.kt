package com.avengers.appwakanda.ui.common

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import com.avengers.appwakanda.db.room.entity.ContextItemEntity
import com.avengers.appwakanda.ui.indexmain.repository.NetworkState

open class BaseVMResult<T>(
        open var data: LiveData<T>,
        open var netWorkState: LiveData<NetworkState>,
        open var refresh: () -> Unit
)



