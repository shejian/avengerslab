package com.avengers.appwakanda.db.room.dao;


import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.avengers.appwakanda.db.room.entity.ContextItemEntity;
import com.avengers.appwakanda.db.room.entity.NewsDetailEntity;

import java.util.List;

@Dao
public interface NewsDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItemDetail(NewsDetailEntity entity);

    @Query("DELETE FROM NewsDetailEntity ")
    void deleteByItem();

    @Query("SELECT * FROM NewsDetailEntity  WHERE id == :newsId ")
    DataSource.Factory<Integer, NewsDetailEntity> quryDetail(String newsId);

    @Query("SELECT  COUNT(site_id) FROM NewsDetailEntity")
    Integer quryAllCount();

}
