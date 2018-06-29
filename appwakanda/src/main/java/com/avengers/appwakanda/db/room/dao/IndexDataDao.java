package com.avengers.appwakanda.db.room.dao;


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

    @Query("SELECT * FROM ContextItemEntity  ORDER BY _mid ASC")
    DataSource.Factory<Integer, ContextItemEntity> quryAllItem();


    @Query("SELECT MAX(_mid)+ 1 FROM ContextItemEntity")
    Integer quryMaxMId();

}
