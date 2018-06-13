package com.avengers.power;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.support.multidex.MultiDex;

import com.spinytech.macore.MaApplication;

public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        //注册全局的activity生命周期监听
        registerActivityLifecycleCallbacks(new MyAppActivityLifecycleCallbacks());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
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
     *
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
