package com.avengers.weather.vm

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations.map
import android.arch.lifecycle.Transformations.switchMap
import android.arch.lifecycle.ViewModel
import com.avengers.weather.bean.CityWeatherBean
import com.avengers.weather.repository.NetworkState
import com.avengers.weather.repository.WeatherRepository

/**
 * Created by duo.chen on 2018/7/9
 */
class WeatherViewModel(private val repo: WeatherRepository) : ViewModel() {

    val cityName = MutableLiveData<String>()

    val repoResult = map(cityName) {
        repo.getWeatherByCity(it)
    }
    val networkState: LiveData<NetworkState>? = switchMap(repoResult) { it.netWorkState }
    val post: LiveData<CityWeatherBean> = switchMap(repoResult) { it.weather }
    val refreshState: LiveData<NetworkState>? = switchMap(repoResult) { it.refreshState }

    fun request(city: String) {
        cityName.value = city
    }


    fun refresh() {
        repoResult.value?.refresh?.invoke()
    }

    fun retry() {
        repoResult.value?.retry?.invoke()
    }

}