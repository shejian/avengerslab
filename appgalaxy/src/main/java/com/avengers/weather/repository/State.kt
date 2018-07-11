package com.avengers.weather.repository

import android.arch.lifecycle.LiveData

/**
 * Created by duo.chen on 2018/7/9
 */
class State<T>(val weather: LiveData<T>,
               val netWorkState: LiveData<NetworkState>,
               val refreshState: LiveData<NetworkState>,
               val refresh: () -> Unit,
               val retry: () -> Unit)