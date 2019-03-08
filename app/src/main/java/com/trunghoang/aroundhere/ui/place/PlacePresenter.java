package com.trunghoang.aroundhere.ui.place;

import com.trunghoang.aroundhere.data.model.OnDataLoadedCallback;
import com.trunghoang.aroundhere.data.model.Place;
import com.trunghoang.aroundhere.data.model.PlaceRepository;
import com.trunghoang.aroundhere.data.model.Review;

import java.util.List;

public class PlacePresenter implements PlaceContract.Presenter {
    private PlaceRepository mPlaceRepository;
    private PlaceContract.View mView;
    private Place mPlace;

    public PlacePresenter(Place place, PlaceRepository placeRepository,
                          PlaceContract.View view) {
        mPlace = place;
        mPlaceRepository = placeRepository;
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        if (mPlace == null) return;
        mPlaceRepository.getPlace(mPlace.getDetailUrl(), new OnDataLoadedCallback<Place>() {
            @Override
            public void onDataLoaded(Place data) {
                getReviews(data.getResId());
                processPlace(data);
            }

            @Override
            public void onDataNotAvailable(Exception exception) {
            }
        });
    }

    private void processPlace(Place place) {
        mView.showPlace(place);
    }

    private void getReviews(String resId) {
        mPlaceRepository.getReviews(resId, new OnDataLoadedCallback<List<Review>>() {
            @Override
            public void onDataLoaded(List<Review> data) {
                processReviews(data);
            }

            @Override
            public void onDataNotAvailable(Exception exception) {
                mView.showLoadingError(exception);
            }
        });
    }

    private void processReviews(List<Review> reviews) {
        mView.showReviews(reviews);
    }
}
