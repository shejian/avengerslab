package com.avengers.appwakanda.ui.indexmain.vm

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import com.avengers.appwakanda.db.room.entity.ContextItemEntity

data class ItemResult(
        val data: LiveData<PagedList<ContextItemEntity>>,
        val newworkError: LiveData<String>
)



