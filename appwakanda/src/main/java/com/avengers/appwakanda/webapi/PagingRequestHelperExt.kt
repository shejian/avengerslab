package com.avengers.appwakanda.webapi

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.avengers.zombiebase.accbase.NetworkState



private fun getErrorMessage(report: PagingRequestHelper.StatusReport): String {
    return PagingRequestHelper.RequestType.values().mapNotNull {
        report.getErrorFor(it)?.message
    }.first()
}


//创建网络状态的LiveData数据
fun PagingRequestHelper.createStatusLiveData(): LiveData<NetworkState> {

    val liveData = MutableLiveData<NetworkState>()
    addListener { report ->
        when {
            report.hasRunning() -> liveData.postValue(NetworkState.LOADING)
            report.hasError() -> liveData.postValue(
                    NetworkState.error(getErrorMessage(report)))
            else -> liveData.postValue(NetworkState.LOADED)
        }
    }
    return liveData
}
