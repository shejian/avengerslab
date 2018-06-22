package com.avengers.appwakanda.ui.indexmain.vm

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.avengers.appwakanda.db.room.MyDataBase
import com.avengers.appwakanda.ui.indexmain.repository.IndexRepository
import com.avengers.zombiebase.ApplicationInitBase
import com.avengers.zombiebase.BaseApplication

class IndexListViewModel : ViewModel() {

    private var indexListLiveData: LiveData<List<ContextItemBean>>? = null

    fun getIndexListLiveData(): LiveData<List<ContextItemBean>> {
        return indexListLiveData!!
    }

    var indexpeository: IndexRepository? = null

    fun init() {
        var myDataBase = MyDataBase.getInstance(BaseApplication.getMaApplication(), ApplicationInitBase.getInstanceExecutors())
        indexpeository = IndexRepository(
                ApplicationInitBase.getInstanceExecutors().diskIO(), myDataBase.indexDataDao()!!)
        indexListLiveData = indexpeository!!.getIndexListData2(0, 20)
    }


}