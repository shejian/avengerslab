package com.avengers.appwakanda.db.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.avengers.appwakanda.db.room.dao.IndexDataDao;
import com.avengers.appwakanda.db.room.dao.NewsDetailDao;
import com.avengers.appwakanda.db.room.entity.ContextItemEntity;
import com.avengers.appwakanda.db.room.entity.NewsDetailEntity;


@Database(entities = {
        ContextItemEntity.class,
        NewsDetailEntity.class
}, version = 2, exportSchema = false)
public abstract class WakandaDb extends RoomDatabase {

    public abstract IndexDataDao indexDataDao();

    public abstract NewsDetailDao newsDetailDao();

}
