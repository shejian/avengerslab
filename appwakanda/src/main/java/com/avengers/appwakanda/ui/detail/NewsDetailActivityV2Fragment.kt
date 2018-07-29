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
import com.avengers.appwakanda.databinding.FragmentNewsDetailV2Binding
import com.avengers.appwakanda.db.room.RoomHelper
import com.avengers.appwakanda.ui.detail.repository.NewsDetailRepositoryV2
import com.avengers.appwakanda.ui.detail.vm.IReqDetailParam
import com.avengers.appwakanda.ui.detail.vm.NewsDetailViewModelV2
import com.avengers.appwakanda.webapi.Api
import com.avengers.zombiebase.SnackbarUtil
import com.avengers.zombiebase.ToastOneUtil
import com.avengers.zombiebase.aacbase.AACBaseFragment

/**
 * @author Jervis
 * @Date 2018-07-18
 */
class NewsDetailActivityV2Fragment : AACBaseFragment<FragmentNewsDetailV2Binding, NewsDetailViewModelV2, NewsDetailRepositoryV2>() {

    override val layout: Int
        get() = R.layout.fragment_news_detail_v2

    override fun createRepository(): NewsDetailRepositoryV2 {
        return NewsDetailRepositoryV2.getInstance(
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
        mDataBinding.handlerClick = this.HandlerClick()
        //7.设置参数，发送请求
        mViewModel.request(IReqDetailParam())

        mViewModel.netWorkState.observe(this, Observer {
            //  statusViewHelper.setNetworkState(it!!)
            settingStatusView(it!!)
        })
        mViewModel.liveData.observe(this, Observer {
            //   ToastOneUtil.showToastShort("" + it?.data?.list?.get(0)?.brief)
        })
        initQdaily()
        return mDataBinding.root
    }

    fun initQdaily() {
        val qqqq = QdailyDetailReqParam()
        qqqq.keyWord = "54743.json"
        mViewModel.qdqueryParam.postValue(qqqq)
        mViewModel.qdliveData.observe(this, Observer {
           // ToastOneUtil.showToastShort(it?.response?.article?.body!!)
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
            mViewModel.refresh()
            //  mViewModel.request(IReqDetailParam())
        }
    }

}
