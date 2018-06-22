package com.avengers.appwakanda.ui.indexmain.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.avengers.appwakanda.db.dao.IndexDataDao
import com.avengers.appwakanda.ui.indexmain.vm.ContextItemBean
import com.avengers.appwakanda.ui.indexmain.vm.IndexReaderListBean
import com.bty.retrofit.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.util.concurrent.Executor

class IndexRepository {

    var executor: Executor? = null
    var dao: IndexDataDao? = null

    constructor(executor: Executor, dao: IndexDataDao) {
        this.executor = executor
        this.dao = dao
    }

    fun getIndexListData(offiset: Int, pageSize: Int): MutableLiveData<List<ContextItemBean>> {


        var indexListLiveData: MutableLiveData<List<ContextItemBean>> = MutableLiveData()

        Api.getSmartApi().getSmtIndex("line/show", offiset, pageSize).enqueue(object : Callback<IndexReaderListBean> {

            override fun onResponse(call: Call<IndexReaderListBean>, response: Response<IndexReaderListBean>) {
                Log.d("shejian", "onResponse到数据")
                var indexReaderListBean = response.body()
                indexListLiveData.value = indexReaderListBean!!.data!!.list
            }

            override fun onFailure(call: Call<IndexReaderListBean>, t: Throwable) {
            }
        })
        return indexListLiveData
    }

    fun getIndexListData2(offiset: Int, pageSize: Int): LiveData<List<ContextItemBean>> {
        refreshData(offiset, pageSize)
        return dao!!.quryInfos()

    }

    fun refreshData(offiset: Int, pageSize: Int) {
        executor!!.execute {
            try {
                var response = Api.getSmartApi().getSmtIndex("line/show", offiset, pageSize).execute()
                var listData = response.body()!!.data!!.list
                if (listData!!.isEmpty()) {
                    return@execute
                }
                for (bb in listData!!) {
                    dao!!.insertInfo(bb)
                }
            }catch (a:Exception){


            }
        }

    }

}