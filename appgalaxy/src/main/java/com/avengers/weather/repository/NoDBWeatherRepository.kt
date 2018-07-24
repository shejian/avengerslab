package com.avengers.weather.repository

import com.avengers.weather.api.WeatherApi
import com.avengers.weather.bean.CityWeatherBean
import com.avengers.weather.bean.FakeRequest
import com.avengers.zombiebase.aacbase.NetworkState
import com.avengers.zombiebase.aacbase.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by duo.chen on 2018/7/13
 */
class NoDBWeatherRepository(private val api: WeatherApi) : Repository<FakeRequest,CityWeatherBean>(haveCache = false) {

    override fun refresh(args: FakeRequest) {
        api.getCityWeather(args.city).enqueue(object : Callback<CityWeatherBean> {
            override fun onFailure(call: Call<CityWeatherBean>?,t: Throwable?) {
                netWorkState.postValue(NetworkState.error("failed"))
            }

            override fun onResponse(call: Call<CityWeatherBean>?,response: Response<CityWeatherBean>?) {
                if (response!!.body()?.status.equals("200")) {
                    saveData(response.body()!!)
                    netWorkState.postValue(NetworkState.LOADED)
                } else {
                    netWorkState.postValue(NetworkState.error("failed" + response.body()?.message))
                }
            }
        })

    }
}