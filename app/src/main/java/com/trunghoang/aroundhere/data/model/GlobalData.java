package com.trunghoang.aroundhere.data.model;

import android.location.Location;
import android.net.Uri;

import com.trunghoang.aroundhere.util.Constants;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GlobalData {
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
    private static final String DEFAULT_DS = "Restaurant";
    private static final String DEFAULT_VT = "row";
    private static final String DEFAULT_ST = "7";
    private static final String DEFAULT_Q = "";
    private static final double DEFAULT_LAT = 21.006887;
    private static final double DEFAULT_LON = 105.7992413;
    private static final String DEFAULT_PAGE = "1";
    private static final String DEFAULT_PROVINCEID = "218";
    private static final String DEFAULT_APPEND = "true";
    private static GlobalData sInstance;
    private static final Map<String, String> PROVINCES;
    static {
        Map<String, String> mapProvinces = new HashMap<>();
        mapProvinces.put("218", "ha-noi");
        PROVINCES = Collections.unmodifiableMap(mapProvinces);
    }
    private String mPage;

    private GlobalData() {}

    public static GlobalData getInstance() {
        if (sInstance == null) {
            synchronized (GlobalData.class) {
                if (sInstance == null) sInstance = new GlobalData();
            }
        }
        return sInstance;
    }

    public String getPage() {
        return this.mPage;
    }

    public void setPage(String page) {
        mPage = page;
    }

    public String buildPlacesApiUrl(Location location) {
        String lat, lon;
        if (location == null) {
            lat = String.valueOf(DEFAULT_LAT);
            lon = String.valueOf(DEFAULT_LON);
        } else {
            lat = String.valueOf(location.getLatitude());
            lon = String.valueOf(location.getLongitude());
        }
        String provinceId = getProvinceId(location);
        String provincePath = getProvince(provinceId);
        return new Uri.Builder()
                .scheme(SCHEME_HTTPS)
                .authority(Constants.API_BASE_URL)
                .appendPath(provincePath)
                .appendPath(Constants.PLACE)
                .appendQueryParameter(API_QUERY_DS, DEFAULT_DS)
                .appendQueryParameter(API_QUERY_VT, DEFAULT_VT)
                .appendQueryParameter(API_QUERY_ST, DEFAULT_ST)
                .appendQueryParameter(API_QUERY_Q, DEFAULT_Q)
                .appendQueryParameter(API_QUERY_LAT, lat)
                .appendQueryParameter(API_QUERY_LON, lon)
                .appendQueryParameter(API_QUERY_PAGE, mPage)
                .appendQueryParameter(API_QUERY_PROVINCEID, provinceId)
                .appendQueryParameter(API_QUERY_APPEND, DEFAULT_APPEND)
                .build()
                .toString();
    }

    private String getProvince(String id) {
        return PROVINCES.get(id);
    }

    private String getProvinceId(Location location) {
        return DEFAULT_PROVINCEID;
    }
}
