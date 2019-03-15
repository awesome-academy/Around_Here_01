package com.trunghoang.aroundhere.data.model;

import org.json.JSONObject;

public class User {
    private String mDisplayName;
    private String mAvatar;

    public User(JSONObject jsonObject) {
        if (jsonObject == null) return;
        mDisplayName = jsonObject.optString(JSONKey.DISPLAY_NAME);
        mAvatar = jsonObject.optString(JSONKey.AVATAR);
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public interface JSONKey {
        String DISPLAY_NAME = "DisplayName";
        String AVATAR = "Avatar";
    }
}
