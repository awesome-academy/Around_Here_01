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
        if (searchParams.getResId() != null) {
            DaoTask<String, Place> getPlaceTask = new DaoTask<>(new DaoHandler<String, Place>() {
                @Override
                public Place execute(String[] placeIds, PlaceDAO placeDao) {
                    if (placeIds == null || placeIds.length == 0) return null;
                    PlaceEntity entity = mPlaceDAO.getPlace(placeIds[0]);
                    return PlaceEntityDataMapper.transform(entity);
                }
            }, mPlaceDAO, callback);
            getPlaceTask.execute(searchParams.getResId());
        }
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

    public void savePlace(SearchParams searchParams, @NonNull OnDataLoadedCallback<Place> callback) {
        if (searchParams.getPlace() != null) {
            DaoTask<Place, Place> updatePlaceTask = new DaoTask<>(new DaoHandler<Place, Place>() {
                @Override
                public Place execute(Place[] places, PlaceDAO placeDao) {
                    if (places == null || places.length == 0) return null;
                    PlaceEntity entity = new PlaceEntity();
                    Place sourcePlace = places[0];
                    entity.setResId(sourcePlace.getResId());
                    entity.setIsFavored(sourcePlace.isFavored());
                    entity.setIsCheckedIn(sourcePlace.isCheckedIn());
                    entity.setCheckedInTime(sourcePlace.getCheckedInTime());
                    entity.setPhoto(sourcePlace.getPhoto());
                    entity.setTitle(sourcePlace.getTitle());
                    entity.setAddress(sourcePlace.getAddress());
                    entity.setDetailUrl(sourcePlace.getDetailUrl());
                    mPlaceDAO.upsert(entity);
                    return places[0];
                }
            }, mPlaceDAO, callback);
            updatePlaceTask.execute(searchParams.getPlace());
        }
    }
}
