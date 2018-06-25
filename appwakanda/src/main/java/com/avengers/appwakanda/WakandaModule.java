package com.avengers.appwakanda;


import com.avengers.appwakanda.db.room.RoomHelper;
import com.avengers.zombiebase.AppExecutors;
import com.avengers.zombiebase.ApplicationInitBase;
import com.avengers.zombiebase.BaseAppLogic;

/**
 * @author jvis
 * 相关全局组件需要初始化的位置
 */
public class WakandaModule extends BaseAppLogic {
    public static AppExecutors appExecutors = null;

    @Override
    public void onCreate() {
        super.onCreate();
        appExecutors = ApplicationInitBase.getInstanceExecutors();
        RoomHelper.getInstance(mApplication, ApplicationInitBase.getInstanceExecutors());
    }


}
