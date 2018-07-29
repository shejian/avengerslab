package com.avengers.appwakanda.db.room.dao;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.avengers.appwakanda.db.room.entity.NewsDetailEntity;

import java.util.List;

@Dao
public interface NewsDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItemDetail(List<NewsDetailEntity> entity);

    @Query("DELETE FROM NewsDetailEntity")
    void deleteAll();

    @Query("SELECT * FROM NewsDetailEntity")
    LiveData<NewsDetailEntity> queryDetail();

}
