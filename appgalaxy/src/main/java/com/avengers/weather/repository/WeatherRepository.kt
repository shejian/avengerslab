package com.avengers.weather.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.avengers.templete.repository.Repository
import com.avengers.templete.viewModel.Stating
import com.avengers.weather.api.WeatherApi
import com.avengers.weather.bean.CityWeatherBean
import com.avengers.weather.bean.FakeRequest
import com.avengers.weather.db.WeatherDao
import com.avengers.templete.viewModel.NetworkState
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
                        private val ioExecutor: Executor) : Repository<FakeRequest,CityWeatherBean>() {

    var networkState: MutableLiveData<NetworkState> = MutableLiveData()

    override fun request(request: FakeRequest): Stating<CityWeatherBean> {

        val instance = Calendar.getInstance()
        instance.set(Calendar.HOUR_OF_DAY,0)
        instance.set(Calendar.MINUTE,0)
        instance.set(Calendar.SECOND,0)
        instance.set(Calendar.MILLISECOND,0)

        var time: Long = instance.timeInMillis

        val weather = db.getWeather(request.city,time)

        val refreshTrigger = MutableLiveData<Int>()

        val refreshState = Transformations.switchMap(refreshTrigger) {
            getWeatherFromNet(request.city)
        }

        getWeatherFromNet(request.city)

        var state = Stating(response = weather,
                netWorkState = networkState,
                refreshState = refreshState,
                refresh = {
                    refreshTrigger.value = null
                },
                retry = {
                    refreshTrigger.value = null
                })

        return state
    }

    fun getWeatherFromNet(city: String): LiveData<NetworkState> {

        networkState.value = NetworkState.LOADING

        api.getCityWeather(city).enqueue(object : Callback<CityWeatherBean> {
            override fun onFailure(call: Call<CityWeatherBean>?,t: Throwable?) {
                networkState.value = NetworkState.error("failed")

            }

            override fun onResponse(call: Call<CityWeatherBean>?,response: Response<CityWeatherBean>?) {

                ioExecutor.execute {
                    if (response!!.body()?.status == 200) {
                        db.insert(response.body()!!)
                        networkState.postValue(NetworkState.LOADED)
                    } else{
                        networkState.postValue(NetworkState.error("failed" + response.body()?.message))
                    }
                }
            }
        })
        return networkState
    }
}