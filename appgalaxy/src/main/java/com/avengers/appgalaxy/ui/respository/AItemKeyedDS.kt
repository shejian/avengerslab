package com.avengers.appgalaxy.ui.respository

import android.arch.paging.ItemKeyedDataSource
import com.avengers.appgalaxy.db.room.entity.ContextItemEntity

class AItemKeyedDS: ItemKeyedDataSource<Int, ContextItemEntity>() {

    /**
     * 初始化
     */
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<ContextItemEntity>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * 之后
     */
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<ContextItemEntity>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    /**
     * 之前
     */
    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<ContextItemEntity>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * 通过value获取Key
     */
    override fun getKey(item: ContextItemEntity): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}