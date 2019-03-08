package com.trunghoang.aroundhere.ui.discover;

import android.support.annotation.NonNull;

import com.trunghoang.aroundhere.data.model.Place;
import com.trunghoang.aroundhere.data.model.PlaceDataSource;
import com.trunghoang.aroundhere.data.model.PlaceRepository;

import java.util.List;

public class DiscoverPresenter implements DiscoverContract.Presenter {
    private final PlaceRepository mPlacesRepository;
    private final DiscoverContract.View mView;

    public DiscoverPresenter(@NonNull PlaceRepository placesRepository,
                             @NonNull DiscoverContract.View view) {
        mPlacesRepository = placesRepository;
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        if (mView.isLocationPermissionGranted()) {
            loadPlaces();
        } else {
            mView.requestLocationPermission();
        }
    }

    @Override
    public void loadPlaces() {
        mPlacesRepository.getPlaces(new PlaceDataSource.LoadPlacesCallback() {
            @Override
            public void OnPlacesLoaded(List<Place> places) {
                if (!mView.isActive()) return;
                processPlaces(places);
            }

            @Override
            public void OnDataNotAvailable(Exception exception) {
                mView.showLoadingPlacesError(exception);
            }
        });
    }

    private void processPlaces(List<Place> places) {
        mView.showPlaces(places);
    }
}
