package com.avengers.power

import android.databinding.DataBindingUtil.setContentView
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.avengers.power.databinding.ActivityIndexMainBinding
import com.avengers.zombiebase.ZombieBaseUtils
import com.spinytech.macore.router.RouterRequest

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var activityMainBinding = setContentView<ActivityIndexMainBinding>(this, R.layout.activity_index_main)
        var routerRequest = RouterRequest.obtain(this)
                .provider("WakandaProvider")
                .action("WakandaAction")
                .data("params1", "参数A")
                .data("params2", "参数B")
        var readsa = ZombieBaseUtils.onLocalRoute(this, routerRequest)


        Log.d("shejian", "路由调用成功， 小程序重新出现现")
    }


    fun buttonClick(view: View) {
/*        RunTimePermission.requestPermission(this, {
            startActivity(Intent(this, ScrollingActivity::class.java))
        }, Permission.CALL_PHONE, Permission.CAMERA)*/
        ZombieBaseUtils.startNavigationBuild("/galaxy/scrollactivity")
                .withString("frompath", "power MainActivity").navigation()
    }

    fun buttonClickweather(view: View) {
        ZombieBaseUtils.startNavigationBuild("/galaxy/weatherMainActivity").navigation()
    }

    fun buttonClickLock(view: View) {
        ZombieBaseUtils.startNavigationBuild("/patternlock/patternLockActivity").navigation()
    }

}
