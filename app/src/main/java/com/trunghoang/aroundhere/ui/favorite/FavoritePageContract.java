package com.trunghoang.aroundhere.ui.favorite;

import com.trunghoang.aroundhere.data.model.Place;
import com.trunghoang.aroundhere.ui.BaseView;

import java.util.List;

public interface FavoritePageContract {
    interface View extends BaseView<Presenter> {
        void showPlaces(List<Place> places);
        void showLoadingError(Exception e);
    }

    interface Presenter {
        void loadFavoredPlaces();
        void loadVisitedPlaces();
    }
}
