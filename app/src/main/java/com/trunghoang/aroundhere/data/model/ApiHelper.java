package com.trunghoang.aroundhere.data.model;

import android.net.Uri;

import com.trunghoang.aroundhere.util.Constants;

public class ApiHelper {
    public static final String API_FIELD_DISTANCE = "Distance";
    public static final String API_FIELD_OPENING = "IsOpening";
    public static final String API_FIELD_PHOTO = "MobilePicturePath";
    public static final String API_FIELD_NAME = "Name";
    public static final String API_FIELD_ADDRESS = "Address";
    public static final String API_FIELD_ITEMS = "searchItems";
    private static final String API_QUERY_DS = "ds";
    private static final String API_QUERY_VT = "vt";
    private static final String API_QUERY_ST = "st";
    private static final String API_QUERY_Q = "q";
    private static final String API_QUERY_LAT = "lat";
    private static final String API_QUERY_LON = "lon";
    private static final String API_QUERY_PAGE = "page";
    private static final String API_QUERY_PROVINCEID = "provinceId";
    private static final String API_QUERY_CATEGORYID = "categoryId";
    private static final String API_QUERY_APPEND = "append";
    private static final String SCHEME_HTTPS = "https";
    private static final String SAMPLE_DS = "Restaurant";
    private static final String SAMPLE_VT = "row";
    private static final String SAMPLE_ST = "7";
    private static final String SAMPLE_Q = "";
    private static final String SAMPLE_LAT = "21.006887";
    private static final String SAMPLE_LON = "105.7992413";
    private static final String SAMPLE_PAGE = "1";
    private static final String SAMPLE_PROVINCEID = "218";
    private static final String SAMPLE_APPEND = "true";

    public static String getApiUrl() {
        return new Uri.Builder()
                .scheme(SCHEME_HTTPS)
                .authority(Constants.API_BASE_URL)
                .appendPath(Constants.HANOI)
                .appendPath(Constants.PLACE)
                .appendQueryParameter(API_QUERY_DS, SAMPLE_DS)
                .appendQueryParameter(API_QUERY_VT, SAMPLE_VT)
                .appendQueryParameter(API_QUERY_ST, SAMPLE_ST)
                .appendQueryParameter(API_QUERY_Q, SAMPLE_Q)
                .appendQueryParameter(API_QUERY_LAT, SAMPLE_LAT)
                .appendQueryParameter(API_QUERY_LON, SAMPLE_LON)
                .appendQueryParameter(API_QUERY_PAGE, SAMPLE_PAGE)
                .appendQueryParameter(API_QUERY_PROVINCEID, SAMPLE_PROVINCEID)
                .appendQueryParameter(API_QUERY_APPEND, SAMPLE_APPEND)
                .build()
                .toString();
    }
}
