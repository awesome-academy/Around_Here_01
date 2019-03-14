package com.trunghoang.aroundhere.data.model;

import android.location.Location;
import android.support.annotation.NonNull;

import java.util.List;

public interface PlaceDataSource {
    void getPlaces(Location location, @NonNull OnDataLoadedCallback<List<Place>> callback);
    /*void getPlace(@NonNull OnDataLoadedCallback<Place> callback);*/
}
