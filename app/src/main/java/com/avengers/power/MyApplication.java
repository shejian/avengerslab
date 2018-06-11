package com.avengers.power;

import android.content.res.Configuration;
import android.support.multidex.MultiDexApplication;

public class MyApplication extends MultiDexApplication {


    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * 当应用程序被终止时将被调用，但是被系统干掉的情况除外
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    /**
     * 配置改变时调用
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    /**
     * 当后台程序已经被终止时且资源还匮乏时会调用这个方法，可以做一些释放的工作
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
