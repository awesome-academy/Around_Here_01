package com.trunghoang.aroundhere.ui.discover;

import com.trunghoang.aroundhere.data.model.Place;
import com.trunghoang.aroundhere.ui.BasePresenter;
import com.trunghoang.aroundhere.ui.BaseView;

import java.util.List;

public interface DiscoverContract {
    interface View extends BaseView<Presenter> {
        void showPlaces(List<Place> places);

        void showLoadingPlacesError(Exception e);

        boolean isActive();

        boolean isLocationPermissionGranted();

        void requestLocationPermission();
    }

    interface Presenter extends BasePresenter {
        void loadPlaces();
    }
}
