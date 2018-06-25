package com.avengers.appgalaxy.db.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.avengers.appgalaxy.db.room.dao.IndexDataDao;
import com.avengers.appgalaxy.db.room.entity.ContextItemEntity;


@Database(entities = {ContextItemEntity.class}, version = 1,exportSchema = false)
public abstract class GalaxyDb extends RoomDatabase {

    public abstract IndexDataDao indexDataDao();

}
