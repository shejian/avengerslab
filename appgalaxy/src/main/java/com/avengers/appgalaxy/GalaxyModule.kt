package com.avengers.appgalaxy

import com.avengers.appgalaxy.db.room.RoomHelper
import com.avengers.zombiebase.AppExecutors
import com.avengers.zombiebase.ApplicationInitBase
import com.avengers.zombiebase.BaseAppLogic

class GalaxyModule : BaseAppLogic() {

    private var appExecutors: AppExecutors? = null
    override fun onCreate() {
        super.onCreate()
        appExecutors = ApplicationInitBase.getInstanceExecutors()
        RoomHelper.getInstance(mApplication, ApplicationInitBase.getInstanceExecutors())
    }

    fun getAppExe():AppExecutors?{
        return appExecutors
    }



}