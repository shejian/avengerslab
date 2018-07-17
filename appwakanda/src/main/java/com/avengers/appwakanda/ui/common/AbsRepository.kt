package com.avengers.appwakanda.ui.common

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.avengers.appwakanda.ui.indexmain.repository.NetworkState

/**
 * @author Jervis
 * @data 2018-07-17
 * Repository作用的抽象类
 * haveCache 默认具备缓存能力
 */
abstract class AbsRepository<T : BeanResponse>(var haveCache: Boolean = true) {

    var netWorkState = MutableLiveData<NetworkState>()

    lateinit var dataSource: LiveData<T>

    /**
     * 组装一个界面主要数据的模型BaseVMResult
     */
    fun assemblyVMResult(vararg args: Any): BaseVMResult<T> {

        dataSource = getLiveData(haveCache, args)

        superRefresh(args)

        return BaseVMResult(dataSource, netWorkState) {
            superRefresh(args)
        }
    }

    private fun superRefresh(vararg args: Any) {
        netWorkState.postValue(NetworkState.LOADING)
        refresh(args)
    }

    /**
     * 请求刷新数据
     */
    abstract fun refresh(vararg args: Any)

    /**
     * 保存数据。有缓存是需要子类自己实现，不要缓存时直接设置到LiveData中
     */
    fun saveData(t: T) {
        when {
            haveCache -> addToDb(t)
            else -> (dataSource as MutableLiveData).postValue(t)
        }
    }


    /**
     * 获取LiveData，有缓存时取数据库，不要缓存时实例化一个空的LiveData
     */
    private fun getLiveData(haveCache: Boolean, vararg args: Any): LiveData<T> {
        return when {
            haveCache -> queryFromDb(args)!!
            else -> MutableLiveData<T>()
        }
    }

    /**
     * 有缓存时需要自己实现插入数据库的函数
     */
    open fun addToDb(t: T) {

    }

    /**
     * 有缓存时需要自己实现查询数据库的函数
     */
    open fun queryFromDb(vararg args: Any): LiveData<T>? {
        return null
    }


}