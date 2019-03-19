package com.trunghoang.aroundhere.data.remote;

import android.support.annotation.NonNull;

import com.trunghoang.aroundhere.data.model.GlobalData;
import com.trunghoang.aroundhere.data.model.OnDataLoadedCallback;
import com.trunghoang.aroundhere.data.model.Place;
import com.trunghoang.aroundhere.data.model.PlaceDataSource;
import com.trunghoang.aroundhere.data.model.Review;
import com.trunghoang.aroundhere.data.model.SearchParams;

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
    public void getPlaces(SearchParams searchParams, @NonNull OnDataLoadedCallback<List<Place>> callback) {
        DownloadTask<List<Place>> downloadTask = new DownloadTask<>(new PlacesDownloadHandler(), callback);
        downloadTask.execute(GlobalData.getInstance().buildPlacesApiUrl(searchParams));
    }

    @Override
    public void getPlace(SearchParams searchParams, @NonNull OnDataLoadedCallback<Place> callback) {
        DownloadTask<Place> downloadTask = new DownloadTask<>(new PlaceDownloadHandler(), callback);
        downloadTask.execute(GlobalData.getInstance().getPlaceApiUrl(searchParams));
    }

    @Override
    public void getReviews(SearchParams searchParams, @NonNull OnDataLoadedCallback<List<Review>> callback) {
        DownloadTask<List<Review>> downloadTask = new DownloadTask<>(new ReviewsDownloadHandler(),
                callback);
        downloadTask.execute(GlobalData.getInstance().getReviewsApiUrl(searchParams));
    }

    @Override
    public void getFavoredPlaces(@NonNull OnDataLoadedCallback<List<Place>> callback) {
    }

    @Override
    public void getVisitedPlaces(@NonNull OnDataLoadedCallback<List<Place>> callback) {
    }
}
