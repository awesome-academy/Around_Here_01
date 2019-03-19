package com.trunghoang.aroundhere.ui.favorite;

import com.trunghoang.aroundhere.data.model.OnDataLoadedCallback;
import com.trunghoang.aroundhere.data.model.Place;
import com.trunghoang.aroundhere.data.model.PlaceRepository;

import java.util.List;

public class FavoritePagePresenter implements FavoritePageContract.Presenter {
    private PlaceRepository mRepository;
    private FavoritePageContract.View mView;
    private OnDataLoadedCallback<List<Place>> mOnDataLoadedCallback;

    public FavoritePagePresenter(PlaceRepository repository, FavoritePageContract.View view) {
        mRepository = repository;
        mView = view;
        mView.setPresenter(this);
        mOnDataLoadedCallback = new OnDataLoadedCallback<List<Place>>() {
            @Override
            public void onDataLoaded(List<Place> data) {
                mView.showPlaces(data);
            }

            @Override
            public void onDataNotAvailable(Exception exception) {
                if (exception != null) mView.showLoadingError(exception);
            }
        };
    }

    @Override
    public void loadFavoredPlaces() {
        mRepository.getFavoredPlaces(mOnDataLoadedCallback);
    }

    @Override
    public void loadVisitedPlaces() {
        mRepository.getVisitedPlaces(mOnDataLoadedCallback);
    }
}
