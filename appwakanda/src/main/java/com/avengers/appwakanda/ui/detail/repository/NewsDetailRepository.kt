package com.avengers.appwakanda.ui.detail.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.avengers.appwakanda.WakandaModule
import com.avengers.appwakanda.bean.NewsDetailBean
import com.avengers.appwakanda.db.room.RoomHelper
import com.avengers.appwakanda.db.room.dao.IndexDataCache
import com.avengers.appwakanda.db.room.dao.IndexDataDao
import com.avengers.appwakanda.db.room.dao.NewsDetailDao
import com.avengers.appwakanda.db.room.entity.NewsDetailEntity
import com.avengers.appwakanda.ui.common.BaseVMResult
import com.avengers.appwakanda.ui.indexmain.repository.NetworkState
import com.avengers.appwakanda.webapi.SmartisanApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NewsDetailRepository(
        private val service: SmartisanApi,
        private val newsDetailDao: NewsDetailDao) {

    var netWorkState = MutableLiveData<NetworkState>()

    /**
     * 组装
     */
    fun getDetailData(qid: String): BaseVMResult<NewsDetailEntity> {
        //本地数据缓存
        val dataSource = newsDetailDao.quryDetail(qid)

        refreshData(qid)

        //封装数据并传给上层
        return BaseVMResult<NewsDetailEntity>(
                dataSource,
                netWorkState) {
            refreshData(qid)
        }
    }

    /**
     * 网络请求数据
     */
    fun refreshData(qid: String) {
        netWorkState.postValue(NetworkState.LOADING)
        service.getDetailInfo("line/show", 0, 20).enqueue(object : Callback<NewsDetailBean> {

            override fun onFailure(call: Call<NewsDetailBean>?, t: Throwable?) {
                netWorkState.postValue(NetworkState.error(t.toString()))

            }

            override fun onResponse(call: Call<NewsDetailBean>?, response: Response<NewsDetailBean>?) {
                var listData = response?.body()?.data?.list
                var detail = listData?.get(1)
                WakandaModule.appExecutors!!.diskIO()?.execute {
                    addDetailToDb(detail!!)
                }
                netWorkState.postValue(NetworkState.LOADED)
            }
        })
    }

    /**
     * 保存数据
     */
    fun addDetailToDb(newsitem: NewsDetailEntity) {
        newsitem.let {
            newsDetailDao.deleteAll()
            newsDetailDao.insertItemDetail(newsitem)
        }
    }
}
