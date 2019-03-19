package com.trunghoang.aroundhere.data.model;

import android.support.annotation.NonNull;

import com.trunghoang.aroundhere.data.db.PlaceDAO;

import java.util.List;

public class PlaceRepository implements PlaceDataSource {
    private static PlaceRepository sInstance;
    private PlaceDataSource mRemote;
    private PlaceDataSource mLocal;
    private PlaceDAO mPlaceDAO;

    private PlaceRepository(PlaceDAO placeDAO, PlaceDataSource remote, PlaceDataSource local) {
        mRemote = remote;
        mLocal = local;
        mPlaceDAO = placeDAO;
    }

    public static PlaceRepository getInstance(PlaceDAO placeDAO,
                                              PlaceDataSource remote,
                                              PlaceDataSource local) {
        if (sInstance == null) {
            sInstance = new PlaceRepository(placeDAO, remote, local);
        }
        return sInstance;
    }

    @Override
    public void getPlaces(SearchParams searchParams, @NonNull OnDataLoadedCallback<List<Place>> callback) {
        if (searchParams.isRemote()) mRemote.getPlaces(searchParams, callback);
    }

    @Override
    public void getPlace(SearchParams searchParams, @NonNull OnDataLoadedCallback<Place> callback) {
        if (searchParams.isRemote()) mRemote.getPlace(searchParams, callback);
        if (searchParams.isLocal()) mLocal.getPlace(searchParams, callback);
    }

    @Override
    public void getReviews(SearchParams searchParams, @NonNull OnDataLoadedCallback<List<Review>> callback) {
        if (searchParams.isRemote()) mRemote.getReviews(searchParams, callback);
    }

    @Override
    public void getFavoredPlaces(@NonNull OnDataLoadedCallback<List<Place>> callback) {
        mLocal.getFavoredPlaces(callback);
    }

    @Override
    public void getVisitedPlaces(@NonNull OnDataLoadedCallback<List<Place>> callback) {
        mLocal.getVisitedPlaces(callback);
    }

    @Override
    public void savePlace(SearchParams searchParams, @NonNull OnDataLoadedCallback<Place> callback) {
        if (searchParams.isLocal()) mLocal.savePlace(searchParams, callback);
    }
}
