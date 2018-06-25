package com.avengers.appgalaxy.db.room.dao;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.avengers.appgalaxy.db.room.entity.ContextItemEntity;

import java.util.List;

@Dao
public interface IndexDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertInfo(ContextItemEntity contextItemBean);

    @Query("SELECT * FROM ContextItemEntity")
    LiveData<List<ContextItemEntity>> quryInfos();

    @Query("SELECT COUNT(*) FROM ContextItemEntity ")
    int hasData();

}
