package com.avengers.power

import android.databinding.DataBindingUtil.setContentView
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.avengers.power.databinding.ActivityIndexMainBinding
import com.avengers.zombiebase.BaseActivity
import com.avengers.zombiebase.LogU
import com.avengers.zombiebase.ZombieBaseUtils
import com.spinytech.macore.router.RouterRequest

class MainActivity : BaseActivity() {

    override fun reloadData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var activityMainBinding = setContentView<ActivityIndexMainBinding>(this, R.layout.activity_index_main)
        var routerRequest = RouterRequest.obtain(this)
                .provider("WakandaProvider")
                .action("WakandaAction")
                .data("params1", "参数A")
                .data("params2", "参数B")
        var readsa = ZombieBaseUtils.onLocalRoute(this, routerRequest)
        LogU.d("shejian", "fsdfsdfsdf")
        activityMainBinding.topbar?.toolbar?.hideBack()
        activityMainBinding.topbar?.toolbar?.setMiddleText("我是主标题")
    }


    fun buttonClick(view: View) {
/*        RuntimePermissionUtil.requestPermission(this, {
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
