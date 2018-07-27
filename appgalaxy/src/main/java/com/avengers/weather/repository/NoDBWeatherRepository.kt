package com.avengers.weather.repository

import android.arch.lifecycle.LifecycleOwner
import com.avengers.weather.api.WeatherApi
import com.avengers.weather.bean.CityWeatherBean
import com.avengers.weather.bean.FakeRequest
import com.avengers.zombiebase.aacbase.BaseCallback
import com.avengers.zombiebase.aacbase.Repository
import java.util.concurrent.Executor

/**
 * Created by duo.chen on 2018/7/13
 */
class NoDBWeatherRepository(
        private val lifecycleOwner: LifecycleOwner,
        private val api: WeatherApi,
        ioExecutor: Executor) : Repository<FakeRequest,CityWeatherBean>(ioExecutor,haveCache = false) {

    override fun refresh(args: FakeRequest) {
        api.getCityWeather(args.city).enqueue(BaseCallback(lifecycleOwner,this))

    }
}