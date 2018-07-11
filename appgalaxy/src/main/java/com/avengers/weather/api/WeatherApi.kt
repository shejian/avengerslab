package com.avengers.weather.api

import com.avengers.weather.bean.CityWeatherBean
import com.bty.annotation.Service
import com.bty.retrofit.net.bean.JsonBeanResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by duo.chen on 2018/7/9
 */
@Service(baseUrl = "https://www.sojson.com/")
interface WeatherApi {

    @GET("/open/api/weather/json.shtml")
    fun getCityWeather(@Query("city") city: String): Call<CityWeatherBean>

}