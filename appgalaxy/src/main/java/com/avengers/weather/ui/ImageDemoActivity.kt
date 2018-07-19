package com.avengers.weather.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.avengers.R
import com.avengers.zombiebase.glide.GlideImageUtil
import com.avengers.zombiebase.matisse.MatisseChooser
import com.zhihu.matisse.Matisse
import kotlinx.android.synthetic.main.image_activity.*

/**
 * Created by duo.chen on 2018/7/19
 */
class ImageDemoActivity : AppCompatActivity() {


    private val REQUEST_CODE_CHOOSE = 1111

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.image_activity)
    }

    fun matisseChooser(view: View) {
        MatisseChooser.choose(this,REQUEST_CODE_CHOOSE)
    }

    override fun onActivityResult(requestCode: Int,resultCode: Int,data: Intent?) {
        super.onActivityResult(requestCode,resultCode,data)
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == Activity.RESULT_OK) {
            var string = Matisse.obtainPathResult(data).get(0)

            GlideImageUtil.show(this,string,image)
        }
    }
}
