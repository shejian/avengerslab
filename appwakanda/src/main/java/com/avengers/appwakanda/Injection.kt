package com.avengers.appwakanda

import android.arch.lifecycle.ViewModelProvider
import com.avengers.appwakanda.db.room.RoomHelper
import com.avengers.appwakanda.db.room.dao.IndexDataCache
import com.avengers.appwakanda.ui.indexmain.repository.IndexRepository
import com.avengers.appwakanda.ui.common.ViewModelFactory
import com.avengers.appwakanda.webapi.Api

object Injection {

    //3
    fun provideCache(): IndexDataCache {
        val dao = RoomHelper.indexDataDao()
        val ex = WakandaModule.appExecutors
        return IndexDataCache(dao, ex.diskIO())
    }

    //2
    fun provideIndexRepository(): IndexRepository {
        return IndexRepository(Api.getSmartApi(), provideCache())
    }

    //1
    fun provideViewModuleFactory(): ViewModelProvider.Factory {
        return ViewModelFactory(provideIndexRepository())
    }


}