package com.trunghoang.aroundhere.util;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class PlaceUpdateType {
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({FAVORITES_ADDED, FAVORITES_REMOVED, VISITED_ADDED, VISITED_REMOVED})
    public @interface PlaceUpdateDef {
    }
    public static final int FAVORITES_ADDED = 1;
    public static final int FAVORITES_REMOVED = 2;
    public static final int VISITED_ADDED = 3;
    public static final int VISITED_REMOVED = 4;
}
