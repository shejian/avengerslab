package com.avengers.appwakanda.db.room;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.avengers.appwakanda.db.dao.IndexDataDao;
import com.avengers.appwakanda.ui.indexmain.vm.ContextItemBean;
import com.avengers.appwakanda.ui.indexmain.vm.IndexReaderListBean;
import com.avengers.zombiebase.AppExecutors;


@Database(entities = {ContextItemBean.class}, version = 1)
public abstract class MyDataBase extends RoomDatabase {


    public abstract IndexDataDao indexDataDao();


    private static MyDataBase myDataBase;

    public static MyDataBase getInstance(final Context context, final AppExecutors executors) {
        if (myDataBase == null) {
            synchronized (MyDataBase.class) {
                if (myDataBase == null) {
                    myDataBase = buildDataBase(context, executors);
                }
            }
        }
        return myDataBase;
    }

    private static MyDataBase buildDataBase(Context context, AppExecutors executors) {
        return Room.databaseBuilder(context, MyDataBase.class, "database_fundatation").addCallback(new Callback() {
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
