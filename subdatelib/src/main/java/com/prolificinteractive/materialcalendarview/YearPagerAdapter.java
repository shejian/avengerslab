package com.prolificinteractive.materialcalendarview;

import android.support.annotation.NonNull;
import android.support.v4.util.SparseArrayCompat;

import com.prolificinteractive.materialcalendarview.format.DateFormatMonthFormatter;
import com.prolificinteractive.materialcalendarview.format.DateFormatTitleFormatter;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Locale;

/**
 * Pager adapter backing the calendar view
 * 每年一页界面，
 * Created by Jervis on 2018/4.
 */
class YearPagerAdapter extends CalendarPagerAdapter<YearView> {

    YearPagerAdapter(MaterialCalendarView mcv) {
        super(mcv);
    }

    @Override
    protected YearView createView(int position) {
        setTitleFormatter(new DateFormatTitleFormatter(
                new SimpleDateFormat(
                        "yyyy年", Locale.getDefault()
                )));
        setDayFormatter(new DateFormatMonthFormatter());
        CalendarPagerView.DEFAULT_DAYS_IN_WEEK = 6;
        CalendarPagerView.DAY_NAMES_ROW=0;
        return new YearView(mcv, getItem(position));
    }

    @Override
    protected int indexOf(YearView view) {
        CalendarDay month = view.getFirstViewDay();
        return getRangeIndex().indexOf(month);
    }

    @Override
    protected boolean isInstanceOfView(Object object) {
        return object instanceof YearView;
    }

    @Override
    protected DateRangeIndex createRangeIndex(CalendarDay min, CalendarDay max) {
        return new Yearly(min, max);
    }

    public static class Yearly implements DateRangeIndex {

        private final CalendarDay min;
        private final int count;

        private SparseArrayCompat<CalendarDay> dayCache = new SparseArrayCompat<>();

        public Yearly(@NonNull CalendarDay min, @NonNull CalendarDay max) {
            //最小为当年的第一天
            this.min = CalendarDay.from(min.getYear(), 1, 1);
            //最大为12月的31
            max = CalendarDay.from(max.getYear(), 12, 31);
            this.count = indexOf(max) + 1;
        }

        @Override
        public int getCount() {
            return count;
        }

        @Override
        public int indexOf(CalendarDay day) {
            int yDiff = day.getYear() - min.getYear();
            return yDiff;
        }


        @Override
        public CalendarDay getItem(int position) {

            CalendarDay re = dayCache.get(position);
            if (re != null) {
                return re;
            }

            int numY = position;
            int year = min.getYear() + numY;
            re = CalendarDay.from(year, 0, 1);
            dayCache.put(position, re);
            return re;
        }
    }




}
