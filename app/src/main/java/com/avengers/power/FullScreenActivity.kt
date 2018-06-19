package com.avengers.power

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.avengers.power.databinding.ActivityFullscreenBinding
import com.avengers.zombiebase.BasePremissionActivity
import com.yanzhenjie.permission.Permission


class FullScreenActivity : BasePremissionActivity() {


    override fun initPermission() {
        requestPermission(Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE)
    }

    override fun toMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var fullscreenBinding = DataBindingUtil.setContentView<ActivityFullscreenBinding>(this, R.layout.activity_fullscreen)
    }

}
