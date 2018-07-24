package com.avengers.appwakanda.ui.indexmain

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.avengers.appwakanda.R
import com.avengers.appwakanda.databinding.ActivityWakandaMainBinding
import com.avengers.appwakanda.ui.detail.NewsDetailActivity
import com.avengers.appwakanda.ui.indexmain.vm.IndexListViewModel
import com.avengers.zombiebase.RecycleHelper
import com.avengers.zombiebase.aacbase.Status

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

        initRecycle()

        setUIObserve()

        //  setupScrollListener()

        //将Adapter设置到RecycleView上，将ViewModel中的数据增加监听，设置到Adapter中
        initAdapter()

        //指定请求参数，作为LiveData 数据，将自动触发请求，是否需要首次触发下拉刷新onRefresh
        indexListViewModel.getIndexData("line/show", true)
        /**
        if (DeviceUtil.isFullDisplay) {
        LogU.d("shejian", "是全面屏手机")
        } else {
        LogU.d("shejian", "不是全面屏手机")
        }
         */
    }

    private fun initRecycle() {
        RecycleHelper.initBaseRecycleView(this, activityDataBinding.recyclerView)
        RecycleHelper.initSwipeRefresh(activityDataBinding.swipeRefreshView) {
            //下拉触发运行刷新的值，联动触发刷新事件
            indexListViewModel.runRefresh.postValue(true)
        }
    }


    private fun setUIObserve() {
        //对刷新事件做了监控，刷新属性发生变化时，改变刷新UI
        indexListViewModel.mRefreshing.observe(this, Observer {
            activityDataBinding.swipeRefreshView.isRefreshing = it!!
        })
        //完成刷新请求，停止刷新UI
        indexListViewModel.refreshState.observe(this, Observer {
            activityDataBinding.swipeRefreshView.isRefreshing = false
        })
    }

    private fun initAdapter() {
        val adapter = IndexPagedListAdapter() // 方法2 val adapter = ReposAdapter()

        adapter.onItemClickFun = { _, _ ->
            startActivity(Intent(this, NewsDetailActivity::class.java))
        }

        adapter.onRetryFun = { _, _ ->
            indexListViewModel.retry()
        }

        activityDataBinding.recyclerView.adapter = adapter
        indexListViewModel.items.observe(this, Observer {
            if (it?.size == 0) {
                //极端情况需要处理空数据的情况，按具体业务而定
                return@Observer
            }
            adapter.submitList(it)
            scrollRetry = false
        })

        //完成"更多"加载请求
        indexListViewModel.netWorkState.observe(this, Observer {
            scrollRetry = Status.FAILED == it?.status
            adapter.setNetworkState(it)
        })
    }


    var scrollRetry = false

    var cnum = 0
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
                //  LogU.d("shejian", "onScrolled:$visibleItemCount/$lastVisibleItem/$totalItemCount")
                if (lastVisibleItem + 5 == totalItemCount) {
                    //LogU.d("shejian", "scrollRetry:$scrollRetry")
                    if (scrollRetry && cnum == 0) {
                        cnum++
                        scrollRetry = false
                        //  Toast.makeText(this@WakandaMainActivity, "onScrolled", Toast.LENGTH_SHORT).show()
                        indexListViewModel.retry()
                    }
                } else {
                    cnum = 0
                }
            }
        })
    }


}

