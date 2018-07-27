package com.prolificinteractive.materialcalendarview;

@Experimental
public enum CalendarMode {

    MONTHS(6),
    WEEKS(1),
    YEARS(2);//增加的每一年的月的选择模式

    final int visibleWeeksCount;

    CalendarMode(int visibleWeeksCount) {
        this.visibleWeeksCount = visibleWeeksCount;
    }
}
