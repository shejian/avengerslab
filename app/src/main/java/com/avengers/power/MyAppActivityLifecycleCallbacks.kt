package com.avengers.power

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log

import com.avengers.zombiebase.SystemUtil

/**
 * 注册全局的activity生命周期监听
 *
 * @author jvis
 */
class MyAppActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        SystemUtil.setSystemStatusBar(activity.window)
    }

    override fun onActivityStarted(activity: Activity) {

    }

    override fun onActivityResumed(activity: Activity) {

    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStopped(activity: Activity) {

    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {

    }

    override fun onActivityDestroyed(activity: Activity) {

    }
}
