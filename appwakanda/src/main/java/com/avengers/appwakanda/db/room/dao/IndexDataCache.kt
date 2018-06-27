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


    fun queryIndexList(): DataSource.Factory<Int, ContextItemEntity> {
        return indexDao.quryAllItem()
    }


}