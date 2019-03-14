package com.trunghoang.aroundhere.data.model;

import android.location.Location;
import android.support.annotation.NonNull;

import java.util.List;

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
    public void getPlaces(Location location, @NonNull OnDataLoadedCallback<List<Place>> callback) {
        getPlacesFromRemote(location, callback);
    }

    /*@Override
    public void getPlace(@NonNull OnDataLoadedCallback<Place> callback) {
        mRemote.getPlace(callback);
    }*/

    private void getPlacesFromRemote(Location location,
                                     @NonNull OnDataLoadedCallback<List<Place>> callback) {
        mRemote.getPlaces(location, callback);
    }
}
