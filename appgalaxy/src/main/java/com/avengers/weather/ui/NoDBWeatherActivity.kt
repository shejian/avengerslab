package com.avengers.weather.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.avengers.R
import com.avengers.databinding.NodbActivityWeatherBinding
import com.avengers.weather.api.Api
import com.avengers.weather.api.WeatherApi
import com.avengers.weather.bean.FakeRequest
import com.avengers.weather.repository.NoDBWeatherRepository
import com.avengers.weather.vm.NoDbweatherViewModel
import com.avengers.zombiebase.BaseActivity
import com.avengers.zombiebase.aacbase.NetworkState
import com.avengers.zombiebase.aacbase.Status
import kotlinx.android.synthetic.main.activity_weather.*
import java.util.concurrent.Executors

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

        weatherViewModel.netWorkState?.observe(this,Observer {
            swipe_refresh.isRefreshing = it == NetworkState.LOADING
        })

        swipe_refresh.setOnRefreshListener { weatherViewModel.refresh() }
    }

    private fun initWithViewModel(viewModel: NoDbweatherViewModel) {

        viewModel.netWorkState.observe(this,Observer {

            if (it?.status == Status.RUNNING) {
                showLoadTransView()
            } else if (it?.status == Status.FAILED) {
                Toast.makeText(this,it.msg,Toast.LENGTH_SHORT).show()
                showErrorView(it.msg!!)
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
                val weatherRepository = NoDBWeatherRepository(this@NoDBWeatherActivity,getApi(),Executors.newSingleThreadExecutor())
                @Suppress("UNCHECKED_CAST")
                return NoDbweatherViewModel(weatherRepository) as T
            }
        }).get(NoDbweatherViewModel::class.java)
    }

    private fun getApi(): WeatherApi {
        return Api.getWeatherApi()
    }


}