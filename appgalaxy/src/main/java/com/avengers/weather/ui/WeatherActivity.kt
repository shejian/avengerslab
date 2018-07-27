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
import com.avengers.zombiebase.ui.LaeView
import kotlinx.android.synthetic.main.activity_weather.*
import java.util.concurrent.Executors

/**
 * Created by duo.chen on 2018/7/9
 */
class WeatherActivity : AACBaseActivity<ActivityWeatherBinding, WeatherViewModel, WeatherRepository>(), LaeView {
    override fun showLoadView() {
    }

    override fun showLoadTransView() {
    }

    override fun initErrorLayout(): Boolean {
        return false
    }

    override fun showContentView() {
    }

    override fun showErrorView(error: String?) {
    }

    override val layout: Int
        get() = R.layout.activity_weather

    override fun createRepository(): WeatherRepository {
        return WeatherRepository(
                this,
                Api.getWeatherApi(),
                RoomHelper.weatherDao(),
                Executors.newSingleThreadExecutor())
    }

    override fun reloadData() {
        mViewModel.refresh()
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
        mViewModel.netWorkState.observe(this, Observer {
            settingStatusView(it!!, mViewModel.liveData.value != null)
        })
    }

    fun initSwipeRefresh() {

        mViewModel.netWorkState.observe(this, Observer {
            swipe_refresh.isRefreshing = it == NetworkState.LOADING
        })

        swipe_refresh.setOnRefreshListener { mViewModel.refresh() }
    }


    fun click(view: View) {
        mViewModel.refresh()
    }


}