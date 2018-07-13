package com.avengers.templete.repository

import com.avengers.templete.viewModel.Stating

/**
 * Created by duo.chen on 2018/7/13
 */
abstract class Repository<T,V> {

    abstract fun request(request: T): Stating<V>

}