package com.avengers.appwakanda.ui.indexmain.vm

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import com.avengers.appwakanda.db.room.entity.ContextItemEntity
import com.avengers.appwakanda.db.room.RoomHelper
import com.avengers.appwakanda.ui.indexmain.repository.IndexRepository
import com.avengers.zombiebase.ApplicationInitBase

class IndexListViewModel : ViewModel() {

    var indexListLiveData: LiveData<PagedList<ContextItemEntity>>? = null


    var indexpeository: IndexRepository? = null

    fun init() {
        indexpeository = IndexRepository(ApplicationInitBase.getInstanceExecutors().diskIO(), RoomHelper.indexDataDao())
        refreshData()
    }

    var aSDD: ItemResult? = null

    fun refreshData() {
        aSDD = indexpeository!!.getIndexListData(0, 20)
        indexListLiveData = aSDD?.data//indexpeository!!.getIndexListData(0, 20)
    }


}