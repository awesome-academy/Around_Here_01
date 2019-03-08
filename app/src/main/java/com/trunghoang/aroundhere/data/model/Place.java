package com.trunghoang.aroundhere.data.model;

import org.json.JSONObject;

public class Place {

    private double mDistance;
    private boolean mIsOpen;
    private String mPhoto;
    private String mTitle;
    private String mAddress;

    public Place(double distance, boolean isOpen, String photo, String title, String address) {
        mDistance = distance;
        mIsOpen = isOpen;
        mPhoto = photo;
        mTitle = title;
        mAddress = address;
    }

    public Place(JSONObject jsonObject) {
        mDistance = jsonObject.optDouble(ApiField.API_FIELD_DISTANCE);
        mIsOpen = jsonObject.optBoolean(ApiField.API_FIELD_OPENING);
        mPhoto = jsonObject.optString(ApiField.API_FIELD_PHOTO);
        mTitle = jsonObject.optString(ApiField.API_FIELD_NAME);
        mAddress = jsonObject.optString(ApiField.API_FIELD_ADDRESS);
    }

    public double getDistance() {
        return mDistance;
    }

    public void setDistance(long distance) {
        mDistance = distance;
    }

    public boolean isOpen() {
        return mIsOpen;
    }

    public void setOpen(boolean open) {
        mIsOpen = open;
    }

    public String getPhoto() {
        return mPhoto;
    }

    public void setPhoto(String photo) {
        mPhoto = photo;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public static class Builder {
        private double mDistance;
        private boolean mIsOpen;
        private String mPhoto;
        private String mTitle;
        private String mAddress;

        public Builder() {
        }

        public Builder setDistance(double distance) {
            mDistance = distance;
            return this;
        }

        public Builder setOpen(boolean open) {
            mIsOpen = open;
            return this;
        }

        public Builder setPhoto(String photo) {
            mPhoto = photo;
            return this;
        }

        public Builder setTitle(String title) {
            mTitle = title;
            return this;
        }

        public Builder setAddress(String address) {
            mAddress = address;
            return this;
        }

        public Place build() {
            return new Place(mDistance, mIsOpen, mPhoto, mTitle, mAddress);
        }
    }

    public interface ApiField {
        String API_FIELD_DISTANCE = "Distance";
        String API_FIELD_OPENING = "IsOpening";
        String API_FIELD_PHOTO = "MobilePicturePath";
        String API_FIELD_NAME = "Name";
        String API_FIELD_ADDRESS = "Address";
        String API_FIELD_ITEMS = "searchItems";
    }
}
