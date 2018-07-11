package com.avengers.weather.binding

import android.databinding.BindingAdapter
import android.widget.TextView

/**
 * Created by duo.chen on 2018/7/10
 */

@BindingAdapter("android:text")
fun longToText(view: TextView,number: Long) {
    if (number != 0L) {
        view.text = number.toString()
    }
}

