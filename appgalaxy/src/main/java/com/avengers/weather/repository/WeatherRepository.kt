package com.avengers.weather.repository

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import com.avengers.weather.api.WeatherApi
import com.avengers.weather.bean.CityWeatherBean
import com.avengers.weather.bean.FakeRequest
import com.avengers.weather.db.WeatherDao
import com.avengers.zombiebase.aacbase.BaseCallback
import com.avengers.zombiebase.aacbase.Repository
import com.avengers.zombiebase.ui.LaeView
import java.util.*
import java.util.concurrent.Executor

/**
 * Created by duo.chen on 2018/7/13
 */
class WeatherRepository(
        private val lifecycleOwner: LifecycleOwner,
        private val laeView: LaeView,
        private val api: WeatherApi,
        private val db: WeatherDao,
        ioExecutor: Executor) : Repository<FakeRequest,CityWeatherBean>(ioExecutor,haveCache = true) {

    override fun refresh(args: FakeRequest) {
        api.getCityWeather(args.city).enqueue(BaseCallback(lifecycleOwner,laeView,this))
    }

    override fun addToDb(t: CityWeatherBean) {
        db.insert(t)
    }

    override fun queryFromDb(args: FakeRequest): LiveData<CityWeatherBean>? {
        val instance = Calendar.getInstance()
        instance.set(Calendar.HOUR_OF_DAY,0)
        instance.set(Calendar.MINUTE,0)
        instance.set(Calendar.SECOND,0)
        instance.set(Calendar.MILLISECOND,0)

        var time: Long = instance.timeInMillis
        return db.getWeather(args.city,time)
    }
}