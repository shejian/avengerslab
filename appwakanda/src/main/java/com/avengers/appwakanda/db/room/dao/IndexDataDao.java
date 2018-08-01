package com.avengers.appwakanda.db.room.dao;


import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.avengers.appwakanda.db.room.entity.ContextItemEntity;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface IndexDataDao {

    @Insert
    void insertList(List<ContextItemEntity> contextItemBean);

    @Query("DELETE FROM ContextItemEntity ")
    void deleteByItem();

    @Query("SELECT * FROM ContextItemEntity  ORDER BY oid ASC")
    DataSource.Factory<Integer, ContextItemEntity> queryAllItem();

    @Query("SELECT  COUNT(oid) FROM ContextItemEntity")
    Integer queryAllCount();

 /*   @Query("SELECT MAX(_mid)+ 1 FROM ContextItemEntity")
    Integer queryMaxMId();*/

}
