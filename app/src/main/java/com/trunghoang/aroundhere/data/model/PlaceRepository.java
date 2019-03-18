package com.trunghoang.aroundhere.data.model;

import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;

import com.trunghoang.aroundhere.data.db.AppDatabase;
import com.trunghoang.aroundhere.data.db.DaoHandler;
import com.trunghoang.aroundhere.data.db.DaoTask;
import com.trunghoang.aroundhere.data.db.PlaceDAO;
import com.trunghoang.aroundhere.data.db.entity.PlaceEntity;
import com.trunghoang.aroundhere.data.db.entity.PlaceEntityDataMapper;

import java.util.List;

public class PlaceRepository implements PlaceDataSource {
    private static PlaceRepository sInstance;
    private PlaceDataSource mRemote;
    private PlaceDAO mPlaceDAO;

    private PlaceRepository(Context appContext, PlaceDataSource remote) {
        mRemote = remote;
        AppDatabase db = AppDatabase.getInstance(appContext);
        mPlaceDAO = db.placeDAO();
    }

    public static PlaceRepository getInstance(Context appContext, PlaceDataSource remote) {
        if (sInstance == null) {
            sInstance = new PlaceRepository(appContext, remote);
        }
        return sInstance;
    }

    @Override
    public void getPlaces(Location location, @NonNull OnDataLoadedCallback<List<Place>> callback) {
        if (location != null) getPlacesFromRemote(location, callback);
    }

    @Override
    public void getPlace(String placeUrl, @NonNull OnDataLoadedCallback<Place> callback) {
        mRemote.getPlace(placeUrl, callback);
    }

    @Override
    public void getReviews(String resId, @NonNull OnDataLoadedCallback<List<Review>> callback) {
        mRemote.getReviews(resId, callback);
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
                entity.setResId(places[0].getResId());
                entity.setFavored(places[0].isFavored());
                mPlaceDAO.upsert(entity);
                return places[0];
            }
        }, mPlaceDAO, callback);
        updatePlaceTask.execute(place);
    }

    private void getPlacesFromRemote(Location location,
            @NonNull OnDataLoadedCallback<List<Place>> callback) {
        mRemote.getPlaces(location, callback);
    }

}
