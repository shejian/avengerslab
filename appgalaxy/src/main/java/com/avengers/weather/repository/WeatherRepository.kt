package com.avengers.weather.repository

import android.arch.lifecycle.LiveData
import com.avengers.weather.api.WeatherApi
import com.avengers.weather.bean.CityWeatherBean
import com.avengers.weather.bean.FakeRequest
import com.avengers.weather.db.WeatherDao
import com.avengers.zombiebase.aacbase.NetworkState
import com.avengers.zombiebase.aacbase.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.concurrent.Executor

/**
 * Created by duo.chen on 2018/7/13
 */
class WeatherRepository(private val api: WeatherApi,
                        private val db: WeatherDao,
                        private val ioExecutor: Executor) : Repository<FakeRequest,CityWeatherBean>(haveCache = true) {

    override fun refresh(args: FakeRequest) {

        api.getCityWeather(args.city).enqueue(object : Callback<CityWeatherBean> {
            override fun onFailure(call: Call<CityWeatherBean>?,t: Throwable?) {
                netWorkState.postValue(NetworkState.error("failed"))
            }

            override fun onResponse(call: Call<CityWeatherBean>?,response: Response<CityWeatherBean>?) {

                ioExecutor.execute {
                    if (response!!.body()?.status == 200) {
                        saveData(response.body()!!)
                        netWorkState.postValue(NetworkState.LOADED)
                    } else {
                        netWorkState.postValue(NetworkState.error("failed" + response.body()?.message))
                    }
                }
            }
        })
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