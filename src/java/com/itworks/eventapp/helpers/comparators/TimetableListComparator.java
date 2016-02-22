package com.itworks.eventapp.helpers.comparators;

import com.itworks.eventapp.models.BaseTimetable;

import java.util.Comparator;

public class TimetableListComparator implements Comparator<BaseTimetable> {
    @Override
    public int compare(BaseTimetable o1, BaseTimetable o2) {
        return o1.start_time.compareTo(o2.start_time);
    }
}
