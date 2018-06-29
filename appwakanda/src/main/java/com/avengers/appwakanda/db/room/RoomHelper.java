package com.avengers.appwakanda.db.room;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.avengers.appwakanda.db.room.dao.IndexDataDao;
import com.avengers.zombiebase.AppExecutors;

/**
 * @author jvis
 */
public class RoomHelper {

    private final static String DB_NAME = "db_wakanda";


    public static IndexDataDao indexDataDao() {
        return wakandaDb.indexDataDao();
    }

    private static WakandaDb wakandaDb;

    public static WakandaDb getWakandaDb() {
        return wakandaDb;
    }

    public static WakandaDb getInstance(final Context context, final AppExecutors executors) {
        if (wakandaDb == null) {
            synchronized (WakandaDb.class) {
                if (wakandaDb == null) {
                    wakandaDb = buildDataBase(context, executors);
                }
            }
        }
        return wakandaDb;
    }

    private static WakandaDb buildDataBase(Context context, AppExecutors executors) {
        return Room.databaseBuilder(context, WakandaDb.class, DB_NAME).addCallback(new RoomDatabase.Callback() {
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
