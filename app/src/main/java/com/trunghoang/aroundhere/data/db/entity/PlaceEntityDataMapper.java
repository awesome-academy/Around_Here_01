package com.trunghoang.aroundhere.data.db.entity;

import com.trunghoang.aroundhere.data.model.Place;

import java.util.ArrayList;
import java.util.List;

public class PlaceEntityDataMapper {
    public static Place transform(PlaceEntity entity) {
        if (entity != null) {
            return new Place.Builder()
                    .setResId(entity.getResId())
                    .setPhoto(entity.getPhoto())
                    .setTitle(entity.getTitle())
                    .setAddress(entity.getAddress())
                    .setDetailUrl(entity.getDetailUrl())
                    .setFavored(entity.isFavored())
                    .setCheckedIn(entity.isCheckedIn())
                    .setCheckedInTime(entity.getCheckedInTime())
                    .build();
        }
        return null;
    }

    public static List<Place> transform(List<PlaceEntity> entities) {
        List<Place> places = new ArrayList<>();
        for(PlaceEntity entity: entities) {
            places.add(transform(entity));
        }
        return places;
    }
}
