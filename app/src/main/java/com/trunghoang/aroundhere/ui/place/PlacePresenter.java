package com.trunghoang.aroundhere.ui.place;

import com.trunghoang.aroundhere.data.model.OnDataLoadedCallback;
import com.trunghoang.aroundhere.data.model.Place;
import com.trunghoang.aroundhere.data.model.PlaceRepository;
import com.trunghoang.aroundhere.data.model.Review;
import com.trunghoang.aroundhere.util.PlaceUpdateType;

import java.util.Date;
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
        updatePlaceFromLocal();
        updatePlaceFromRemote();
    }

    @Override
    public void updateFavoredStatus() {
        mPlace.setFavored(!mPlace.isFavored());
        mPlaceRepository.updatePlaceToLocal(mPlace, new OnDataLoadedCallback<Place>() {
            @Override
            public void onDataLoaded(Place data) {
                mView.showUpdateInform(mPlace.isFavored() ?
                        PlaceUpdateType.FAVORITES_ADDED :
                        PlaceUpdateType.FAVORITES_REMOVED);
                updatePlace(data);
            }

            @Override
            public void onDataNotAvailable(Exception exception) {
                if (exception != null) mView.showUpdateError(exception);
            }
        });
    }

    @Override
    public void updateCheckInStatus() {
        mPlace.setCheckedIn(!mPlace.isCheckedIn());
        if (mPlace.isCheckedIn()) mPlace.setCheckedInTime(new Date().getTime());
        mPlaceRepository.updatePlaceToLocal(mPlace, new OnDataLoadedCallback<Place>() {
            @Override
            public void onDataLoaded(Place data) {
                mView.showUpdateInform(mPlace.isCheckedIn() ?
                        PlaceUpdateType.VISITED_ADDED :
                        PlaceUpdateType.VISITED_REMOVED);
                updatePlace(data);
            }

            @Override
            public void onDataNotAvailable(Exception exception) {
                if (exception != null) mView.showUpdateError(exception);
            }
        });
    }

    private void updatePlaceFromLocal() {
        mPlaceRepository.updatePlaceFromLocal(mPlace.getResId(), new OnDataLoadedCallback<Place>() {
            @Override
            public void onDataLoaded(Place data) {
                updatePlace(data);
            }

            @Override
            public void onDataNotAvailable(Exception exception) {
                if (exception != null) mView.showUpdateError(exception);
            }
        });
    }

    private void updatePlaceFromRemote() {
        updateReviewsFromRemote();
        updateTimePriceFromRemote();
    }

    private void updateReviewsFromRemote() {
        mView.showLoadingIndicator(true);
        mPlaceRepository.getReviews(mPlace.getResId(), new OnDataLoadedCallback<List<Review>>() {
            @Override
            public void onDataLoaded(List<Review> data) {
                processReviews(data);
            }

            @Override
            public void onDataNotAvailable(Exception exception) {
                if (exception != null) mView.showLoadingError(exception);
            }
        });
    }

    private void updateTimePriceFromRemote() {
        mPlaceRepository.getPlace(mPlace.getDetailUrl(), new OnDataLoadedCallback<Place>() {
            @Override
            public void onDataLoaded(Place data) {
                processPlace(data);
            }

            @Override
            public void onDataNotAvailable(Exception exception) {
                if (exception != null) mView.showUpdateError(exception);
            }
        });
    }

    private void processPlace(Place place) {
        mView.showPlace(place);
    }

    private void processReviews(List<Review> reviews) {
        mView.showReviews(reviews);
    }

    private void updatePlace(Place place) {
        mPlace.setFavored(place.isFavored());
        mPlace.setCheckedIn(place.isCheckedIn());
        mPlace.setCheckedInTime(place.getCheckedInTime());
        mView.updatePlace(mPlace);
    }
}
