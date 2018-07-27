package com.prolificinteractive.materialcalendarview.format;

import android.support.annotation.NonNull;
import android.util.Log;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Format using a {@linkplain DateFormat} instance.
 */
public class DateFormatMonthFormatter implements DayFormatter {

    private final DateFormat dateFormat;

    /**
     * Format using a default format
     */
    public DateFormatMonthFormatter() {
        this.dateFormat = new SimpleDateFormat("M月", Locale.CHINA);
    }

    /**
     * Format using a specific {@linkplain DateFormat}
     *
     * @param format the format to use
     */
    public DateFormatMonthFormatter(@NonNull DateFormat format) {
        this.dateFormat = format;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NonNull
    public String format(@NonNull CalendarDay day) {
        return dateFormat.format(day.getDate());
    }
}
