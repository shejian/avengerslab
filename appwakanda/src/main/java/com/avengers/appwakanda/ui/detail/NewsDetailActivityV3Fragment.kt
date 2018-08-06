package com.avengers.appwakanda.ui.detail

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.avengers.appwakanda.R
import com.avengers.appwakanda.WakandaModule
import com.avengers.appwakanda.bean.QdailyDetailReqParam
import com.avengers.appwakanda.databinding.FragmentNewsDetailV3Binding
import com.avengers.appwakanda.db.room.RoomHelper
import com.avengers.appwakanda.ui.detail.repository.NewsDetailRepositoryV3
import com.avengers.appwakanda.ui.detail.vm.IReqDetailParam
import com.avengers.appwakanda.ui.detail.vm.NewsDetailViewModelV3
import com.avengers.appwakanda.webapi.Api
import com.avengers.zombiebase.SnackbarUtil
import com.avengers.zombiebase.ToastOneUtil
import com.avengers.zombiebase.aacbase.AACBaseFragmentV3

/**
 * @author Jervis
 * @Date 2018-07-18
 */
class NewsDetailActivityV3Fragment
    : AACBaseFragmentV3<FragmentNewsDetailV3Binding,
                        NewsDetailViewModelV3,
                        NewsDetailRepositoryV3>() {

    override fun retry() {
        mViewModel.qdVm.refresh()
        mViewModel.detailVm.refresh()
    }

    override val layout: Int
        get() = R.layout.fragment_news_detail_v3

    override fun createRepository(): NewsDetailRepositoryV3 {
        return NewsDetailRepositoryV3.getInstance(
                this,
                Api.getSmartApi(),
                Api.getQdailyApi(),
                RoomHelper.getWakandaDb().newsDetailDao(),
                WakandaModule.appExecutors)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        mDataBinding.apply {
            //4.指定DataBinding中的ViewModule
            vm2 = mViewModel
        }
        initbaseDetail()
        initQdaily()
        return mDataBinding.root
    }

    private fun initbaseDetail() {
        //  mDataBinding.handlerClick = this.HandlerClick()
        //7.设置参数，发送请求
        mViewModel.detailVm.request(IReqDetailParam())

        mViewModel.detailVm.netWorkState.observe(this, Observer {
            //  statusViewHelper.setNetworkState(it!!)
            settingStatusView(it!!)
        })
        mViewModel.detailVm.liveData.observe(this, Observer {
            //   ToastOneUtil.showToastShort("" + it?.data?.list?.get(0)?.brief)
        })
    }

    private fun initQdaily() {
        val qqqq = QdailyDetailReqParam()
        qqqq.keyWord = "54743.json"
        mViewModel.qdVm.request(qqqq)
        mViewModel.qdVm.liveData.observe(this, Observer {
            ToastOneUtil.showToastShort(it?.response?.article?.body!!)
        })
    }


    /**
     * Handle点击事件
     */
    inner class HandlerClick {
        fun refreshClick() {
            view?.let {
                // ToastOneUtil.showToastShort("测试我的提示")
                SnackbarUtil.showActionLong(it,
                        "我测试一下",
                        "撤销", {
                    ToastOneUtil.showToastShort("已撤销")
                }, Snackbar.LENGTH_INDEFINITE)
                // Snackbar.make(it, "测试我的Snackbar", Snackbar.LENGTH_LONG).show()
            }
            mViewModel.qdVm.refresh()
            mViewModel.detailVm.refresh()
            //  mViewModel.request(IReqDetailParam())
        }
    }

}
