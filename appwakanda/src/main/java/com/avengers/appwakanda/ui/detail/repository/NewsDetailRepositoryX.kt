package com.avengers.appwakanda.ui.detail.repository

import android.arch.lifecycle.LiveData
import com.avengers.appwakanda.bean.NewsDetailBean
import com.avengers.appwakanda.db.room.dao.NewsDetailDao
import com.avengers.appwakanda.db.room.entity.NewsDetailEntity
import com.avengers.zombiebase.accbase.NetworkState
import com.avengers.appwakanda.webapi.SmartisanApi
import com.avengers.zombiebase.AppExecutors
import com.avengers.zombiebase.accbase.AbsRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NewsDetailRepositoryX(
        private val service: SmartisanApi,
        private val newsDetailDao: NewsDetailDao,
        private val appExecutors: AppExecutors) : AbsRepository<NewsDetailEntity>(true) {

    /**
     * 请求数据
     */
    override fun refresh(vararg args: Any) {
        service.getDetailInfo("line/show", 0, 20).enqueue(object : Callback<NewsDetailBean> {

            override fun onFailure(call: Call<NewsDetailBean>?, t: Throwable?) {
                netWorkState.postValue(NetworkState.error(t.toString()))
            }

            override fun onResponse(call: Call<NewsDetailBean>?, response: Response<NewsDetailBean>?) {
                val listData = response?.body()?.data?.list
                val detail = listData?.get(2)
                appExecutors.diskIO().execute {
                    saveData(detail!!)
                    netWorkState.postValue(NetworkState.LOADED)
                }
            }
        })
    }

    /**
     * 在需要缓存时必须实现
     */
    override fun addToDb(newsitem: NewsDetailEntity) {
        newsitem.let {
            newsDetailDao.deleteAll()
            newsDetailDao.insertItemDetail(newsitem)
        }
    }

    /**
     * 在需要缓存时必须实现
     */
    override fun queryFromDb(vararg args: Any): LiveData<NewsDetailEntity> {
        return newsDetailDao.quryDetail()

    }

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: NewsDetailRepositoryX? = null

        fun getInstance(api: SmartisanApi, plantDao: NewsDetailDao, appExecutors: AppExecutors) =
                instance ?: synchronized(this) {
                    instance ?: NewsDetailRepositoryX(api, plantDao, appExecutors).also {
                        instance = it
                    }
                }
    }

}
