package com.avengers.appwakanda.ui.detail

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.SnapHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.avengers.appwakanda.R
import com.avengers.appwakanda.databinding.FragmentNewsDetailBinding
import com.avengers.appwakanda.ui.detail.vm.IReqDetailParam
import com.avengers.appwakanda.ui.detail.vm.NewsDetailVMX
import com.avengers.zombiebase.SnackbarUtil
import com.avengers.zombiebase.ToastOneUtil

/**
 * @author Jervis
 * @Date 2018-07-18
 */
class NewsDetailActivityFragment : Fragment() {

    var newsDetailVM: NewsDetailVMX? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        //1.构建数据来源加护的类-Repository
        var repository = InjectorUtils.getNewDetailRepository()

        //2.构建ViewModule，注意VMFactory的构建位置和步骤，如果构造函数没有参数，可以去掉factory
        newsDetailVM = ViewModelProviders
                .of(this, NewsDetailVMX.VMFactory(repository))
                .get(NewsDetailVMX::class.java)

        //3.指定Layout，构建DataBinding
        var newsDetailBinding = DataBindingUtil
                .inflate<FragmentNewsDetailBinding>(inflater, R.layout.fragment_news_detail, container, false)
                .apply {
                    //4.指定DataBinding中的ViewModule
                    vm = newsDetailVM
                    //5.指定点击事件
                    handlerClick = HandlerClick()
                    //6.设置Lifecycle 否则监听将无效
                    setLifecycleOwner(this@NewsDetailActivityFragment)
                }
        //7.设置参数，发送请求
        newsDetailVM?.queryParam?.postValue(IReqDetailParam())
        return newsDetailBinding.root
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
                },    Snackbar.LENGTH_INDEFINITE)
                // Snackbar.make(it, "测试我的Snackbar", Snackbar.LENGTH_LONG).show()
            }
            // newsDetailVM?.queryParam?.postValue(IReqDetailParam())
        }
    }

}
