package com.avengers.appwakanda.ui.indexmain

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.avengers.appwakanda.R
import com.avengers.appwakanda.WakandaModule
import com.avengers.appwakanda.bean.NewsListReqParam
import com.avengers.appwakanda.databinding.ActivityWakandaMainBinding
import com.avengers.appwakanda.db.room.RoomHelper
import com.avengers.appwakanda.db.room.dao.IndexDataCache
import com.avengers.appwakanda.ui.detail.NewsDetailActivity
import com.avengers.appwakanda.ui.indexmain.repository.IndexRepository2
import com.avengers.appwakanda.ui.indexmain.vm.IndexListViewModel
import com.avengers.appwakanda.webapi.Api
import com.avengers.zombiebase.RecycleHelper
import com.avengers.zombiebase.aacbase.Status
import com.avengers.zombiebase.aacbase.paging.AACPagedListActivity

@Route(path = "/wakanda/mainactivity")
class WakandaMainActivity : AACPagedListActivity<ActivityWakandaMainBinding, IndexListViewModel, IndexRepository2>() {

    override val layout: Int
        get() = R.layout.activity_wakanda_main

    override fun createRepository(): IndexRepository2 {
        return IndexRepository2.getInstance(
                Api.getSmartApi(),
                IndexDataCache(RoomHelper.indexDataDao(), WakandaModule.appExecutors.diskIO()),
                WakandaModule.appExecutors)
    }


    override fun createModelFactory(repository: IndexRepository2): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(IndexListViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return IndexListViewModel(repository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }

/*
    var mViewModel: IndexListViewModel? = null
    var mDataBinding: ActivityWakandaMainBinding? = null
*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initRecycle()
        setUIObserve()
        //  setupScrollListener()

        initAdapter()

        val newsListReqParam = NewsListReqParam()
        newsListReqParam.keyWord = "line/show"

        //指定请求参数，作为LiveData 数据，将自动触发请求
        mViewModel!!.getPagedListData(newsListReqParam)

    }


/*    companion object {

        private fun createRepository(): IndexRepository2 {
            return IndexRepository2.getInstance(
                    Api.getSmartApi(),
                    IndexDataCache(RoomHelper.indexDataDao(), WakandaModule.appExecutors.diskIO()),
                    WakandaModule.appExecutors)
        }

        fun createViewModel(activity: FragmentActivity): mViewModel {
            return ViewModelProviders
                    .of(activity, IndexViewModelFactory(createRepository()))
                    .get(mViewModel::class.java)
        }

        fun setDataBinding(activity: FragmentActivity): ActivityWakandaMainBinding {
            return DataBindingUtil.setContentView(activity, getLayout())
        }


        private fun getLayout(): Int {
            return R.layout.activity_wakanda_main
        }

    }*/


    private fun initRecycle() {
        RecycleHelper.initBaseRecycleView(this, mDataBinding!!.recyclerView)
        RecycleHelper.initSwipeRefresh(mDataBinding!!.swipeRefreshView) {
            //下拉触发运行刷新的值，联动触发刷新事件
            mViewModel?.refresh()
        }
    }


    private fun setUIObserve() {
        //完成刷新请求，停止刷新UI
        mViewModel!!.refreshState.observe(this, Observer {
            mDataBinding!!.swipeRefreshView.isRefreshing = it?.status == Status.RUNNING
        })
    }

    private fun initAdapter() {
        val adapter = IndexPagedListAdapter() // 方法2 val adapter = ReposAdapter()

        adapter.onItemClickFun = { _, _ ->
            startActivity(Intent(this, NewsDetailActivity::class.java))
        }

        adapter.onRetryFun = { _, _ ->
            mViewModel!!.retry()
        }

        mDataBinding!!.recyclerView.adapter = adapter
        mViewModel!!.items.observe(this, Observer {
            if (it?.size == 0) {
                //极端情况需要处理空数据的情况，按具体业务而定
                return@Observer
            }
            adapter.submitList(it)
            scrollRetry = false
        })

        //完成"更多"加载请求
        mViewModel!!.netWorkState.observe(this, Observer {
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
        val layoutManager = mDataBinding!!.recyclerView.layoutManager as LinearLayoutManager
        mDataBinding!!.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

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
                        mViewModel!!.retry()
                    }
                } else {
                    cnum = 0
                }
            }
        })
    }


}

