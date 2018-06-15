package com.avengers.power;

import android.content.res.Configuration;

import com.avengers.appgalaxy.GalaxyProvider;
import com.avengers.intfacebase.RoaterBaseProviderManager;
import com.spinytech.macore.multiprocess.BaseApplicationLogic;
import com.spinytech.macore.router.LocalRouter;

public class MyApplicationLogic extends BaseApplicationLogic {

    @Override
    public void onCreate() {
        super.onCreate();
        //注册Provider
        LocalRouter localRouter = LocalRouter.getInstance(mApplication);
        RoaterBaseProviderManager.initRegProvider(localRouter);
        initSubRegisterProvider(localRouter);
    }

    private void initSubRegisterProvider(LocalRouter localRouter) {
        localRouter.registerProvider("WakandaProvider", new GalaxyProvider());
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
