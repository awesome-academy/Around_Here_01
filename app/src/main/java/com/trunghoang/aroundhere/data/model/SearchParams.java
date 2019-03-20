package com.trunghoang.aroundhere.data.model;

import android.location.Location;

public class SearchParams {
    private Location mLocation;
    private String mQuery;
    private boolean mIsRemote;
    private boolean mIsLocal;
    private String mPlaceUrl;
    private String mResId;
    private Place mPlace;

    public Location getLocation() {
        return mLocation;
    }

    public void setLocation(Location location) {
        mLocation = location;
    }

    public String getQuery() {
        return mQuery;
    }

    public void setQuery(String query) {
        mQuery = query;
    }

    public boolean isRemote() {
        return mIsRemote;
    }

    public void setRemote(boolean remote) {
        mIsRemote = remote;
    }

    public boolean isLocal() {
        return mIsLocal;
    }

    public void setLocal(boolean local) {
        mIsLocal = local;
    }

    public String getPlaceUrl() {
        return mPlaceUrl;
    }

    public void setPlaceUrl(String placeUrl) {
        mPlaceUrl = placeUrl;
    }

    public String getResId() {
        return mResId;
    }

    public void setResId(String resId) {
        mResId = resId;
    }

    public Place getPlace() {
        return mPlace;
    }

    public void setPlace(Place place) {
        mPlace = place;
    }
}
