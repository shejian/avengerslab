package com.avengers.weather.binding

import android.databinding.BindingAdapter
import android.support.design.widget.Snackbar
import android.widget.RelativeLayout
import android.widget.TextView
import com.avengers.zombiebase.SnackbarUtil

/**
 * Created by duo.chen on 2018/7/10
 */

@BindingAdapter("android:text")
fun longToText(view: TextView, number: Long) {
    if (number != 0L) {
        view.text = number.toString()
    }
}

@BindingAdapter("app:isShowSnackBar" )
fun snackBarAction(view: RelativeLayout, isShowSnackBar: Boolean) {
    if (isShowSnackBar) {
        SnackbarUtil.showActionLong(view, "数据获取失败", "重试", {
        }, Snackbar.LENGTH_LONG)
    }
}
