package com.avengers.power

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.avengers.zombiebase.ZombieBaseUtils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var zombieBaseUtils= ZombieBaseUtils()
        zombieBaseUtils.getZombieLibraryInfo()


    }


    fun test() {


    }


}
