package com.avengers.appgalaxy.db.room

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.migration.Migration
import android.content.Context
import android.util.Log

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

    fun weatherDao(): WeatherDao {
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
        return Room.databaseBuilder(context, GalaxyDb::class.java, DB_NAME)
                /*          .addMigrations(object : Migration(1, 2) {
                              override fun migrate(database: SupportSQLiteDatabase) {
                                  executors.diskIO().execute {
                                      GalaxyDb.installAllDefData()
                                  }
                              }
                          })*/
                //破坏性升级策略，将会销毁库里面的所有数据
                .fallbackToDestructiveMigration()
                .addCallback(object : RoomDatabase.Callback() {

                    //首次打开时走Create
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Log.d("shejian", "onCreate RoomDatabase")
                        executors.diskIO().execute {
                            Log.d("shejian", "onCreate & installAllDefData")
                            GalaxyDb.installAllDefData()
                        }
                    }

                    //非首次打开时走open
                    override fun onOpen(db: SupportSQLiteDatabase) {
                        super.onOpen(db)
                        executors.diskIO().execute {
                            Log.d("shejian", "onOpen RoomDatabase")
                            when (GalaxyDb.getDataCount()) {
                                0 -> {
                                    GalaxyDb.installAllDefData()
                                    Log.d("shejian", "onOpen & installAllDefData")
                                }
                            }
                        }
                    }

                }).build()

    }

}
