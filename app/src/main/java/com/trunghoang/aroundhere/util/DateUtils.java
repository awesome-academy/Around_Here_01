package com.trunghoang.aroundhere.util;

import java.util.Date;

import static android.text.format.DateUtils.DAY_IN_MILLIS;
import static android.text.format.DateUtils.FORMAT_ABBREV_RELATIVE;

public class DateUtils {
    public static String getDateFromTime(long time) {
        return android.text.format.DateUtils.getRelativeTimeSpanString(time,
                new Date().getTime(),
                DAY_IN_MILLIS,
                FORMAT_ABBREV_RELATIVE).toString();
    }
}
