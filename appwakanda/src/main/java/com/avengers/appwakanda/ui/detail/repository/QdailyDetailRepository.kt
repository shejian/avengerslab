package com.avengers.appwakanda.ui.detail.repository

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import com.avengers.appwakanda.db.room.dao.NewsDetailDao
import com.avengers.zombiebase.AppExecutors
import com.avengers.zombiebase.aacbase.BaseCallback
import com.avengers.zombiebase.aacbase.Repository
import com.avengers.appwakanda.bean.QdailyDetailBean
import com.avengers.appwakanda.bean.QdailyDetailReqParam
import com.avengers.appwakanda.webapi.QdailyApi

class QdailyDetailRepository(
        private val lifecycleOwner: LifecycleOwner,
        private val service: QdailyApi,
        appExecutors: AppExecutors) : Repository<QdailyDetailReqParam, QdailyDetailBean>(appExecutors.diskIO()) {

    /**
     * 请求数据
     */
    override fun refresh(args: QdailyDetailReqParam) {
        service.getQdailyDetail(args.keyWord!!).enqueue(BaseCallback<QdailyDetailBean>(lifecycleOwner, this))
    }

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: QdailyDetailRepository? = null
        fun getInstance(lifecycleOwner: LifecycleOwner,
                        api: QdailyApi,  appExecutors: AppExecutors) =
                instance ?: synchronized(this) {
                    instance
                            ?: QdailyDetailRepository(lifecycleOwner, api,  appExecutors).also {
                                instance = it
                            }
                }
    }

}
