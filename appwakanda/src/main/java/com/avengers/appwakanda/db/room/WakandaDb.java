package com.avengers.appwakanda.db.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.avengers.appwakanda.db.room.dao.IndexDataDao;
import com.avengers.appwakanda.db.room.entity.ContextItemEntity;


@Database(entities = {ContextItemEntity.class}, version = 1,exportSchema = false)
public abstract class WakandaDb extends RoomDatabase {

    public abstract IndexDataDao indexDataDao();

}
