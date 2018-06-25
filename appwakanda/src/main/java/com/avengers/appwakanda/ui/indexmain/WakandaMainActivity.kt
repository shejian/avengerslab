package com.avengers.appwakanda.ui.indexmain

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.alibaba.android.arouter.facade.annotation.Route
import com.avengers.appwakanda.BR
import com.avengers.appwakanda.R
import com.avengers.appwakanda.databinding.ActivityWakandaMainBinding
import com.avengers.appwakanda.databinding.IndexlistDbdItemBinding
import com.avengers.appwakanda.db.room.entity.ContextItemEntity
import com.avengers.appwakanda.ui.indexmain.vm.IndexListViewModel
import com.avengers.zombiebase.DataBindingLiteAdapter
import com.avengers.zombiebase.RcycyleHelper
import java.util.*

@Route(path = "/wakanda/mainactivity")
class WakandaMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var indexListViewModel = ViewModelProviders.of(this).get(IndexListViewModel::class.java)
        var activityDataBinding = DataBindingUtil.setContentView<ActivityWakandaMainBinding>(this@WakandaMainActivity, R.layout.activity_wakanda_main)

        indexListViewModel.init()

        RcycyleHelper.initBaseRcycyleView(this, activityDataBinding.recyclerView)
        var dataBindingLiteAdapter = DataBindingLiteAdapter<ContextItemEntity, IndexlistDbdItemBinding>(
                ArrayList<ContextItemEntity>(),
                R.layout.indexlist_dbd_item,
                BR.contextItem,
                DataBindingLiteAdapter.DbdAdapterEvent
                { holder, position, viewModel ->

                }
        )

        activityDataBinding.recyclerView.adapter = dataBindingLiteAdapter

        indexListViewModel.getIndexListLiveData().observe(this, Observer { list ->
            dataBindingLiteAdapter.setNewList(list)
        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
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
