package com.avengers.appgalaxy

import android.app.Application
import com.avengers.appgalaxy.db.room.RoomHelper
import com.avengers.zombiebase.AppExecutors
import com.avengers.zombiebase.ApplicationInitBase

class GalaxyModule {
    companion object {
        var appExecutors: AppExecutors? = null
        var app: Application? = null

        fun init(application: Application) {
            app = application
            appExecutors = ApplicationInitBase.getInstanceExecutors()
            RoomHelper.getInstance(application, ApplicationInitBase.getInstanceExecutors())
        }

    }


}