package com.avengers.weather.binding

import android.databinding.BindingAdapter
import android.widget.TextView

/**
 * Created by duo.chen on 2018/7/10
 */
object BindUtil {

    @BindingAdapter("android:text")
    fun loadImage(view: TextView,number: Long) {
        view.text = number.toString()
    }
}
