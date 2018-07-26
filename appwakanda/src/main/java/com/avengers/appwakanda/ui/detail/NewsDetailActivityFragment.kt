package com.avengers.appwakanda.ui.detail

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.avengers.appwakanda.R
import com.avengers.appwakanda.WakandaModule
import com.avengers.appwakanda.databinding.FragmentNewsDetailBinding
import com.avengers.appwakanda.db.room.RoomHelper
import com.avengers.appwakanda.ui.detail.repository.NewsDetailRepositoryX
import com.avengers.appwakanda.ui.detail.vm.IReqDetailParam
import com.avengers.appwakanda.ui.detail.vm.NewsDetailViewModel
import com.avengers.appwakanda.webapi.Api
import com.avengers.zombiebase.SnackbarUtil
import com.avengers.zombiebase.ToastOneUtil
import com.avengers.zombiebase.aacbase.AACBaseFragment
import com.avengers.zombiebase.ui.LaeView

/**
 * @author Jervis
 * @Date 2018-07-18
 */
class NewsDetailActivityFragment : AACBaseFragment<FragmentNewsDetailBinding, NewsDetailViewModel, NewsDetailRepositoryX>(),LaeView{
    override fun showLoadView() {
    }

    override fun reloadData() {
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
        get() = R.layout.fragment_news_detail

    override fun createRepository(): NewsDetailRepositoryX {
        return NewsDetailRepositoryX.getInstance(
                this,this,
                Api.getSmartApi(),
                RoomHelper.getWakandaDb().newsDetailDao(),
                WakandaModule.appExecutors)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        mDataBinding.apply {
            //4.指定DataBinding中的ViewModule
            vm = mViewModel
        }
        mDataBinding.handlerClick = this.HandlerClick()
        //7.设置参数，发送请求
        mViewModel.request(IReqDetailParam())

        mViewModel.netWorkState.observe(this, Observer {
            sViewHelper.setNs(it!!)
        })
        sViewHelper.setRefreshClick {
            mViewModel.refresh()
        }
        return mDataBinding.root
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
