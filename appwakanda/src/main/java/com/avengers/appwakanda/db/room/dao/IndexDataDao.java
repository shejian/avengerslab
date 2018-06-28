package com.avengers.appwakanda.db.room.dao;


import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.avengers.appwakanda.db.room.entity.ContextItemEntity;

import java.util.List;

@Dao
public interface IndexDataDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertList(List<ContextItemEntity> contextItemBean);

    @Query("SELECT * FROM ContextItemEntity")
    LiveData<List<ContextItemEntity>> quryInfos();

    @Query("SELECT COUNT(*) FROM ContextItemEntity ")
    int hasData();

    @Query("SELECT * FROM ContextItemEntity ")
    DataSource.Factory<Integer, ContextItemEntity> quryAllItem();

}
