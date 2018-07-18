package com.avengers.appwakanda.ui.detail.repository

import android.arch.lifecycle.MutableLiveData
import com.avengers.appwakanda.bean.NewsDetailBean
import com.avengers.appwakanda.db.room.dao.NewsDetailDao
import com.avengers.appwakanda.db.room.entity.NewsDetailEntity
import com.avengers.zombiebase.accbase.NetworkState
import com.avengers.appwakanda.webapi.SmartisanApi
import com.avengers.zombiebase.AppExecutors
import com.avengers.zombiebase.accbase.BaseCoreResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NewsDetailRepository(
        private val service: SmartisanApi,
        private val newsDetailDao: NewsDetailDao,
        private val appExecutors: AppExecutors) {

    var netWorkState = MutableLiveData<NetworkState>()

    /**
     * 组装livedata数据，包含网络状态，网络数据源，以及网络请求函数
     */
    fun getDetailData(qid: String): BaseCoreResult<NewsDetailEntity> {
        //本地数据缓存
        val dataSource = newsDetailDao.quryDetail()

        //触发请求
        refreshData(qid)

        //封装数据并传给上层
        return BaseCoreResult<NewsDetailEntity>(dataSource, netWorkState) {
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
                var detail = listData?.get(0)
                appExecutors.diskIO().execute {
                    addDetailToDb(detail!!)
                    netWorkState.postValue(NetworkState.LOADED)
                }
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

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: NewsDetailRepository? = null

        fun getInstance(api: SmartisanApi, plantDao: NewsDetailDao, appExecutors: AppExecutors) =
                instance ?: synchronized(this) {
                    instance ?: NewsDetailRepository(api, plantDao, appExecutors).also {
                        instance = it
                    }
                }
    }

}
