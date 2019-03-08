package com.trunghoang.aroundhere.data.remote;

import com.trunghoang.aroundhere.data.model.Review;
import com.trunghoang.aroundhere.util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class ReviewsDownloadHandler extends BaseDataDownloadHandler<List<Review>> {

    @Override
    public void buildUrlConnection(HttpsURLConnection connection) throws IOException {
        super.buildUrlConnection(connection);
        connection.setRequestProperty(Constants.METHOD_GET, Constants.HEADER_ACCEPT_JSON);
        connection.setRequestProperty(Constants.HEADER_X_REQUEST, Constants.HEADER_X_REQUEST_XML);
    }

    @Override
    public List<Review> parseRawToData(String in) throws JSONException {
        JSONObject reader = new JSONObject(in);
        JSONArray itemsJson = reader.getJSONArray(Review.ApiField.API_FIELD_REVIEW_ITEMS);
        List<Review> reviews = new ArrayList<>();
        for (int i = 0; i < itemsJson.length(); i ++) {
            JSONObject itemJson = itemsJson.getJSONObject(i);
            reviews.add(new Review(itemJson));
        }
        return reviews;
    }
}
