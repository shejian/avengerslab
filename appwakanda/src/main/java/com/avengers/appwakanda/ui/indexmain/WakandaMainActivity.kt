package com.avengers.appwakanda.ui.indexmain

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
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
        indexListViewModel = ViewModelProviders.of(this).get(IndexListViewModel::class.java)
        activityDataBinding = DataBindingUtil.setContentView(this@WakandaMainActivity, R.layout.activity_wakanda_main)
        initAdapter()
        RcycyleHelper.initBaseRcycyleView(this, activityDataBinding.recyclerView)
        RcycyleHelper.initSwipeRefresh(activityDataBinding.swipeRefreshView)

        activityDataBinding.swipeRefreshView.setOnRefreshListener {
           // indexListViewModel.refreshData()
            activityDataBinding.swipeRefreshView.isRefreshing = false
        }
        indexListViewModel.getIndexData("line/show")

    }


    fun initAdapter() {

        var adapter = IndexPagedListAdapter<IndexlistDbdItemBinding>()
        activityDataBinding.recyclerView.adapter = adapter
        indexListViewModel.items.observe(this, Observer {
            adapter.submitList(it)
            activityDataBinding.swipeRefreshView.isRefreshing = false
        })
    }


}

