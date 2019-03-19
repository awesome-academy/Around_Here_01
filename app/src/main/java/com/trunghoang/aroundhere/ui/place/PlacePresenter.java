package com.trunghoang.aroundhere.ui.place;

import com.trunghoang.aroundhere.data.model.OnDataLoadedCallback;
import com.trunghoang.aroundhere.data.model.Place;
import com.trunghoang.aroundhere.data.model.PlaceRepository;
import com.trunghoang.aroundhere.data.model.Review;
import com.trunghoang.aroundhere.data.model.SearchParams;
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
        getPlaceFromLocal();
        getPlaceFromRemote();
    }

    @Override
    public void updateFavoredStatus() {
        mPlace.setFavored(!mPlace.isFavored());
        SearchParams searchParams = new SearchParams();
        searchParams.setLocal(true);
        searchParams.setPlace(mPlace);
        mPlaceRepository.savePlace(searchParams, new OnDataLoadedCallback<Place>() {
            @Override
            public void onDataLoaded(Place data) {
                mView.showUpdateInform(mPlace.isFavored() ?
                        PlaceUpdateType.FAVORITES_ADDED :
                        PlaceUpdateType.FAVORITES_REMOVED);
                updatePlace(data);
            }

            @Override
            public void onDataNotAvailable(Exception exception) {
            }
        });
    }

    @Override
    public void updateCheckInStatus() {
        mPlace.setCheckedIn(!mPlace.isCheckedIn());
        if (mPlace.isCheckedIn()) mPlace.setCheckedInTime(new Date().getTime());
        SearchParams searchParams = new SearchParams();
        searchParams.setLocal(true);
        searchParams.setPlace(mPlace);
        mPlaceRepository.savePlace(searchParams, new OnDataLoadedCallback<Place>() {
            @Override
            public void onDataLoaded(Place data) {
                mView.showUpdateInform(mPlace.isCheckedIn() ?
                        PlaceUpdateType.VISITED_ADDED :
                        PlaceUpdateType.VISITED_REMOVED);
                updatePlace(data);
            }

            @Override
            public void onDataNotAvailable(Exception exception) {
            }
        });
    }

    private void getPlaceFromLocal() {
        SearchParams searchParams = new SearchParams();
        searchParams.setLocal(true);
        searchParams.setResId(mPlace.getResId());
        mPlaceRepository.getPlace(searchParams, new OnDataLoadedCallback<Place>() {
            @Override
            public void onDataLoaded(Place data) {
                showPlace(data);
            }

            @Override
            public void onDataNotAvailable(Exception exception) {
                if (exception != null) mView.showUpdateError(exception);
            }
        });
    }

    private void getPlaceFromRemote() {
        getInfoFromRemote();
        getReviewsFromRemote();
    }

    private void getReviewsFromRemote() {
        mView.showLoadingIndicator(true);
        SearchParams searchParams = new SearchParams();
        searchParams.setRemote(true);
        searchParams.setResId(mPlace.getResId());
        mPlaceRepository.getReviews(searchParams, new OnDataLoadedCallback<List<Review>>() {
            @Override
            public void onDataLoaded(List<Review> data) {
                showReviews(data);
            }

            @Override
            public void onDataNotAvailable(Exception exception) {
                if (exception != null) mView.showLoadingError(exception);
            }
        });
    }

    private void getInfoFromRemote() {
        SearchParams searchParams = new SearchParams();
        searchParams.setRemote(true);
        searchParams.setPlaceUrl(mPlace.getDetailUrl());
        mPlaceRepository.getPlace(searchParams, new OnDataLoadedCallback<Place>() {
            @Override
            public void onDataLoaded(Place data) {
                showPlace(data);
            }

            @Override
            public void onDataNotAvailable(Exception exception) {
            }
        });
    }

    private void showPlace(Place place) {
        mView.showPlace(Place.mergePlace(mPlace, place));
    }

    private void showReviews(List<Review> reviews) {
        mView.showReviews(reviews);
    }

    private void updatePlace(Place place) {
        mView.updatePlace(Place.mergePlace(mPlace, place));
    }
}
