package com.avengers.weather.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.avengers.appgalaxy.db.room.RoomHelper
import com.avengers.weather.api.WeatherApi
import com.avengers.weather.bean.CityWeatherBean
import com.avengers.weather.db.WeatherDao
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.DayOfWeek
import java.util.*
import java.util.concurrent.Executor

/**
 * Created by duo.chen on 2018/7/9
 */
class WeatherRepository(private val api: WeatherApi,
                        private val db: WeatherDao,
                        private val ioExecutor: Executor) {

    var networkState: MutableLiveData<NetworkState> = MutableLiveData()

    fun getWeatherByCity(city: String): State<CityWeatherBean> {

        val instance = Calendar.getInstance()
        instance.set(Calendar.HOUR_OF_DAY,0)
        instance.set(Calendar.MINUTE,0)
        instance.set(Calendar.SECOND,0)
        instance.set(Calendar.MILLISECOND,0)

        var time : Long = instance.timeInMillis

        val weather = db.getWeather(city,time)

        val refreshTrigger = MutableLiveData<Int>()

        val refreshState = Transformations.switchMap(refreshTrigger) {
            getWeatherFromNet(city)
        }

        return com.avengers.weather.repository.State(
                weather = weather,
                netWorkState = networkState,
                refreshState = refreshState,
                refresh = {
                    refreshTrigger.value = null
                },
                retry = {
                    refreshTrigger.value = null
                }
        )
    }

    fun getWeatherFromNet(city: String): LiveData<NetworkState> {

        networkState.value = NetworkState.LOADING

        api.getCityWeather(city).enqueue(object : Callback<CityWeatherBean> {
            override fun onFailure(call: Call<CityWeatherBean>?,t: Throwable?) {
                networkState.value = NetworkState.error("failed")

            }

            override fun onResponse(call: Call<CityWeatherBean>?,response: Response<CityWeatherBean>?) {

                ioExecutor.execute {
                    db.insert(response?.body()!!)
                    networkState.postValue(NetworkState.LOADED)
                }
            }
        })
        return networkState
    }

}