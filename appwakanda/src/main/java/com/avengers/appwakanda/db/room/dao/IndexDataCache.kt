package com.avengers.appwakanda.db.room.dao

import android.arch.paging.DataSource
import android.util.Log
import com.avengers.appwakanda.db.room.entity.ContextItemEntity
import java.util.concurrent.Executor

class IndexDataCache(
        private val indexDao: IndexDataDao,
        private val ioExecutor: Executor
) {


    fun insert(repos: List<ContextItemEntity>, insertFinished: () -> Unit) {
        ioExecutor.execute {
            Log.d("GithubLocalCache", "inserting ${repos.size} repos")
            indexDao.insertList(repos)
            insertFinished()
        }
    }


    fun cleanData(delFinished: () -> Unit) {
        ioExecutor.execute {
            indexDao.deleteByItem()
            delFinished()
        }

    }


    fun refresh(repos: List<ContextItemEntity>,finished: () -> Unit) {
        ioExecutor.execute {
            indexDao.deleteByItem()
            indexDao.insertList(repos)
            finished()
        }
    }

    fun queryIndexList(): DataSource.Factory<Int, ContextItemEntity> {
        return indexDao.queryAllItem()
    }


    fun queryMaxIndex(): Int {
        var dds = indexDao.queryMaxMId()
        if (dds == null) {
            dds = 0
        }
        return dds
    }

    fun queryTotal(): Int {
        return indexDao.queryAllCount()
    }


}