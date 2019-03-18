package com.trunghoang.aroundhere.ui.place;

import com.trunghoang.aroundhere.data.model.Place;
import com.trunghoang.aroundhere.data.model.Review;
import com.trunghoang.aroundhere.ui.BasePresenter;
import com.trunghoang.aroundhere.ui.BaseView;
import com.trunghoang.aroundhere.util.PlaceUpdateType;

import java.util.List;

public interface PlaceContract {

    interface View extends BaseView<Presenter> {
        void showPlace(Place data);

        void showReviews(List<Review> reviews);

        void showLoadingError(Exception e);

        void showLoadingIndicator(boolean isLoading);

        void updatePlace(Place data);

        void showUpdateInform(@PlaceUpdateType.PlaceUpdateDef int update);

        void showUpdateError(Exception e);
    }

    interface Presenter extends BasePresenter {
        void updateFavoredStatus();

        void updateCheckInStatus();
    }
}
