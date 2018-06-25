package com.avengers.appgalaxy.db.room;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.avengers.appgalaxy.db.room.dao.IndexDataDao;
import com.avengers.zombiebase.AppExecutors;

/**
 * @author jvis
 */
public class RoomHelper {


    private final static String DB_NAME = "db_galaxy";


    public static IndexDataDao indexDataDao() {
        return galaxyDb.indexDataDao();
    }

    private static GalaxyDb galaxyDb;

    public static GalaxyDb getInstance(final Context context, final AppExecutors executors) {
        if (galaxyDb == null) {
            synchronized (GalaxyDb.class) {
                if (galaxyDb == null) {
                    galaxyDb = buildDataBase(context, executors);
                }
            }
        }
        return galaxyDb;
    }

    private static GalaxyDb buildDataBase(Context context, AppExecutors executors) {
        return Room.databaseBuilder(context, GalaxyDb.class, DB_NAME).addCallback(new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
            }

            @Override
            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                super.onOpen(db);
            }
        }).build();

    }

}
