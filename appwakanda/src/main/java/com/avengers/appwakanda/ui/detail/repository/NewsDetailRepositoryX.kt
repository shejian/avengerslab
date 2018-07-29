package com.avengers.appwakanda.ui.detail.repository

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.avengers.appwakanda.bean.NewsDetailBean
import com.avengers.appwakanda.db.room.dao.NewsDetailDao
import com.avengers.appwakanda.ui.detail.vm.IReqDetailParam
import com.avengers.appwakanda.webapi.SmartisanApi
import com.avengers.zombiebase.AppExecutors
import com.avengers.zombiebase.aacbase.BaseCallback
import com.avengers.zombiebase.aacbase.Repository
import android.arch.lifecycle.Transformations.switchMap
import com.avengers.appwakanda.db.room.entity.NewsDetailEntity
import com.avengers.zombiebase.LogU

class NewsDetailRepositoryX(
        private val lifecycleOwner: LifecycleOwner,
        private val service: SmartisanApi,
        private val newsDetailDao: NewsDetailDao,
        appExecutors: AppExecutors) : Repository<IReqDetailParam, NewsDetailBean>(appExecutors.diskIO(), true) {


    /**
     * 请求数据
     */
    override fun refresh(args: IReqDetailParam) {
        service.getDetailInfo(args.lineshow, 0, 20).enqueue(BaseCallback<NewsDetailBean>(lifecycleOwner, this))
    }


    /**
     * 在需要缓存时必须实现
     */
    override fun addToDb(t: NewsDetailBean) {
        t.let {
            newsDetailDao.deleteAll()
            LogU.d("GithubLocalCache", "inserting ${t.data!!.list!!.size} repos")
            newsDetailDao.insertItemDetail(t.data!!.list)
        }
    }

    /**
     * 在需要缓存时必须实现
     */
    override fun queryFromDb(args: IReqDetailParam): LiveData<NewsDetailBean>? {

        var entitys = newsDetailDao.queryDetail()

        return switchMap(entitys) {
            val bean: MutableLiveData<NewsDetailBean> = MutableLiveData()

            val newsDetailBean = NewsDetailBean()

            val data = NewsDetailBean.ContextData()

            val list = ArrayList<NewsDetailEntity>()

            list.add(it)

            data.list=list

            newsDetailBean.data=data

            bean.postValue(newsDetailBean)

            bean
        }

    }


    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: NewsDetailRepositoryX? = null

        fun getInstance(lifecycleOwner: LifecycleOwner,
                        api: SmartisanApi, plantDao: NewsDetailDao, appExecutors: AppExecutors) =
                instance ?: synchronized(this) {
                    instance
                            ?: NewsDetailRepositoryX(lifecycleOwner, api, plantDao, appExecutors).also {
                                instance = it
                            }
                }
    }

}
