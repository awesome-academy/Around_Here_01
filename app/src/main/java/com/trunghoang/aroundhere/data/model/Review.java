package com.trunghoang.aroundhere.data.model;

import org.json.JSONObject;

public class Review {
    private User mUser;
    private String mCreatedTimeDiff;
    private String mDescription;
    private double mAvgRating;

    public Review(JSONObject jsonObject) {
        if (jsonObject == null) return;
        mUser = new User(jsonObject.optJSONObject(ApiField.API_FIELD_REVIEW_OWNER));
        mCreatedTimeDiff = jsonObject.optString(ApiField.API_FIELD_REVIEW_CREATED_TIME_DIFF);
        mDescription = jsonObject.optString(ApiField.API_FIELD_REVIEW_DESCRIPTION);
        mAvgRating = jsonObject.optDouble(ApiField.API_FIELD_REVIEW_AVG_RATING);
    }

    public User getUser() {
        return mUser;
    }

    public String getCreatedTimeDiff() {
        return mCreatedTimeDiff;
    }

    public String getDescription() {
        return mDescription;
    }

    public double getAvgRating() {
        return mAvgRating;
    }

    public interface ApiField {
        String API_FIELD_REVIEW_ITEMS = "Items";
        String API_FIELD_REVIEW_OWNER = "Owner";
        String API_FIELD_REVIEW_CREATED_TIME_DIFF = "CreatedOnTimeDiff";
        String API_FIELD_REVIEW_DESCRIPTION = "Description";
        String API_FIELD_REVIEW_AVG_RATING = "AvgRating";
    }
}
