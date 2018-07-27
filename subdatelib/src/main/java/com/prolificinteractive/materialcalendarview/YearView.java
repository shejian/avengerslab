package com.prolificinteractive.materialcalendarview;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.util.Log;

import com.prolificinteractive.materialcalendarview.format.DateFormatMonthFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Locale;

/**
 * Display a year of {@linkplain DayView}s and
 * seven {@linkplain YearView}s.
 * 展示一年的月数据，共12月
 * Created by Jervis on 2018/4.
 */
@Experimental
@SuppressLint("ViewConstructor")
public class YearView extends CalendarPagerView {

    public YearView(@NonNull MaterialCalendarView view,
                    CalendarDay firstViewDay
    ) {
        super(view, firstViewDay, -1);
    }

    Collection<DayView> dayViews;

    @Override
    protected void buildDayViews(Collection<DayView> dayViews, Calendar calendar) {

        for (int i = 0; i < DEFAULT_MONTH_IN_YEAR; i++) {
            addMonthView(dayViews, calendar, i);
        }
        calendar.add(Calendar.YEAR, 1);
        this.dayViews = dayViews;
    }

    protected void addMonthView(Collection<DayView> dayViews, Calendar calendar, int ii) {
        calendar.set(Calendar.MONTH, ii);
        CalendarDay day = CalendarDay.from(calendar);
        DayView dayView = new DayView(getContext(), day);
        dayView.setOnClickListener(this);
        dayViews.add(dayView);
        addView(dayView, new LayoutParams());
    }

    @Override
    public void buildWeekDays(Calendar calendar) {
        //不需要周
        return;
    }

    private static final Calendar tempWorkingCalendar = CalendarUtils.getInstance();

    @Override
    public Calendar resetAndGetWorkingCalendar() {
        getFirstViewDay().copyTo(tempWorkingCalendar);
        return tempWorkingCalendar;
    }

    @Override
    protected boolean isDayEnabled(CalendarDay day) {
        return true;
    }

    @Override
    protected int getRows() {
        return DAY_NAMES_ROW+2;
    }

    @Override
    public void setSelectedDates(Collection<CalendarDay> dates) {
        Collection<CalendarDay> newDate = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        for (CalendarDay day : dates) {
            day.copyToMonthOnly(calendar);
            newDate.add(CalendarDay.from(calendar));
        }
        for (DayView dayView : dayViews) {//循环是有天View，取没个dayview对应的天在整个
            CalendarDay day = dayView.getDate();
            dayView.setChecked(newDate != null && newDate.contains(day));
        }
        postInvalidate();
    }
}
