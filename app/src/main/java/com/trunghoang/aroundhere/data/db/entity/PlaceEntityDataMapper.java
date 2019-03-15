package com.trunghoang.aroundhere.data.db.entity;

import com.trunghoang.aroundhere.data.model.Place;

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
                    .build();
        }
        return null;
    }
}
