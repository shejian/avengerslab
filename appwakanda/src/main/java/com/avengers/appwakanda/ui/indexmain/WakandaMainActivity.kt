package com.avengers.appwakanda.ui.indexmain

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.avengers.appwakanda.BR
import com.avengers.appwakanda.ui.indexmain.vm.IndexReaderListBean
import com.avengers.appwakanda.R
import com.avengers.appwakanda.databinding.ActivityWakandaMainBinding
import com.avengers.appwakanda.databinding.IndexlistDbdItemBinding
import com.avengers.appwakanda.ui.indexmain.vm.ContextItemBean
import com.avengers.appwakanda.ui.indexmain.vm.IndexListViewModel
import com.avengers.zombiebase.DataBindingLiteAdapter
import com.avengers.zombiebase.RcycyleHelper
import com.bty.retrofit.Api
import kotlinx.android.synthetic.main.activity_wakanda_main.fab
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

@Route(path = "/wakanda/mainactivity")
class WakandaMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var indexListViewModel = ViewModelProviders.of(this).get(IndexListViewModel::class.java)
        var activitydatabinding = DataBindingUtil.setContentView<ActivityWakandaMainBinding>(this@WakandaMainActivity, R.layout.activity_wakanda_main)
        //  setSupportActionBar(toolbar)
        RcycyleHelper.initBaseRcycyleViewNoScroll(this, activitydatabinding.recyclerView)


    /*    var dataBindingLiteAdapter = DataBindingLiteAdapter(ArrayList<Any>(),
                R.layout.indexlist_dbd_item, BR.,
                DataBindingLiteAdapter.DbdAdapterEvent<IndexlistDbdItemBinding, ContextItemBean> { holder, position, leaveRateDbdModel ->


                })

              activitydatabinding.recyclerView.setAdapter(dataBindingLiteAdapter)

*/
        Api.getSmartApi().getSmtIndex("line/show", 0, 20).enqueue(object : Callback<IndexReaderListBean> {

            override fun onResponse(call: Call<IndexReaderListBean>, response: Response<IndexReaderListBean>) {
                var indexReaderListBean = response.body()
                Toast.makeText(this@WakandaMainActivity, ("数据量:").plus(indexReaderListBean!!.data!!.list!!.size), Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<IndexReaderListBean>, t: Throwable) {

                Toast.makeText(this@WakandaMainActivity, "请求失败:", Toast.LENGTH_SHORT).show()
            }
        })
 /*       fab.setOnClickListener { view ->
            var fromPath = intent.getStringExtra("frompath")
            Snackbar.make(view, fromPath, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()


        }*/


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
