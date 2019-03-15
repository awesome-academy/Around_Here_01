package com.trunghoang.aroundhere.data.model;

import android.location.Location;
import android.support.annotation.NonNull;

import java.util.List;

public interface PlaceDataSource {
    void getPlaces(Location location, @NonNull OnDataLoadedCallback<List<Place>> callback);
    void getPlace(String placeUrl, @NonNull OnDataLoadedCallback<Place> callback);
    void getReviews(String resId, @NonNull OnDataLoadedCallback<List<Review>> callback);
}
