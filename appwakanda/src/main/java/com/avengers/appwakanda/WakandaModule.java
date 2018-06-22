package com.avengers.appwakanda;

import android.app.Application;
import android.content.Context;

import com.avengers.appwakanda.db.room.MyDataBase;
import com.avengers.zombiebase.ApplicationInitBase;

public class WakandaModule {



    Application mApp;


    public void setmApp(Application mApp) {
        this.mApp = mApp;
    }

    public MyDataBase getDataBase(Context context) {
        return MyDataBase.getInstance(context, ApplicationInitBase.getInstanceExecutors());
    }


}
