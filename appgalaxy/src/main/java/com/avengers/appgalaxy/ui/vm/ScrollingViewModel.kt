package com.avengers.appgalaxy.ui.vm

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.*
import android.util.Log
import com.avengers.appgalaxy.db.room.RoomHelper
import com.avengers.appgalaxy.db.room.entity.ContextItemEntity

class ScrollingViewModel : ViewModel() {

    private var liveDataModel: LiveData<PagedList<ContextItemEntity>>? = null

    fun initModel() {
        val dataSource = RoomHelper.indexDataDao().quryInfos()


       // var mySource= ItemKeyedDataSource<Int,PagedList<ContextItemEntity>>

        val config = PagedList.Config.Builder()
                .setInitialLoadSizeHint(20)
                .setPageSize(10)
                .setEnablePlaceholders(false)
                .setPrefetchDistance(2)
                .build()
        liveDataModel = LivePagedListBuilder(dataSource, config).build()
    }

    fun getLiveData(): LiveData<PagedList<ContextItemEntity>>? {
        return this.liveDataModel
    }






}