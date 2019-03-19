package com.trunghoang.aroundhere.data.model;

import android.support.annotation.NonNull;

import com.trunghoang.aroundhere.data.db.DaoHandler;
import com.trunghoang.aroundhere.data.db.DaoTask;
import com.trunghoang.aroundhere.data.db.PlaceDAO;
import com.trunghoang.aroundhere.data.db.entity.PlaceEntity;
import com.trunghoang.aroundhere.data.db.entity.PlaceEntityDataMapper;

import java.util.List;

public class PlaceRepository implements PlaceDataSource {
    private static PlaceRepository sInstance;
    private PlaceDataSource mRemote;
    private PlaceDataSource mLocal;
    private PlaceDAO mPlaceDAO;

    private PlaceRepository(PlaceDAO placeDAO, PlaceDataSource remote, PlaceDataSource local) {
        mRemote = remote;
        mLocal = local;
        mPlaceDAO = placeDAO;
    }

    public static PlaceRepository getInstance(PlaceDAO placeDAO,
                                              PlaceDataSource remote,
                                              PlaceDataSource local) {
        if (sInstance == null) {
            sInstance = new PlaceRepository(placeDAO, remote, local);
        }
        return sInstance;
    }

    @Override
    public void getPlaces(SearchParams searchParams, @NonNull OnDataLoadedCallback<List<Place>> callback) {
        if (searchParams.isRemote()) mRemote.getPlaces(searchParams, callback);
    }

    @Override
    public void getPlace(SearchParams searchParams, @NonNull OnDataLoadedCallback<Place> callback) {
        if (searchParams.isRemote()) mRemote.getPlace(searchParams, callback);
    }

    @Override
    public void getReviews(SearchParams searchParams, @NonNull OnDataLoadedCallback<List<Review>> callback) {
        if (searchParams.isRemote()) mRemote.getReviews(searchParams, callback);
    }

    @Override
    public void getFavoredPlaces(@NonNull OnDataLoadedCallback<List<Place>> callback) {
        mLocal.getFavoredPlaces(callback);
    }

    @Override
    public void getVisitedPlaces(@NonNull OnDataLoadedCallback<List<Place>> callback) {
        mLocal.getVisitedPlaces(callback);
    }

    public void updatePlaceFromLocal(String resId,
                                     @NonNull OnDataLoadedCallback<Place> callback) {
        DaoTask<String, Place> getPlaceTask = new DaoTask<>(new DaoHandler<String, Place>() {
            @Override
            public Place execute(String[] placeIds, PlaceDAO placeDao) {
                if (placeIds == null || placeIds.length == 0) return null;
                PlaceEntity entity = mPlaceDAO.getPlace(placeIds[0]);
                return PlaceEntityDataMapper.transform(entity);
            }
        }, mPlaceDAO, callback);
        getPlaceTask.execute(resId);
    }

    public void updatePlaceToLocal(Place place, @NonNull OnDataLoadedCallback<Place> callback) {
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
                mPlaceDAO.upsert(entity);
                return sourcePlace;
            }
        }, mPlaceDAO, callback);
        updatePlaceTask.execute(place);
    }
}
