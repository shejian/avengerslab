package com.avengers.appwakanda.ui.indexmain.vm

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.avengers.appwakanda.db.room.entity.ContextItemEntity
import com.avengers.appwakanda.db.room.RoomHelper
import com.avengers.appwakanda.ui.indexmain.repository.IndexRepository
import com.avengers.zombiebase.ApplicationInitBase

class IndexListViewModel : ViewModel() {

    private var indexListLiveData: LiveData<List<ContextItemEntity>>? = null

    fun getIndexListLiveData(): LiveData<List<ContextItemEntity>> {
        return indexListLiveData!!
    }

    var indexpeository: IndexRepository? = null

    fun init() {
        indexpeository = IndexRepository(ApplicationInitBase.getInstanceExecutors().diskIO(), RoomHelper.indexDataDao())
        indexListLiveData = indexpeository!!.getIndexListData2(0, 20)
    }


}