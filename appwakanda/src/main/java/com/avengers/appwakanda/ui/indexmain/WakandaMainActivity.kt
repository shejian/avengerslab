package com.avengers.appwakanda.ui.indexmain

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.avengers.appwakanda.Injection
import com.avengers.appwakanda.R
import com.avengers.appwakanda.databinding.ActivityWakandaMainBinding
import com.avengers.appwakanda.databinding.IndexlistDbdItemBinding
import com.avengers.appwakanda.ui.indexmain.vm.IndexListViewModel
import com.avengers.zombiebase.RcycyleHelper

@Route(path = "/wakanda/mainactivity")
class WakandaMainActivity : AppCompatActivity() {

    private lateinit var indexListViewModel: IndexListViewModel

    private lateinit var activityDataBinding: ActivityWakandaMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        indexListViewModel = ViewModelProviders
                .of(this, Injection.provideViewModuleFactory())
                .get(IndexListViewModel::class.java)
        activityDataBinding = DataBindingUtil.setContentView(this@WakandaMainActivity,
                R.layout.activity_wakanda_main)

        //将Adapter设置到Rcycyleview上，将ViewModel中的数据增加监听，设置到Adapter中
        initAdapter()

        RcycyleHelper.initBaseRcycyleView(this, activityDataBinding.recyclerView)
        //   RcycyleHelper.initSwipeRefresh(activityDataBinding)
        //   setupScrollListener()

        //初始化刷新控件的监听
        activityDataBinding.swipeRefreshView.setOnRefreshListener {
            //下拉触发运行刷新的值，联动触发刷新事件
            indexListViewModel.runRefresh.postValue(true)
        }
        //指定请求参数，作为livedata 数据，将自动触发请求，是否需要首次触发下拉刷新onRefresh
        indexListViewModel.getIndexData("line/show",true)

    }


    fun initAdapter() {
        val adapter = IndexPagedListAdapter<IndexlistDbdItemBinding>()
        activityDataBinding.recyclerView.adapter = adapter
        indexListViewModel.items.observe(this, Observer {
            adapter.submitList(it)
        })
        //对刷新事件做了监控，刷新属性发生变化时，改变刷新UI
        indexListViewModel.mRefreshing.observe(this, Observer {
            activityDataBinding.swipeRefreshView.isRefreshing = true
        })
        //完成刷新请求，停止刷新UI
        indexListViewModel.errorInfo.observe(this, Observer {
            activityDataBinding.swipeRefreshView.isRefreshing = false
        })
    }


    /**
     * 滚动控制加载
     */
    private fun setupScrollListener() {
        val layoutManager = activityDataBinding.recyclerView.layoutManager as LinearLayoutManager
        activityDataBinding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                indexListViewModel.listScrolled(visibleItemCount, lastVisibleItem, totalItemCount)
            }
        })
    }


}
