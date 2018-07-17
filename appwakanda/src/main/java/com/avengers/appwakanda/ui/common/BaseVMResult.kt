package com.avengers.appwakanda.ui.common

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import com.avengers.appwakanda.db.room.entity.ContextItemEntity
import com.avengers.appwakanda.ui.indexmain.repository.NetworkState

open class BaseVMResult<T>(
        //api数据
        open var data: LiveData<T>,
        //网络状态
        open var netWorkState: LiveData<NetworkState>,
        //刷新数据
        open var refresh: () -> Unit
)



