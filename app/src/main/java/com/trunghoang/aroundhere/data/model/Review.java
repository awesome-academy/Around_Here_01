package com.trunghoang.aroundhere.data.model;

import org.json.JSONObject;

public class Review {
    private User mUser;
    private String mCreatedTimeDiff;
    private String mDescription;
    private double mAvgRating;

    public Review(JSONObject jsonObject) {
        if (jsonObject == null) return;
        mUser = new User(jsonObject.optJSONObject(JSONKey.OWNER));
        mCreatedTimeDiff = jsonObject.optString(JSONKey.CREATED_TIME_DIFF);
        mDescription = jsonObject.optString(JSONKey.DESCRIPTION);
        mAvgRating = jsonObject.optDouble(JSONKey.AVG_RATING);
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

    public interface JSONKey {
        String ITEMS = "Items";
        String OWNER = "Owner";
        String CREATED_TIME_DIFF = "CreatedOnTimeDiff";
        String DESCRIPTION = "Description";
        String AVG_RATING = "AvgRating";
    }
}
