package com.avengers.power

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.avengers.power.databinding.ActivityFullscreenBinding
import com.avengers.zombiebase.BasePermissionActivity


class FullScreenActivity : BasePermissionActivity() {


    override fun toMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var fullscreenBinding = DataBindingUtil.setContentView<ActivityFullscreenBinding>(this, R.layout.activity_fullscreen)
    }

}
