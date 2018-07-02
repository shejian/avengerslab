package com.avengers.appgalaxy.db.room.dao


import android.arch.paging.DataSource
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.avengers.appgalaxy.db.room.entity.ContextItemEntity

@Dao
interface IndexDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInfo(contextItemBean: ContextItemEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInfos(contextItemBeans: List<ContextItemEntity>)

    @Query("SELECT * FROM ContextItemEntity")
    fun quryInfos(): DataSource.Factory<Int, ContextItemEntity>

    @Query("SELECT COUNT(*) FROM ContextItemEntity ")
    fun hasData(): Int

}
