package com.avengers.appwakanda

import android.arch.lifecycle.ViewModelProvider
import com.avengers.appwakanda.db.room.RoomHelper
import com.avengers.appwakanda.db.room.dao.IndexDataCache
import com.avengers.appwakanda.ui.indexmain.repository.IndexRepository
import com.avengers.appwakanda.ui.indexmain.vm.ViewModelFactory
import com.avengers.appwakanda.webapi.SmartisanService

object Injection {

    //3
    fun provideCache(): IndexDataCache {
        var dao = RoomHelper.indexDataDao()
        var ex = WakandaModule.appExecutors
        return IndexDataCache(dao, ex.diskIO())
    }

    //2
    fun provideIndexRepository(): IndexRepository {
        return IndexRepository(SmartisanService(), provideCache())
    }

    //1
    fun provideViewModuleFactory(): ViewModelProvider.Factory {
        return ViewModelFactory(provideIndexRepository())
    }


}