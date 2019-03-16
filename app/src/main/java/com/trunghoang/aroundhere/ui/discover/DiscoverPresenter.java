package com.trunghoang.aroundhere.ui.discover;

import android.support.annotation.NonNull;

import com.trunghoang.aroundhere.data.model.Place;
import com.trunghoang.aroundhere.data.model.PlaceRepository;
import com.trunghoang.aroundhere.data.model.OnDataLoadedCallback;
import com.trunghoang.aroundhere.data.model.SearchParams;

import java.util.List;

public class DiscoverPresenter implements DiscoverContract.Presenter {
    private final PlaceRepository mPlaceRepository;
    private final DiscoverContract.View mView;

    public DiscoverPresenter(@NonNull PlaceRepository placeRepository,
                             @NonNull DiscoverContract.View view) {
        mPlaceRepository = placeRepository;
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        if (mView.isLocationPermissionGranted()) {
            mView.detectLocation();
        } else {
            mView.requestLocationPermission();
        }
    }

    @Override
    public void loadPlaces(SearchParams searchParams) {
        mView.showLoadingIndicator(true);
        mPlaceRepository.getPlaces(searchParams, new OnDataLoadedCallback<List<Place>>() {
            @Override
            public void onDataLoaded(List<Place> places) {
                if (!mView.isActive()) return;
                processPlaces(places);
            }

            @Override
            public void onDataNotAvailable(Exception exception) {
                mView.showLoadingPlacesError(exception);
            }
        });
    }

    private void processPlaces(List<Place> places) {
        mView.showPlaces(places);
    }
}
