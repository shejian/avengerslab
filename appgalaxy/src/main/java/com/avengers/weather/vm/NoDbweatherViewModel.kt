package com.avengers.weather.vm

import com.avengers.weather.bean.CityWeatherBean
import com.avengers.weather.bean.FakeRequest
import com.avengers.weather.repository.NoDBWeatherRepository
import com.avengers.zombiebase.aacbase.BaseViewModel

/**
 * Created by duo.chen on 2018/7/13
 */
class NoDbweatherViewModel(repo: NoDBWeatherRepository) : BaseViewModel<FakeRequest,CityWeatherBean>(repo)