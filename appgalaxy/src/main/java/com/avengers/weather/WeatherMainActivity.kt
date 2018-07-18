package com.avengers.weather

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.avengers.R
import com.avengers.weather.ui.NoDBWeatherActivity
import com.avengers.weather.ui.WeatherActivity
import com.avengers.zombiebase.matisse.MatisseChooser

/**
 * Created by duo.chen on 2018/7/13
 */
@Route(path = "/galaxy/weatherMainActivity")
class WeatherMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.weather_main)
    }

    fun buttonClickNoDbWeather(view: View) {
        var intent = Intent(this,NoDBWeatherActivity::class.java)
        startActivity(intent)
    }

    fun buttonClickWithDbWeather(view: View) {
        var intent = Intent(this,WeatherActivity::class.java)
        startActivity(intent)
    }

    fun matisseChooser(view: View) {
        MatisseChooser.choose(this,1)
    }
}