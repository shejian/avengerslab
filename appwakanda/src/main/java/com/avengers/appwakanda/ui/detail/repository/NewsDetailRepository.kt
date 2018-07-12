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
import com.avengers.appwakanda.webapi.SmartisanApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NewsDetailRepository(
        private val service: SmartisanApi,
        private val newsDetailDao: NewsDetailDao
) {

    fun getDetailData(qid: String) {
        //边界回调 略

        //本地数据缓存
        var dataSource = newsDetailDao.quryDetail(qid)

        var liveData = reqData(qid)
        //请求数据
        //封装数据并传给上层

        //return BaseVMResult<NewsDetailEntity>(   liveData, )
    }

    fun reqData(qid: String): LiveData<NewsDetailEntity> {
        val liveData = MutableLiveData<NewsDetailEntity>()
        service.getDetailInfo("", 0, 20).enqueue(object : Callback<NewsDetailBean> {

            override fun onFailure(call: Call<NewsDetailBean>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<NewsDetailBean>?, response: Response<NewsDetailBean>?) {
                var listData = response?.body()?.data?.list
                var detail = listData?.get(1)
                WakandaModule.appExecutors!!.diskIO()?.execute {
                    addDetailToDb(detail!!)
                }
            }

        })

        return liveData
    }


    fun addDetailToDb(newsitem: NewsDetailEntity) {
        newsitem.let {
            newsDetailDao.insertItemDetail(newsitem)
        }
    }
}
