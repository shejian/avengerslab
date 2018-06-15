package com.avengers.appgalaxy

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.alibaba.android.arouter.facade.annotation.Route
import com.avengers.zombiebase.ZombieBaseUtils
import com.spinytech.macore.router.RouterRequest
import kotlinx.android.synthetic.main.activity_scrolling.*

@Route(path = "/galaxy/scrollactivity")
class ScrollingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->

            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            //  ZombieBaseUtils.startNavigation("/wakanda/mainactivity")
            ZombieBaseUtils.startNavigationBuild("/wakanda/mainactivity")
                    .withString("frompath", "/galaxy/scrollactivity").navigation()
        }

        var request234 = RouterRequest.obtain(this)
                .provider("util")
                .action("mymd5")
                .data("param1", "hello")
                .data("param2", "world")
                .`object`(String())

        var res = ZombieBaseUtils.onLocalRoute(this, request234)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
