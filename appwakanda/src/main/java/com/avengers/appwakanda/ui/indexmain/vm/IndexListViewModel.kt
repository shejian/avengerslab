package com.avengers.appwakanda.ui.indexmain.vm

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations.map
import android.arch.lifecycle.Transformations.switchMap
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import com.avengers.appwakanda.bean.NewsListReqParam
import com.avengers.appwakanda.db.room.entity.ContextItemEntity
import com.avengers.appwakanda.ui.indexmain.repository.IndexRepository2
import com.avengers.zombiebase.aacbase.ItemCoreResult
import com.avengers.zombiebase.aacbase.NetworkState

class IndexListViewModel(private val repository: IndexRepository2) : ViewModel() {

    companion object {
        private const val VISIBLE_THRESHOLD = 5
    }

    /**
     * 设置请求参数，
     */
    fun getIndexData(queryString: NewsListReqParam, onRefresh: Boolean) {
        queryLiveData.postValue(queryString)
        runRefresh.postValue(onRefresh)
    }

    //获取参数值
    private fun lastQueryValue() = queryLiveData.value

    private val queryLiveData = MutableLiveData<NewsListReqParam>()//"line/show"

    //queryLiveData 为参数 通过函数 转化为结果，Transformations将绑定queryLiveData与匿名函数的触发
    //列表数据

    private var result: LiveData<ItemCoreResult<ContextItemEntity>> = map(queryLiveData) { repository.assembleResult(it) }

    var items: LiveData<PagedList<ContextItemEntity>> = switchMap(result) { it.data }

    var netWorkState: LiveData<NetworkState> = switchMap(result) { it.netWorkState }

    var refreshState: LiveData<NetworkState> = switchMap(result) { it.refreshState }

    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {
        if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount) {
            val immutableQuery = lastQueryValue()
            if (immutableQuery != null) {
                repository.assembleResult(immutableQuery)
            }
        }
    }

    fun retry() {
        result.value?.retry?.invoke()
    }

    val runRefresh = MutableLiveData<Boolean>()

    //runRefresh属性的变化联动触发刷新
    var mRefreshing = map(runRefresh) {
        if (it) result.value?.refresh?.invoke()
        it
    }

}