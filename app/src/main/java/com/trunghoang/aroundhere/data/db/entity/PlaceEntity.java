package com.trunghoang.aroundhere.data.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = PlaceEntity.PlaceKey.TABLE)
public class PlaceEntity {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = PlaceKey.ID)
    private String mResId;
    @ColumnInfo(name = PlaceKey.PHOTO)
    private String mPhoto;
    @ColumnInfo(name = PlaceKey.TITLE)
    private String mTitle;
    @ColumnInfo(name = PlaceKey.ADDRESS)
    private String mAddress;
    @ColumnInfo(name = PlaceKey.DETAIL_URL)
    private String mDetailUrl;
    @ColumnInfo(name = PlaceKey.IS_FAVORED)
    private boolean isFavored;
    @ColumnInfo(name = PlaceKey.IS_CHECKED)
    private boolean isCheckedIn;

    public PlaceEntity() {
    }

    public String getResId() {
        return mResId;
    }

    public void setResId(String resId) {
        this.mResId = resId;
    }

    public String getPhoto() {
        return mPhoto;
    }

    public void setPhoto(String photo) {
        this.mPhoto = photo;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        this.mAddress = address;
    }

    public String getDetailUrl() {
        return mDetailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.mDetailUrl = detailUrl;
    }

    public boolean isFavored() {
        return isFavored;
    }

    public void setFavored(boolean favored) {
        this.isFavored = favored;
    }

    public boolean isCheckedIn() {
        return isCheckedIn;
    }

    public void setCheckedIn(boolean checkedIn) {
        isCheckedIn = checkedIn;
    }

    interface PlaceKey {
        String TABLE = "place_table";
        String ID = "place_id";
        String PHOTO = "place_photo";
        String TITLE = "place_title";
        String ADDRESS = "place_address";
        String DETAIL_URL = "place_detail_url";
        String IS_FAVORED = "place_is_favored";
        String IS_CHECKED = "place_is_checked";
    }
}
