package com.avengers.appgalaxy.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.alibaba.android.arouter.facade.annotation.Route
import com.avengers.BR
import com.avengers.R
import com.avengers.databinding.ActivityScrollingBinding
import com.avengers.appgalaxy.ui.vm.ScrollingViewModel
import com.avengers.zombiebase.RecycleHelper
import com.avengers.zombiebase.ZombieBaseUtils
import com.spinytech.macore.router.RouterRequest

@Route(path = "/galaxy/scrollactivity")
class ScrollingActivity : AppCompatActivity() {

    var databinding: ActivityScrollingBinding? = null
    var scrollingViewModel: ScrollingViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databinding = DataBindingUtil.setContentView(this, R.layout.activity_scrolling)

        scrollingViewModel = ViewModelProviders.of(this).get(ScrollingViewModel::class.java)
        scrollingViewModel?.initModel()
        setSupportActionBar(databinding?.toolbar)
        databinding?.fab?.setOnClickListener { view ->

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

        initRecycle()
    }

    private var adapter: ScrollingAdapter<ActivityScrollingBinding>? = null

    private fun initRecycle() {
        RecycleHelper.initBaseRecycleView(this, databinding?.inclodeview?.recyclerView!!)
        adapter = ScrollingAdapter(R.layout.list_dbd_item, BR.itemobj)
        databinding?.inclodeview?.recyclerView?.adapter = adapter
        scrollingViewModel?.getLiveData()?.observe(this, Observer {
            this.adapter?.submitList(it)
        })


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
