package com.trunghoang.aroundhere.data.model;

import android.location.Location;
import android.net.Uri;

import com.trunghoang.aroundhere.util.Constants;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GlobalData {
    private static GlobalData sInstance;
    private static final Map<String, String> PROVINCES;

    static {
        Map<String, String> mapProvinces = new HashMap<>();
        mapProvinces.put("218", "ha-noi");
        PROVINCES = Collections.unmodifiableMap(mapProvinces);
    }

    private String mPage;

    private GlobalData() {
    }

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
            lat = String.valueOf(PlacesKey.DEFAULT_LAT);
            lon = String.valueOf(PlacesKey.DEFAULT_LON);
        } else {
            lat = String.valueOf(location.getLatitude());
            lon = String.valueOf(location.getLongitude());
        }
        String provinceId = getProvinceId(location);
        String provincePath = getProvince(provinceId);
        return new Uri.Builder()
                .scheme(Constants.SCHEME_HTTPS)
                .authority(Constants.API_BASE_URL)
                .appendPath(provincePath)
                .appendPath(Constants.PLACE)
                .appendQueryParameter(PlacesKey.API_QUERY_DS, PlacesKey.DEFAULT_DS)
                .appendQueryParameter(PlacesKey.API_QUERY_VT, PlacesKey.DEFAULT_VT)
                .appendQueryParameter(PlacesKey.API_QUERY_ST, PlacesKey.DEFAULT_ST)
                .appendQueryParameter(PlacesKey.API_QUERY_Q, PlacesKey.DEFAULT_Q)
                .appendQueryParameter(PlacesKey.API_QUERY_LAT, lat)
                .appendQueryParameter(PlacesKey.API_QUERY_LON, lon)
                .appendQueryParameter(PlacesKey.API_QUERY_PAGE, mPage)
                .appendQueryParameter(PlacesKey.API_QUERY_PROVINCEID, provinceId)
                .appendQueryParameter(PlacesKey.API_QUERY_APPEND, PlacesKey.DEFAULT_APPEND)
                .build()
                .toString();
    }

    public String getReviewsApiUrl(String resId) {
        return new Uri.Builder()
                .scheme(Constants.SCHEME_HTTPS)
                .authority(Constants.API_BASE_URL)
                .appendPath(ReviewsKey.API_PATH_GET)
                .appendPath(ReviewsKey.API_PATH_REVIEW)
                .appendPath(ReviewsKey.API_PATH_RESLOADMORE)
                .appendQueryParameter(ReviewsKey.API_QUERY_RES_ID, resId)
                .appendQueryParameter(ReviewsKey.API_QUERY_COUNT, ReviewsKey.DEFAULT_REVIEW_COUNT)
                .appendQueryParameter(ReviewsKey.API_QUERY_TYPE, ReviewsKey.DEFAULT_REVIEW_TYPE)
                .appendQueryParameter(ReviewsKey.API_QUERY_FROM_OWNER,
                        ReviewsKey.DEFAULT_FROM_OWNER)
                .appendQueryParameter(ReviewsKey.API_QUERY_IS_LASTEST,
                        ReviewsKey.DEFAULT_REVIEW_IS_LASTEST)
                .appendQueryParameter(ReviewsKey.API_QUERY_EXCLUDEIDS,
                        ReviewsKey.DEFAULT_REVIEW_EXCLUDEIDS)
                .build()
                .toString();
    }

    public String getPlaceApiUrl(String detailUrl) {
        return new Uri.Builder()
                .scheme(Constants.SCHEME_HTTPS)
                .authority(Constants.API_BASE_URL)
                .appendPath(detailUrl)
                .build()
                .toString();
    }

    private String getProvince(String id) {
        return PROVINCES.get(id);
    }

    private String getProvinceId(Location location) {
        return PlacesKey.DEFAULT_PROVINCEID;
    }

    interface ReviewsKey {
        String API_PATH_GET = "__get";
        String API_PATH_REVIEW = "Review";
        String API_PATH_RESLOADMORE = "ResLoadMore";
        String API_QUERY_RES_ID = "ResId";
        String API_QUERY_COUNT = "Count";
        String API_QUERY_TYPE = "Type";
        String API_QUERY_FROM_OWNER = "fromOwner";
        String API_QUERY_IS_LASTEST = "isLatest";
        String API_QUERY_EXCLUDEIDS = "ExcludeIds";
        String DEFAULT_REVIEW_RES_ID = "";
        String DEFAULT_REVIEW_COUNT = "10";
        String DEFAULT_REVIEW_TYPE = "1";
        String DEFAULT_FROM_OWNER = "";
        String DEFAULT_REVIEW_IS_LASTEST = "true";
        String DEFAULT_REVIEW_EXCLUDEIDS = "";
    }

    interface PlacesKey {
        String API_QUERY_DS = "ds";
        String API_QUERY_VT = "vt";
        String API_QUERY_ST = "st";
        String API_QUERY_Q = "q";
        String API_QUERY_LAT = "lat";
        String API_QUERY_LON = "lon";
        String API_QUERY_PAGE = "page";
        String API_QUERY_PROVINCEID = "provinceId";
        String API_QUERY_CATEGORYID = "categoryId";
        String API_QUERY_APPEND = "append";
        String DEFAULT_DS = "Restaurant";
        String DEFAULT_VT = "row";
        String DEFAULT_ST = "7";
        String DEFAULT_Q = "";
        double DEFAULT_LAT = 21.006887;
        double DEFAULT_LON = 105.7992413;
        String DEFAULT_PAGE = "1";
        String DEFAULT_PROVINCEID = "218";
        String DEFAULT_APPEND = "true";
    }
}
