package com.avengers.weather.vm

import com.avengers.templete.viewModel.BaseViewModel
import com.avengers.weather.bean.CityWeatherBean
import com.avengers.weather.bean.FakeRequest
import com.avengers.weather.repository.NoDBWeatherRepository

/**
 * Created by duo.chen on 2018/7/13
 */
class NoDbweatherViewModel(repo: NoDBWeatherRepository) : BaseViewModel<FakeRequest,CityWeatherBean>(repo)