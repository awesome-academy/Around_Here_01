package com.trunghoang.aroundhere.data.model;

import org.json.JSONObject;

public class User {
    private String mDisplayName;
    private String mAvatar;

    public User(JSONObject jsonObject) {
        if (jsonObject == null) return;
        mDisplayName = jsonObject.optString(ApiField.API_FIELD_REVIEW_DISPLAY_NAME);
        mAvatar = jsonObject.optString(ApiField.API_FIELD_REVIEW_AVATAR);
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public interface ApiField {
        String API_FIELD_REVIEW_DISPLAY_NAME = "DisplayName";
        String API_FIELD_REVIEW_AVATAR = "Avatar";
    }
}
