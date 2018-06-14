package com.avengers.power

import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.avengers.appgalaxy.ScrollingActivity
import com.avengers.power.databinding.ActivityMainBinding
import com.avengers.zombiebase.ZombieBaseUtils
import com.spinytech.macore.router.RouterRequest

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var activityMainBinding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        var routerRequest = RouterRequest.obtain(this)
                .provider("GalaxyProvider")
                .action("GalaxyAction")
                .data("params1", "参数A")
                .data("params2", "参数B")
        var readsa = ZombieBaseUtils.onLocalRoute(this, routerRequest)


        Log.d("shejian", "路由调用成功" + readsa.data)
    }


    fun test() {


    }


    fun buttonClick(view: View) {
        var s = Intent(this, ScrollingActivity::class.java)
        startActivity(s)
    }


}
