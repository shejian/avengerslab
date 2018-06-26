package com.avengers.power;

import android.content.Context;
import android.content.res.Configuration;
import android.support.multidex.MultiDex;

import com.avengers.appgalaxy.GalaxyModule;
import com.avengers.appwakanda.BuildConfig;
import com.avengers.appwakanda.WakandaModule;
import com.avengers.zombiebase.AppExecutors;
import com.avengers.zombiebase.ApplicationInitBase;
import com.avengers.zombiebase.BaseApplication;

/**
 * @author jvis
 * @date
 */
public class MyApplication extends BaseApplication {

    public static AppExecutors appExecutors;

    @Override
    public void onCreate() {
        super.onCreate();
        //注册全局的activity生命周期监听
        registerActivityLifecycleCallbacks(new MyAppActivityLifecycleCallbacks());
        appExecutors = ApplicationInitBase.getInstanceExecutors();
        ApplicationInitBase.initARouter(this);
        ApplicationInitBase.initWebServer(BuildConfig.BASE_URL);

    }


    public AppExecutors getAppExecutors() {
        return appExecutors;
    }

    @Override
    public void initializeAllProcessRouter() {
        //注册多个进程的本地路由
    }

    @Override
    protected void initializeLogic() {
        //注册Application逻辑
        //进程名称，初始化的优先级，application逻辑类，在需要的会反射初始化
        registerApplicationLogic("com.avengers.power", 999, MyApplicationLogic.class);
        registerApplicationLogic("com.avengers.power", 998, WakandaModule.class);
        registerApplicationLogic("com.avengers.power", 997, GalaxyModule.class);
    }

    @Override
    public boolean needMultipleProcess() {
        //标记是否是是多进程的，是，返回true，否，返回false
        return false;
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
