package com.avengers.appwakanda.ui.indexmain.repository

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.LivePagedListBuilder
import android.util.Log
import com.avengers.appwakanda.bean.IndexReaderListBean
import com.avengers.appwakanda.db.room.dao.IndexDataDao
import com.avengers.appwakanda.ui.indexmain.vm.ItemResult
import com.bty.retrofit.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor

class IndexRepository(var executor: Executor, var dao: IndexDataDao) {
    companion object {
        private const val NETWORK_PAGE_SIZE = 50
        private const val DB_PAGE_SIZE = 10
    }

    private val networkErrors = MutableLiveData<String>()

    fun getIndexListData(offiset: Int, pageSize: Int): ItemResult   /*: LiveData<PagedList<ContextItemEntity>>*/ {
        //refreshData(offiset, pageSize)
        var dataSourceFactory = dao.quryAllItem()

        var mLiveData = LivePagedListBuilder(dataSourceFactory, DB_PAGE_SIZE).build()

        var sd = ItemResult(mLiveData, networkErrors)

        return sd
        // return dao!!.quryInfos()
    }

    fun refreshData(offiset: Int, pageSize: Int) {

        Api.getSmartApi().getSmtIndex("line/show", offiset, pageSize).enqueue(object : Callback<IndexReaderListBean> {

            override fun onResponse(call: Call<IndexReaderListBean>, response: Response<IndexReaderListBean>) {
                Log.d("shejian", "onResponse到数据")
                val indexReaderListBean = response.body()
                val listData = indexReaderListBean!!.data!!.list
                executor.execute {
                    for (bb in listData!!) {
                        Log.d("shejian", "   insertInfo" + bb.getTitle())
                        dao!!.insertInfo(bb)
                    }
                }

            }

            override fun onFailure(call: Call<IndexReaderListBean>, t: Throwable) {

            }
        })

        /*    executor!!.execute {
                try {
                    var response = Api.getSmartApi().getSmtIndex("line/show", offiset, pageSize).execute()
                    var listData = response.body()!!.data!!.list
                    if (listData!!.isEmpty()) {
                        return@execute
                    }
                    for (bb in listData!!) {
                        dao!!.insertInfo(bb)
                    }

                } catch (a: Exception) {


                }
            }*/

    }

}