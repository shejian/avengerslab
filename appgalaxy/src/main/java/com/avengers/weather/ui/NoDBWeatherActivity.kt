package com.avengers.weather.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.avengers.R
import com.avengers.databinding.NodbActivityWeatherBinding
import com.avengers.templete.viewModel.NetworkState
import com.avengers.templete.viewModel.Status
import com.avengers.weather.api.Api
import com.avengers.weather.api.WeatherApi
import com.avengers.weather.bean.FakeRequest
import com.avengers.weather.repository.NoDBWeatherRepository
import com.avengers.weather.vm.NoDbweatherViewModel
import com.avengers.zombiebase.BaseActivity
import kotlinx.android.synthetic.main.activity_weather.*

/**
 * Created by duo.chen on 2018/7/9
 */
class NoDBWeatherActivity : BaseActivity() {
    override fun reloadData() {
        weatherViewModel.refresh()
    }

    private lateinit var weatherViewModel: NoDbweatherViewModel
    var contentView: NodbActivityWeatherBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        contentView = DataBindingUtil.setContentView(this,R.layout.nodb_activity_weather)

        weatherViewModel = getViewModel()

        contentView?.model = weatherViewModel
        contentView?.setLifecycleOwner(this)


        initWithViewModel(weatherViewModel)

        initSwipeRefresh()

        var request = FakeRequest("上海")

        weatherViewModel.request(request)

    }

    fun initSwipeRefresh() {

        weatherViewModel.refreshState?.observe(this,Observer {
            swipe_refresh.isRefreshing = it == NetworkState.LOADING
        })

        swipe_refresh.setOnRefreshListener { weatherViewModel.refresh() }
    }

    private fun initWithViewModel(viewModel: NoDbweatherViewModel) {
        viewModel.response.observe(this,Observer {
            contentView!!.cityWeather = it

        })
        viewModel.refreshState?.observe(this,Observer {
            if (it?.status == Status.FAILED) {
                Toast.makeText(this,it.msg,Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.networkState?.observe(this,Observer {
            if (it?.status == Status.RUNNING) {
                showLoadTransView()
            } else if(it?.status == Status.FAILED){
                showErrorView("")
            } else {
                showContentView()
            }
        })
    }

    fun click(view: View) {
        weatherViewModel.refresh()
    }

    private fun getViewModel(): NoDbweatherViewModel {
        return ViewModelProviders.of(this,object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val weatherRepository = NoDBWeatherRepository(getApi())
                @Suppress("UNCHECKED_CAST")
                return NoDbweatherViewModel(weatherRepository) as T
            }
        }).get(NoDbweatherViewModel::class.java)
    }

    private fun getApi(): WeatherApi {
        return Api.getWeatherApi()
    }


}