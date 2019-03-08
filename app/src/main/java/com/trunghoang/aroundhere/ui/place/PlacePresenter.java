package com.trunghoang.aroundhere.ui.place;

import com.trunghoang.aroundhere.data.model.PlaceRepository;

public class PlacePresenter implements PlaceContract.Presenter {
    private PlaceRepository mPlaceRepository;
    private PlaceContract.View mView;

    public PlacePresenter(PlaceRepository placeRepository, PlaceContract.View view) {
        mPlaceRepository = placeRepository;
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
    }
}
