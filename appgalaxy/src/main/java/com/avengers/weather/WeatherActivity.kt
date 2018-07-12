package com.avengers.weather

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.avengers.R
import com.avengers.databinding.ActivityWeatherBinding
import com.avengers.appgalaxy.db.room.RoomHelper
import com.avengers.weather.api.Api
import com.avengers.weather.api.WeatherApi
import com.avengers.weather.repository.NetworkState
import com.avengers.weather.repository.Status
import com.avengers.weather.repository.WeatherRepository
import com.avengers.weather.vm.WeatherViewModel
import kotlinx.android.synthetic.main.activity_weather.*
import java.util.concurrent.Executors

/**
 * Created by duo.chen on 2018/7/9
 */
@Route(path = "/galaxy/weatherActivity")
class WeatherActivity : AppCompatActivity() {

    private lateinit var weatherViewModel: WeatherViewModel
    var contentView: ActivityWeatherBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        contentView = DataBindingUtil.setContentView(this,R.layout.activity_weather)

        weatherViewModel = getViewModel()

        contentView?.model = weatherViewModel
        contentView?.setLifecycleOwner(this)


        initWithViewModel(weatherViewModel)

        initSwipeRefresh()

        weatherViewModel.request("上海")

    }

    fun initSwipeRefresh() {

        weatherViewModel.refreshState?.observe(this,Observer {
            swipe_refresh.isRefreshing = it == NetworkState.LOADING
        })

        swipe_refresh.setOnRefreshListener { weatherViewModel.refresh() }
    }

    private fun initWithViewModel(viewModel: WeatherViewModel) {
        viewModel.post.observe(this,Observer {
            contentView!!.cityWeather = it

        })
        viewModel.refreshState?.observe(this,Observer {

        })

        viewModel.networkState?.observe(this,Observer {

            if (it?.status == Status.FAILED) {
                retry.visibility = View.VISIBLE
            } else {
                retry.visibility = View.GONE
            }
        })
    }

    fun click(view: View) {
        weatherViewModel.refresh()
    }

    private fun getViewModel(): WeatherViewModel {
        return ViewModelProviders.of(this,object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val weatherRepository = WeatherRepository(getApi(),RoomHelper.weatherDao(),Executors.newSingleThreadExecutor())
                @Suppress("UNCHECKED_CAST")
                return WeatherViewModel(weatherRepository) as T
            }
        }).get(WeatherViewModel::class.java)
    }

    private fun getApi(): WeatherApi {
        return Api.getWeatherApi()
    }


}