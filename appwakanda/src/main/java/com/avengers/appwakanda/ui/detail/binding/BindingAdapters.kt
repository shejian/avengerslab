package com.avengers.appwakanda.ui.detail.binding

import android.databinding.BindingAdapter
import android.widget.TextView
import com.avengers.appwakanda.ui.indexmain.repository.NetworkState
import com.avengers.appwakanda.ui.indexmain.repository.Status

object BindingAdapters {

    @BindingAdapter("app:status")
    @JvmStatic
    fun reqStatusToView(view: TextView, status: NetworkState?) {
        when (status?.status) {
            Status.RUNNING -> view.text = "加载中。。。"
            Status.SUCCESS -> view.text = "成功。。。"
            Status.FAILED -> view.text = "失败了。。。"
        }
    }

}