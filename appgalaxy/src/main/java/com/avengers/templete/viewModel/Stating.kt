package com.avengers.templete.viewModel

import android.arch.lifecycle.LiveData

/**
 * Created by duo.chen on 2018/7/9
 */
class Stating<T>(val response: LiveData<T>,
                 val netWorkState: LiveData<NetworkState>,
                 val refreshState: LiveData<NetworkState>,
                 val refresh: () -> Unit,
                 val retry: () -> Unit)