package com.avengers.weather.bean

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.avengers.zombiebase.aacbase.IBeanResponse
import com.bty.retrofit.net.bean.JsonBeanResponse

/**
 * Created by duo.chen on 2018/7/9
 */

@Entity(tableName = "cityWeather")
class CityWeatherBean : IBeanResponse() {

    @PrimaryKey
    var date: Long = 0

    var city: String = ""

    var count: String = ""

    var time: Long = System.currentTimeMillis()

    @Embedded
    var data: Data? = null

}

class Data {
    var shidu: String = ""
    var pm25: String = ""
    var pm10: String = ""
    var quality: String = ""
    var wendu: String = ""
    var ganmao: String = ""

}