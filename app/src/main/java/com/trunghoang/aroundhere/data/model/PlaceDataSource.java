package com.trunghoang.aroundhere.data.model;

import android.support.annotation.NonNull;

import java.util.List;

public interface PlaceDataSource {
    void getPlaces(SearchParams searchParams, @NonNull OnDataLoadedCallback<List<Place>> callback);
    void getPlace(SearchParams searchParams, @NonNull OnDataLoadedCallback<Place> callback);
    void getReviews(SearchParams searchParams, @NonNull OnDataLoadedCallback<List<Review>> callback);
}
