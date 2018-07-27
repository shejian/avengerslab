package com.avengers.weather.ui

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.View
import com.avengers.R
import com.avengers.appgalaxy.db.room.RoomHelper
import com.avengers.databinding.ActivityWeatherBinding
import com.avengers.weather.api.Api
import com.avengers.weather.bean.FakeRequest
import com.avengers.weather.repository.WeatherRepository
import com.avengers.weather.vm.WeatherViewModel
import com.avengers.zombiebase.aacbase.AACBaseActivity
import com.avengers.zombiebase.aacbase.NetworkState
import kotlinx.android.synthetic.main.activity_weather.*
import java.util.concurrent.Executors

/**
 * Created by duo.chen on 2018/7/9
 */
open class WeatherActivity : AACBaseActivity<ActivityWeatherBinding,WeatherViewModel,WeatherRepository>() {
    override val layout: Int
        get() = R.layout.activity_weather

    override fun createRepository(): WeatherRepository {
        return WeatherRepository(this,
                Api.getWeatherApi(),
                RoomHelper.weatherDao(),
                Executors.newSingleThreadExecutor())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDataBinding.model = mViewModel
        initWithViewModel()
        initSwipeRefresh()
        val request = FakeRequest("上海")
        mViewModel.request(request)

    }


    private fun initWithViewModel() {
        mViewModel.netWorkState.observe(this,Observer { networkState: NetworkState? ->
            mViewModel.liveData.value?.let {
                settingStatusView(networkState!!,it.time >= 1532680800000) { mViewModel.refresh() }
            }
        })
    }

    fun initSwipeRefresh() {

        mViewModel.netWorkState.observe(this,Observer {
            swipe_refresh.isRefreshing = it == NetworkState.LOADING
        })

        swipe_refresh.setOnRefreshListener { mViewModel.refresh() }
    }


    fun click(view: View) {
        mViewModel.refresh()
    }

}