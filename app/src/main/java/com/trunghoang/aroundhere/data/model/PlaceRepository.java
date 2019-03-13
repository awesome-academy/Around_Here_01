package com.trunghoang.aroundhere.data.model;

import android.support.annotation.NonNull;

public class PlaceRepository implements PlaceDataSource {
    private static PlaceRepository sInstance;
    private PlaceDataSource mRemote;

    private PlaceRepository(PlaceDataSource remote) {
        mRemote = remote;
    }

    public static PlaceRepository getInstance(PlaceDataSource remote) {
        if (sInstance == null) {
            sInstance = new PlaceRepository(remote);
        }
        return sInstance;
    }

    @Override
    public void getPlaces(@NonNull LoadPlacesCallback callback) {
        getPlacesFromRemote(callback);
    }

    private void getPlacesFromRemote(@NonNull final LoadPlacesCallback callback) {
        mRemote.getPlaces(callback);
    }
}
