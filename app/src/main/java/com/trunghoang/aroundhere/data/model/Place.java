package com.trunghoang.aroundhere.data.model;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

public class Place implements Parcelable {

    public static final Parcelable.Creator<Place> CREATOR = new Parcelable.Creator<Place>() {

        @Override
        public Place createFromParcel(Parcel source) {
            return new Place(source);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[0];
        }
    };
    private static final long FULL_DAY = 1000 * 60 * 60 * 24;
    private double mDistance;
    private boolean mIsOpen;
    private String mPhoto;
    private String mTitle;
    private String mAddress;
    private long mPriceMin;
    private long mPriceMax;
    private String mStartTime;
    private String mEndTime;
    private String mResId;
    private String mDetailUrl;
    private boolean mIsFavored;
    private boolean mIsCheckedIn;
    private long mCheckedInTime;
    private long mTimeOpen;
    private long mTimeClose;
    private double mLat;
    private double mLon;

    public static Place mergePlace(Place source, Place dest) {
        source.mPhoto = dest.mPhoto == null ? source.mPhoto : dest.mPhoto;
        source.mTitle = dest.mTitle == null ? source.mTitle : dest.mTitle;
        source.mAddress = dest.mAddress == null ? source.mAddress : dest.mAddress;
        source.mPriceMin = dest.mPriceMin == 0L ? source.mPriceMin : dest.mPriceMin;
        source.mPriceMax = dest.mPriceMax == 0L ? source.mPriceMax : dest.mPriceMax;
        source.mStartTime = dest.mStartTime == null ? source.mStartTime : dest.mStartTime;
        source.mEndTime = dest.mEndTime == null ? source.mEndTime : dest.mEndTime;
        source.mResId = dest.mResId == null ? source.mResId : dest.mResId;
        source.mDetailUrl = dest.mDetailUrl == null ? source.mDetailUrl : dest.mDetailUrl;
        source.mIsFavored = dest.mIsFavored ? dest.mIsFavored : source.mIsFavored;
        source.mIsCheckedIn = dest.mIsCheckedIn ? dest.mIsCheckedIn : source.mIsCheckedIn;
        source.mCheckedInTime = dest.mCheckedInTime == 0L ? source.mCheckedInTime :
                dest.mCheckedInTime;
        source.mTimeOpen = dest.mTimeOpen == 0L ? source.mTimeOpen : dest.mTimeOpen;
        source.mTimeClose = dest.mTimeClose == 0L ? source.mTimeClose : dest.mTimeClose;
        source.mLat = (Double.isNaN(dest.mLat) || dest.mLat == 0.0d) ? source.mLat : dest.mLat;
        source.mLon = (Double.isNaN(dest.mLon) || dest.mLon == 0.0d) ? source.mLon : dest.mLon;
        return source;
    }

    public Place() {
    }

    public Place(Parcel parcel) {
        mDistance = parcel.readDouble();
        mIsOpen = (parcel.readInt() == 1);
        mPhoto = parcel.readString();
        mTitle = parcel.readString();
        mAddress = parcel.readString();
        mPriceMin = parcel.readLong();
        mPriceMax = parcel.readLong();
        mStartTime = parcel.readString();
        mEndTime = parcel.readString();
        mResId = parcel.readString();
        mDetailUrl = parcel.readString();
        mIsFavored = (parcel.readInt() == 1);
        mIsCheckedIn = (parcel.readInt() == 1);
        mCheckedInTime = parcel.readLong();
        mTimeOpen = parcel.readLong();
        mTimeClose = parcel.readLong();
        mLat = parcel.readDouble();
        mLon = parcel.readDouble();
    }

    public Place(JSONObject jsonObject) {
        mDistance = jsonObject.optDouble(JSONKey.DISTANCE);
        mIsOpen = jsonObject.optBoolean(JSONKey.OPENING);
        mPhoto = jsonObject.optString(JSONKey.PHOTO);
        mTitle = jsonObject.optString(JSONKey.NAME);
        mAddress = jsonObject.optString(JSONKey.ADDRESS);
        mResId = jsonObject.optString(JSONKey.PLACE_ID);
        mDetailUrl = jsonObject.optString(JSONKey.DETAIL_URL);
        if (TextUtils.isEmpty(mPhoto)) mPhoto = jsonObject.optString(JSONKey.PHOTO_URL);
        if (TextUtils.isEmpty(mResId)) mResId = jsonObject.optString(JSONKey.RES_ID);
        if (TextUtils.isEmpty(mDetailUrl)) mDetailUrl = jsonObject.optString(JSONKey.RES_URL);
        JSONArray openingTime = jsonObject.optJSONArray(JSONKey.OPENING_TIME);
        if (openingTime != null && openingTime.length() != 0) {
            JSONObject time0 = openingTime.optJSONObject(0);
            JSONObject timeOpen = time0.optJSONObject(JSONKey.TIME_OPEN);
            JSONObject timeClose = time0.optJSONObject(JSONKey.TIME_CLOSE);
            mTimeOpen = timeOpen.optLong(JSONKey.TOTAL_MILLS);
            mTimeClose = timeClose.optLong(JSONKey.TOTAL_MILLS);
            if (mTimeClose == 0) mTimeClose = FULL_DAY;
        }
        mPriceMin = jsonObject.optLong(JSONKey.PRICE_MIN);
        mPriceMax = jsonObject.optLong(JSONKey.PRICE_MAX);
        mLat = jsonObject.optDouble(JSONKey.LAT);
        mLon = jsonObject.optDouble(JSONKey.LON);
    }

    public double getDistance() {
        if (mLat == 0.0d || mLon == 0.0d || Double.isNaN(mLat) || Double.isNaN(mLon)) return mDistance;
        if (Double.isNaN(mDistance) || mDistance == 0.0d) {
            Location placeLocation = new Location(mTitle);
            placeLocation.setLatitude(mLat);
            placeLocation.setLongitude(mLon);
            mDistance = GlobalData.getInstance().getLastLocation().distanceTo(placeLocation) / 1000;
        }
        return mDistance;
    }

    public void setDistance(double distance) {
        mDistance = distance;
    }

    public boolean isOpen() {
        if (!mIsOpen) {
            Date date = new Date();
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            long midnight = cal.getTimeInMillis();
            long now = date.getTime() - midnight;
            mIsOpen = ((now - mTimeOpen >= 0) && (mTimeClose - now >= 0));
        }
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

    public long getPriceMin() {
        return mPriceMin;
    }

    public void setPriceMin(long priceMin) {
        mPriceMin = priceMin;
    }

    public long getPriceMax() {
        return mPriceMax;
    }

    public void setPriceMax(long priceMax) {
        mPriceMax = priceMax;
    }

    public String getStartTime() {
        return mStartTime;
    }

    public void setStartTime(String startTime) {
        mStartTime = startTime;
    }

    public String getEndTime() {
        return mEndTime;
    }

    public void setEndTime(String endTime) {
        mEndTime = endTime;
    }

    public String getResId() {
        return mResId;
    }

    public void setResId(String resId) {
        mResId = resId;
    }

    public String getDetailUrl() {
        return mDetailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        mDetailUrl = detailUrl;
    }

    public boolean isFavored() {
        return mIsFavored;
    }

    public void setFavored(boolean favored) {
        mIsFavored = favored;
    }

    public boolean isCheckedIn() {
        return mIsCheckedIn;
    }

    public void setCheckedIn(boolean checkedIn) {
        mIsCheckedIn = checkedIn;
    }

    public long getCheckedInTime() {
        return mCheckedInTime;
    }

    public void setCheckedInTime(long checkedInTime) {
        mCheckedInTime = checkedInTime;
    }

    public long getTimeOpen() {
        return mTimeOpen;
    }

    public void setTimeOpen(long timeOpen) {
        mTimeOpen = timeOpen;
    }

    public long getTimeClose() {
        return mTimeClose;
    }

    public void setTimeClose(long timeClose) {
        mTimeClose = timeClose;
    }

    public double getLat() {
        return mLat;
    }

    public void setLat(double lat) {
        mLat = lat;
    }

    public double getLon() {
        return mLon;
    }

    public void setLon(double lon) {
        mLon = lon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(mDistance);
        dest.writeInt(mIsOpen ? 1 : 0);
        dest.writeString(mPhoto);
        dest.writeString(mTitle);
        dest.writeString(mAddress);
        dest.writeLong(mPriceMin);
        dest.writeLong(mPriceMax);
        dest.writeString(mStartTime);
        dest.writeString(mEndTime);
        dest.writeString(mResId);
        dest.writeString(mDetailUrl);
        dest.writeInt(mIsFavored ? 1 : 0);
        dest.writeInt(mIsCheckedIn ? 1 : 0);
        dest.writeLong(mCheckedInTime);
        dest.writeLong(mTimeOpen);
        dest.writeLong(mTimeClose);
        dest.writeDouble(mLat);
        dest.writeDouble(mLon);
    }

    public static class Builder {
        private double mDistance;
        private boolean mIsOpen;
        private String mPhoto;
        private String mTitle;
        private String mAddress;
        private long mPriceMin;
        private long mPriceMax;
        private String mStartTime;
        private String mEndTime;
        private String mResId;
        private String mDetailUrl;
        private boolean mIsFavored;
        private boolean mIsCheckedIn;
        private long mCheckedInTime;
        private long mTimeOpen;
        private long mTimeClose;
        private double mLat;
        private double mLon;

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

        public Builder setPriceMin(long priceMin) {
            mPriceMin = priceMin;
            return this;
        }

        public Builder setPriceMax(long priceMax) {
            mPriceMax = priceMax;
            return this;
        }

        public Builder setStartTime(String startTime) {
            mStartTime = startTime;
            return this;
        }

        public Builder setEndTime(String endTime) {
            mEndTime = endTime;
            return this;
        }

        public Builder setResId(String resId) {
            mResId = resId;
            return this;
        }

        public Builder setDetailUrl(String detailUrl) {
            mDetailUrl = detailUrl;
            return this;
        }

        public Builder setFavored(boolean favored) {
            mIsFavored = favored;
            return this;
        }

        public Builder setCheckedIn(boolean checkedIn) {
            mIsCheckedIn = checkedIn;
            return this;
        }

        public Builder setCheckedInTime(long checkedInTime) {
            mCheckedInTime = checkedInTime;
            return this;
        }

        public Builder setTimeOpen(long timeOpen) {
            mTimeOpen = timeOpen;
            return this;
        }

        public Builder setTimeClose(long timeClose) {
            mTimeClose = timeClose;
            return this;
        }

        public Builder setLat(double lat) {
            mLat = lat;
            return this;
        }

        public Builder setLon(double lon) {
            mLon = lon;
            return this;
        }

        public Place build() {
            Place place = new Place();
            place.setDistance(mDistance);
            place.setOpen(mIsOpen);
            place.setPhoto(mPhoto);
            place.setTitle(mTitle);
            place.setAddress(mAddress);
            place.setPriceMin(mPriceMin);
            place.setPriceMax(mPriceMax);
            place.setStartTime(mStartTime);
            place.setEndTime(mEndTime);
            place.setResId(mResId);
            place.setDetailUrl(mDetailUrl);
            place.setFavored(mIsFavored);
            place.setCheckedIn(mIsCheckedIn);
            place.setCheckedInTime(mCheckedInTime);
            place.setTimeOpen(mTimeOpen);
            place.setTimeClose(mTimeClose);
            place.setLat(mLat);
            place.setLon(mLon);
            return place;
        }
    }

    public interface JSONKey {
        String DISTANCE = "Distance";
        String OPENING = "IsOpening";
        String PHOTO = "MobilePicturePath";
        String NAME = "Name";
        String ADDRESS = "Address";
        String ITEMS = "searchItems";
        String DETAIL_URL = "DetailUrl";
        String PLACE_ID = "Id";
        String PRICE_MIN = "PriceMin";
        String PRICE_MAX = "PriceMax";
        String TIME_RANGES = "TimeRanges";
        String TIME_START = "StartTime";
        String TIME_END = "EndTime";
        String RES_ID = "RestaurantID";
        String OPENING_TIME = "OpeningTime";
        String TIME_OPEN = "TimeOpen";
        String TIME_CLOSE = "TimeClose";
        String TOTAL_MILLS = "TotalMilliseconds";
        String PHOTO_URL = "MobileImageUrl";
        String RES_URL = "MicrositeUrl";
        String LAT = "Latitude";
        String LON = "Longtitude";
    }
}
