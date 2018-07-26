package com.avengers.appwakanda.ui.detail.repository

import android.arch.lifecycle.LiveData
import com.avengers.appwakanda.bean.NewsDetailBean
import com.avengers.appwakanda.db.room.dao.NewsDetailDao
import com.avengers.appwakanda.db.room.entity.NewsDetailEntity
import com.avengers.appwakanda.ui.detail.vm.IReqDetailParam
import com.avengers.appwakanda.webapi.SmartisanApi
import com.avengers.zombiebase.AppExecutors
import com.avengers.zombiebase.aacbase.NetworkState
import com.avengers.zombiebase.aacbase.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NewsDetailRepositoryX(
        private val service: SmartisanApi,
        private val newsDetailDao: NewsDetailDao,
        private val appExecutors: AppExecutors) : Repository<IReqDetailParam, NewsDetailEntity>(true) {

    /**
     * 请求数据
     */
    override fun refresh(args: IReqDetailParam) {
        service.getDetailInfo(args.lineshow, 0, 20).enqueue(object : Callback<NewsDetailBean> {

            override fun onFailure(call: Call<NewsDetailBean>?, t: Throwable?) {
                netWorkState.postValue(NetworkState.error(t.toString()))
            }

            override fun onResponse(call: Call<NewsDetailBean>?, response: Response<NewsDetailBean>?) {
                val detail = tran.invoke(response?.body()!!)
                appExecutors.diskIO().execute {
                    saveData(detail!!)
                    netWorkState.postValue(NetworkState.LOADED)
                }
            }
        })
    }


    var tran: (NewsDetailBean) -> NewsDetailEntity? = {
        val listData = it?.data?.list
        listData?.get(2)
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
    override fun queryFromDb(args: IReqDetailParam): LiveData<NewsDetailEntity>? {
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
