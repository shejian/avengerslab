package com.avengers.weather.repository

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.avengers.templete.repository.Repository
import com.avengers.templete.viewModel.NetworkState
import com.avengers.templete.viewModel.Stating
import com.avengers.weather.api.WeatherApi
import com.avengers.weather.bean.CityWeatherBean
import com.avengers.weather.bean.FakeRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by duo.chen on 2018/7/13
 */
class NoDBWeatherRepository(private val api: WeatherApi) : Repository<FakeRequest,CityWeatherBean>() {

    var networkState: MutableLiveData<NetworkState> = MutableLiveData()

    var data: MutableLiveData<CityWeatherBean> = MutableLiveData()

    override fun request(request: FakeRequest): Stating<CityWeatherBean> {

        val weather = getWeather(request.city)

        val refreshTrigger = MutableLiveData<Int>()

        val refreshState = Transformations.switchMap(refreshTrigger) {
            getWeather(request.city)
            networkState
        }

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

    fun getWeather(city: String): MutableLiveData<CityWeatherBean> {

        networkState.value = NetworkState.LOADING

        api.getCityWeather(city).enqueue(object : Callback<CityWeatherBean> {
            override fun onFailure(call: Call<CityWeatherBean>?,t: Throwable?) {
                networkState.value = NetworkState.error("failed")
            }

            override fun onResponse(call: Call<CityWeatherBean>?,response: Response<CityWeatherBean>?) {
                if (response!!.body()?.status == 200) {
                    data.postValue(response.body())
                    networkState.postValue(NetworkState.LOADED)
                } else {
                    networkState.postValue(NetworkState.error("failed" + response.body()?.message))
                }
            }
        })

        return data
    }
}