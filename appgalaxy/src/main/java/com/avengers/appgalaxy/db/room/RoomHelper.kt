package com.avengers.appgalaxy.db.room

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

import com.avengers.appgalaxy.db.room.dao.IndexDataDao
import com.avengers.weather.db.WeatherDao
import com.avengers.zombiebase.AppExecutors

/**
 * @author jvis
 */
object RoomHelper {

    private val DB_NAME = "db_galaxy"

    private var galaxyDb: GalaxyDb? = null


    fun indexDataDao(): IndexDataDao {
        return galaxyDb!!.indexDataDao()
    }

    fun weatherDao():WeatherDao {
        return galaxyDb!!.weatherDao()
    }

    fun getInstance(context: Context, executors: AppExecutors): GalaxyDb? {
        if (galaxyDb == null) {
            synchronized(GalaxyDb::class.java) {
                if (galaxyDb == null) {
                    galaxyDb = buildDataBase(context, executors)
                }
            }
        }
        return galaxyDb
    }

    private fun buildDataBase(context: Context, executors: AppExecutors): GalaxyDb {
        return Room.databaseBuilder(context, GalaxyDb::class.java, DB_NAME).addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                executors.diskIO().execute {
                    GalaxyDb.installAllDefData()
                }

            }

        }).build()

    }

}
