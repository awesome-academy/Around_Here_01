package com.trunghoang.aroundhere.data.model;

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
}
