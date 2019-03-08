package com.trunghoang.aroundhere.data.model;

import android.support.annotation.NonNull;

import java.util.List;

public interface PlaceDataSource {
    interface LoadPlacesCallback {
        void OnPlacesLoaded(List<Place> places);

        void OnDataNotAvailable(Exception exception);
    }

    void getPlaces(@NonNull LoadPlacesCallback callback);
}
