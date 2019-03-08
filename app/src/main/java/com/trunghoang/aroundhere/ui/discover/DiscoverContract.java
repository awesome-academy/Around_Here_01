package com.trunghoang.aroundhere.ui.discover;

import android.location.Location;

import com.trunghoang.aroundhere.data.model.Place;
import com.trunghoang.aroundhere.ui.BasePresenter;
import com.trunghoang.aroundhere.ui.BaseView;

import java.util.List;

public interface DiscoverContract {
    interface View extends BaseView<Presenter> {
        void showPlaces(List<Place> places);

        void showLoadingPlacesError(Exception e);

        void showPlaceActivity();

        boolean isActive();

        boolean isLocationPermissionGranted();

        void requestLocationPermission();

        void detectLocation();
    }

    interface Presenter extends BasePresenter {
        void loadPlaces(Location location);

        void openPlaceActivity();
    }
}
