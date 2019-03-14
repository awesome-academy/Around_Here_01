package com.trunghoang.aroundhere.data.remote;

import android.location.Location;
import android.support.annotation.NonNull;

import com.trunghoang.aroundhere.data.model.GlobalData;
import com.trunghoang.aroundhere.data.model.OnDataLoadedCallback;
import com.trunghoang.aroundhere.data.model.Place;
import com.trunghoang.aroundhere.data.model.PlaceDataSource;

import java.util.List;

public class PlaceRemoteDataSource implements PlaceDataSource {
    private static PlaceRemoteDataSource sInstance;

    private PlaceRemoteDataSource() {
    }

    public static PlaceRemoteDataSource getInstance() {
        if (sInstance == null) {
            sInstance = new PlaceRemoteDataSource();
        }
        return sInstance;
    }

    @Override
    public void getPlaces(Location location, @NonNull OnDataLoadedCallback<List<Place>> callback) {
        DownloadTask<List<Place>> downloadTask = new DownloadTask<>(new PlacesDownloadHandler(),
                callback);
        downloadTask.execute(GlobalData.getInstance().buildPlacesApiUrl(location));
    }

    /*@Override
    public void getPlace(@NonNull OnDataLoadedCallback<Place> callback) {
        DownloadTask<Place> downloadTask = new DownloadTask<>(new PlaceDownloadHandler(), callback);
        downloadTask.execute();
    }*/
}
