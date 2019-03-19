package com.trunghoang.aroundhere.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.text.format.DateUtils.DAY_IN_MILLIS;
import static android.text.format.DateUtils.FORMAT_ABBREV_RELATIVE;

public class AppDateUtils {
    private static long OFFSET_VN = 8 * 1000 * 60 * 60;
    private static final SimpleDateFormat TIME_FORMATTER = new SimpleDateFormat("HH:mm aa",
            Locale.US);

    public static String getDateFromTime(long time) {
        return android.text.format.DateUtils.getRelativeTimeSpanString(time,
                new Date().getTime(),
                DAY_IN_MILLIS,
                FORMAT_ABBREV_RELATIVE).toString();
    }

    public static String getTimeFormat(long time) {
        return TIME_FORMATTER.format(time - OFFSET_VN);
    }
}
