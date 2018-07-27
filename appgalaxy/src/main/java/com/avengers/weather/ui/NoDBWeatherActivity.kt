package com.avengers.weather.ui

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.View
import com.avengers.R
import com.avengers.databinding.NodbActivityWeatherBinding
import com.avengers.weather.api.Api
import com.avengers.weather.bean.FakeRequest
import com.avengers.weather.repository.NoDBWeatherRepository
import com.avengers.weather.vm.NoDbweatherViewModel
import com.avengers.zombiebase.aacbase.AACBaseActivity
import com.avengers.zombiebase.aacbase.NetworkState
import kotlinx.android.synthetic.main.activity_weather.*
import java.util.concurrent.Executors

/**
 * Created by duo.chen on 2018/7/9
 */
open class NoDBWeatherActivity : AACBaseActivity<NodbActivityWeatherBinding,NoDbweatherViewModel,NoDBWeatherRepository>() {
    override val layout: Int
        get() = R.layout.nodb_activity_weather

    override fun createRepository(): NoDBWeatherRepository {
        return NoDBWeatherRepository(
                this,
                Api.getWeatherApi(),
                Executors.newSingleThreadExecutor()
                ,false)
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
        mViewModel.netWorkState.observe(this,Observer {
            settingStatusView(it!!) { mViewModel.refresh() }
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