package com.avengers.weather.vm

import com.avengers.templete.viewModel.BaseViewModel
import com.avengers.weather.bean.CityWeatherBean
import com.avengers.weather.bean.FakeRequest
import com.avengers.weather.repository.WeatherRepository

/**
 * Created by duo.chen on 2018/7/9
 */
class WeatherViewModel(repo: WeatherRepository) : BaseViewModel<FakeRequest,CityWeatherBean>(repo)