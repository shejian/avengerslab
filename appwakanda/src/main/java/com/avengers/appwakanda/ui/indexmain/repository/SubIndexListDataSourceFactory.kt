/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.avengers.appwakanda.ui.indexmain.repository

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.avengers.appwakanda.bean.NewsListReqParam
import com.avengers.appwakanda.db.room.entity.ContextItemEntity
import com.avengers.appwakanda.webapi.SmartisanApi
import java.util.concurrent.Executor

/**
 * A simple data source factory which also provides a way to observe the last created data source.
 * This allows us to channel its network request status etc back to the UI. See the Listing creation
 * in the Repository class.
 */
class SubIndexListDataSourceFactory(
        private val redditApi: SmartisanApi,
        private val query: NewsListReqParam,
        private val retryExecutor: Executor) : DataSource.Factory<Int, ContextItemEntity>() {
    val sourceLiveData = MutableLiveData<PageKeyedSubredditDataSource>()
    override fun create(): DataSource<Int, ContextItemEntity> {
        val source = PageKeyedSubredditDataSource(redditApi, query, retryExecutor)
        sourceLiveData.postValue(source)
        return source
    }
}
