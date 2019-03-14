package com.trunghoang.aroundhere.data.model;

import android.location.Location;
import android.support.annotation.NonNull;

import java.util.List;

public interface PlaceDataSource {
    interface LoadPlacesCallback {
        void OnPlacesLoaded(List<Place> places);

        void OnDataNotAvailable(Exception exception);
    }

    void getPlaces(Location location, @NonNull LoadPlacesCallback callback);
}
