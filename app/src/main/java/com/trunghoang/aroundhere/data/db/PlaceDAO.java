package com.trunghoang.aroundhere.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.sqlite.SQLiteConstraintException;

import com.trunghoang.aroundhere.data.db.entity.PlaceEntity;

import java.util.List;

@Dao
public abstract class PlaceDAO {

    @Query("SELECT * FROM place_table WHERE place_id IN (:placeIds)")
    public abstract List<PlaceEntity> getPlaces(String... placeIds);

    @Query("SELECT * FROM place_table WHERE place_is_favored = 1")
    public abstract List<PlaceEntity> getFavoredPlaces();

    @Query("SELECT * FROM place_table WHERE place_is_checked = 1")
    public abstract List<PlaceEntity> getVisitedPlaces();

    @Query("SELECT * FROM place_table WHERE place_id = :placeId")
    public abstract PlaceEntity getPlace(String placeId);

    @Insert(onConflict = OnConflictStrategy.FAIL)
    public abstract void insert(PlaceEntity placeEntity);

    @Update(onConflict = OnConflictStrategy.FAIL)
    public abstract void update(PlaceEntity placeEntity);

    public void upsert(PlaceEntity placeEntity) {
        try {
            insert(placeEntity);
        } catch (SQLiteConstraintException e) {
            update(placeEntity);
        }
    }
}
