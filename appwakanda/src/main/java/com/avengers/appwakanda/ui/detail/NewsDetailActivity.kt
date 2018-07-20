package com.avengers.appwakanda.ui.detail

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.avengers.appwakanda.R
import com.avengers.zombiebase.SnackbarUtil
import com.avengers.zombiebase.widget.CustomToolBar

import kotlinx.android.synthetic.main.activity_news_detail.*

class NewsDetailActivity : AppCompatActivity() {
    var mainView: View? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)
        mainView = findViewById<View>(R.id.main_view)

        //   setSupportActionBar(toolbar)
        /*    fab.setOnClickListener { view ->
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
            }*/
        var toolbar = findViewById<CustomToolBar>(R.id.topbar)
        toolbar.setLeftText("我是左标题")
        toolbar.setGoBack(View.OnClickListener {
            finish()
        })
        toolbar.inflateMenu(R.menu.menu_news_detail)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_settings -> {
                    SnackbarUtil.showLong(mainView!!, "设置")
                    true
                }
                R.id.action_settings4 -> true
                else -> super.onOptionsItemSelected(it)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                SnackbarUtil.showLong(mainView!!, "设置")
                true
            }
            R.id.action_settings4 -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

}
