package com.avengers.weather.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.avengers.weather.bean.CityWeatherBean

/**
 * Created by duo.chen on 2018/7/9
 */
@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cityWeatherBean: CityWeatherBean)

    @Query("SELECT * FROM cityWeather WHERE city= :city AND time > :time")
    fun getWeather(city: String,time: Long): LiveData<CityWeatherBean>

}