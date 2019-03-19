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

    private String mPage = PlacesKey.DEFAULT_PAGE;

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

    public String buildPlacesApiUrl(SearchParams searchParams) {
        String lat, lon;
        if (searchParams.getLocation() == null) {
            lat = String.valueOf(PlacesKey.DEFAULT_LAT);
            lon = String.valueOf(PlacesKey.DEFAULT_LON);
        } else {
            lat = String.valueOf(searchParams.getLocation().getLatitude());
            lon = String.valueOf(searchParams.getLocation().getLongitude());
        }
        String provinceId = searchParams.getLocation() == null ?
                PlacesKey.DEFAULT_PROVINCE_ID :
                getProvinceId(searchParams.getLocation());
        String provincePath = getProvince(provinceId);
        String query = searchParams.getQuery() == null ?
                PlacesKey.DEFAULT_Q :
                searchParams.getQuery();
        return new Uri.Builder()
                .scheme(Constants.SCHEME_HTTPS)
                .authority(Constants.API_BASE_URL)
                .appendPath(provincePath)
                .appendPath(Constants.PLACE)
                .appendQueryParameter(PlacesKey.API_QUERY_DS, PlacesKey.DEFAULT_DS)
                .appendQueryParameter(PlacesKey.API_QUERY_VT, PlacesKey.DEFAULT_VT)
                .appendQueryParameter(PlacesKey.API_QUERY_ST, PlacesKey.DEFAULT_ST)
                .appendQueryParameter(PlacesKey.API_QUERY_Q, query)
                .appendQueryParameter(PlacesKey.API_QUERY_LAT, lat)
                .appendQueryParameter(PlacesKey.API_QUERY_LON, lon)
                .appendQueryParameter(PlacesKey.API_QUERY_PAGE, mPage)
                .appendQueryParameter(PlacesKey.API_QUERY_PROVINCE_ID, provinceId)
                .appendQueryParameter(PlacesKey.API_QUERY_APPEND, PlacesKey.DEFAULT_APPEND)
                .build()
                .toString();
    }

    public String getReviewsApiUrl(SearchParams searchParams) {
        return new Uri.Builder()
                .scheme(Constants.SCHEME_HTTPS)
                .authority(Constants.API_BASE_URL)
                .appendPath(ReviewsKey.API_PATH_GET)
                .appendPath(ReviewsKey.API_PATH_REVIEW)
                .appendPath(ReviewsKey.API_PATH_RES_LOAD_MORE)
                .appendQueryParameter(ReviewsKey.API_QUERY_RES_ID, searchParams.getResId())
                .appendQueryParameter(ReviewsKey.API_QUERY_COUNT, ReviewsKey.DEFAULT_REVIEW_COUNT)
                .appendQueryParameter(ReviewsKey.API_QUERY_TYPE, ReviewsKey.DEFAULT_REVIEW_TYPE)
                .appendQueryParameter(ReviewsKey.API_QUERY_FROM_OWNER,
                        ReviewsKey.DEFAULT_FROM_OWNER)
                .appendQueryParameter(ReviewsKey.ADI_QUERY_IS_LATEST,
                        ReviewsKey.DEFAULT_REVIEW_IS_LATEST)
                .appendQueryParameter(ReviewsKey.API_QUERY_EXCLUDE_IDS,
                        ReviewsKey.DEFAULT_REVIEW_EXCLUDE_IDS)
                .build()
                .toString();
    }

    public String getPlaceApiUrl(SearchParams searchParams) {
        return new Uri.Builder()
                .scheme(Constants.SCHEME_HTTPS)
                .authority(Constants.API_BASE_URL)
                .appendPath(searchParams.getPlaceUrl())
                .build()
                .toString();
    }

    private String getProvince(String id) {
        return PROVINCES.get(id);
    }

    private String getProvinceId(Location location) {
        return PlacesKey.DEFAULT_PROVINCE_ID;
    }

    interface ReviewsKey {
        String API_PATH_GET = "__get";
        String API_PATH_REVIEW = "Review";
        String API_PATH_RES_LOAD_MORE = "ResLoadMore";
        String API_QUERY_RES_ID = "ResId";
        String API_QUERY_COUNT = "Count";
        String API_QUERY_TYPE = "Type";
        String API_QUERY_FROM_OWNER = "fromOwner";
        String ADI_QUERY_IS_LATEST = "isLatest";
        String API_QUERY_EXCLUDE_IDS = "ExcludeIds";
        String DEFAULT_REVIEW_RES_ID = "";
        String DEFAULT_REVIEW_COUNT = "10";
        String DEFAULT_REVIEW_TYPE = "1";
        String DEFAULT_FROM_OWNER = "";
        String DEFAULT_REVIEW_IS_LATEST = "true";
        String DEFAULT_REVIEW_EXCLUDE_IDS = "";
    }

    interface PlacesKey {
        String API_QUERY_DS = "ds";
        String API_QUERY_VT = "vt";
        String API_QUERY_ST = "st";
        String API_QUERY_Q = "q";
        String API_QUERY_LAT = "lat";
        String API_QUERY_LON = "lon";
        String API_QUERY_PAGE = "page";
        String API_QUERY_PROVINCE_ID = "provinceId";
        String API_QUERY_CATEGORY_ID = "categoryId";
        String API_QUERY_APPEND = "append";
        String DEFAULT_DS = "Restaurant";
        String DEFAULT_VT = "row";
        String DEFAULT_ST = "7";
        String DEFAULT_Q = "";
        double DEFAULT_LAT = 21.006887;
        double DEFAULT_LON = 105.7992413;
        String DEFAULT_PAGE = "1";
        String DEFAULT_PROVINCE_ID = "218";
        String DEFAULT_APPEND = "true";
    }
}
