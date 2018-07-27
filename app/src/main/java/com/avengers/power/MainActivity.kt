package com.avengers.power

import android.databinding.DataBindingUtil.setContentView
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.avengers.power.databinding.ActivityIndexMainBinding
import com.avengers.zombiebase.BaseActivity
import com.avengers.zombiebase.LogU
import com.avengers.zombiebase.ZombieBaseUtils
import com.avengers.zombiebase.widget.calendarwidget.CalendarDialogBuild
import com.avengers.zombiebase.widget.calendarwidget.CalendarDialogBuild.ICalendarDialogEvent
import com.avengers.zombiebase.widget.calendarwidget.CalendarDialogBuild.showRangeDialog
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.spinytech.macore.router.RouterRequest
import java.util.*

class MainActivity : BaseActivity() {

    override fun reloadData() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var activityMainBinding = setContentView<ActivityIndexMainBinding>(this,
                R.layout.activity_index_main)
        var routerRequest = RouterRequest.obtain(this)
                .provider("WakandaProvider")
                .action("WakandaAction")
                .data("params1", "参数A")
                .data("params2", "参数B")
        var readsa = ZombieBaseUtils.onLocalRoute(this, routerRequest)
        activityMainBinding.topbar?.toolbar?.hideBack()
        activityMainBinding.topbar?.toolbar?.setMiddleText("我是主标题")
    }


    fun buttonClick(view: View) {
/*        RuntimePermissionUtil.requestPermission(this, {
            startActivity(Intent(this, ScrollingActivity::class.java))
        }, Permission.CALL_PHONE, Permission.CAMERA)*/
        ZombieBaseUtils.startNavigationBuild("/galaxy/scrollactivity")
                .withString("frompath", "power MainActivity").navigation()
    }

    fun buttonClickweather(view: View) {
        ZombieBaseUtils.startNavigationBuild("/galaxy/weatherMainActivity").navigation()
    }

    fun buttonClickLock(view: View) {
        ZombieBaseUtils.startNavigationBuild("/patternlock/patternLockActivity").navigation()
    }

    fun buttonClickLocation(view: View) {
        ZombieBaseUtils.startNavigationBuild("/location/LocationActivity").navigation()
    }

    fun buttonCanlClickLocation(view: View) {
        /*
        "选择周"
         CalendarDialogBuild.showSelectionWeeksDialog(this,
                   calendarDialogEvent,
                   Date(1519112440000L),
                   Date(1532677240000L))*/

        /*
        "选择年月"
             CalendarDialogBuild.showMonthDialog(this,
                       calendarDialogEvent,
                       Date(1519112440000L),
                       Date(1532677240000L))*/

        /* "选择天"
             CalendarDialogBuild.showRangeDialog(this,
                      calendarDialogEvent,
                      Date(1519112440000),
                      Date(1532677240000L))*/
    }


    var calendarDialogEvent: ICalendarDialogEvent = object : ICalendarDialogEvent {
        override fun onRangeSelected(startDay: CalendarDay, endDay: CalendarDay?) {
        }

        override fun onRangeSelected(list: MutableList<Date>?) {
        }

    }

}
