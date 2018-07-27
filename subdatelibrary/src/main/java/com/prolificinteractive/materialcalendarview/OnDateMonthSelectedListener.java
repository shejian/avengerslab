package com.prolificinteractive.materialcalendarview;

import android.support.annotation.NonNull;

/**
 * The callback used to indicate a date has been selected or deselected
 * 增加了针对月份选择的回调函数，返回的是当月的第一天和下月的第一天
 * Created by Jervis on 2018/4.
 */
public interface OnDateMonthSelectedListener {

    void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, @NonNull CalendarDay dateEnd, boolean selected);
}
