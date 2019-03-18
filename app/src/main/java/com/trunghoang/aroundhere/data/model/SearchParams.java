package com.trunghoang.aroundhere.data.model;

import android.location.Location;

public class SearchParams {
    private Location mLocation;
    private String mQuery;

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
}
