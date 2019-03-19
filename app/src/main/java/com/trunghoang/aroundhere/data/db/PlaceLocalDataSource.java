package com.trunghoang.aroundhere.data.db;

import android.support.annotation.NonNull;

import com.trunghoang.aroundhere.data.db.entity.PlaceEntity;
import com.trunghoang.aroundhere.data.db.entity.PlaceEntityDataMapper;
import com.trunghoang.aroundhere.data.model.OnDataLoadedCallback;
import com.trunghoang.aroundhere.data.model.Place;
import com.trunghoang.aroundhere.data.model.PlaceDataSource;
import com.trunghoang.aroundhere.data.model.Review;
import com.trunghoang.aroundhere.data.model.SearchParams;

import java.util.List;

public class PlaceLocalDataSource implements PlaceDataSource {
    private static PlaceLocalDataSource sInstance;
    private PlaceDAO mPlaceDAO;

    private PlaceLocalDataSource(PlaceDAO placeDAO) {
        mPlaceDAO = placeDAO;
    }

    public static PlaceLocalDataSource getInstance(PlaceDAO placeDAO) {
        if (sInstance == null) {
            sInstance = new PlaceLocalDataSource(placeDAO);
        }
        return sInstance;
    }

    @Override
    public void getPlaces(SearchParams searchParams,
                          @NonNull final OnDataLoadedCallback<List<Place>> callback) {
    }

    @Override
    public void getPlace(SearchParams searchParams, @NonNull OnDataLoadedCallback<Place> callback) {
    }

    @Override
    public void getReviews(SearchParams searchParams, @NonNull OnDataLoadedCallback<List<Review>> callback) {
    }

    @Override
    public void getFavoredPlaces(@NonNull OnDataLoadedCallback<List<Place>> callback) {
        DaoTask<Void, List<Place>> daoTask = new DaoTask<>(new DaoHandler<Void, List<Place>>() {
            @Override
            public List<Place> execute(Void[] args, PlaceDAO placeDao) {
                List<PlaceEntity> entities = placeDao.getFavoredPlaces();
                return PlaceEntityDataMapper.transform(entities);
            }
        }, mPlaceDAO, callback);
        daoTask.execute();
    }

    @Override
    public void getVisitedPlaces(@NonNull OnDataLoadedCallback<List<Place>> callback) {
        DaoTask<Void, List<Place>> daoTask = new DaoTask<>(new DaoHandler<Void, List<Place>>() {
            @Override
            public List<Place> execute(Void[] args, PlaceDAO placeDao) {
                List<PlaceEntity> entities = placeDao.getVisitedPlaces();
                return PlaceEntityDataMapper.transform(entities);
            }
        }, mPlaceDAO, callback);
        daoTask.execute();
    }
}
