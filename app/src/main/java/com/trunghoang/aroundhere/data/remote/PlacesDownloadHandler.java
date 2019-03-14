package com.trunghoang.aroundhere.data.remote;

import com.trunghoang.aroundhere.data.model.Place;
import com.trunghoang.aroundhere.util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class PlacesDownloadHandler extends BaseDataDownloadHandler<List<Place>> {

    @Override
    public void buildUrlConnection(HttpsURLConnection connection) throws IOException {
        super.buildUrlConnection(connection);
        connection.setRequestProperty(Constants.HEADER_X_REQUEST, Constants.HEADER_X_REQUEST_XML);
        connection.setRequestProperty(Constants.HEADER_ACCEPT, Constants.HEADER_ACCEPT_JSON);
    }

    @Override
    public List<Place> parseRawToData(String in) throws JSONException {
        List<Place> places = new ArrayList<>();
        JSONObject reader = new JSONObject(in);
        JSONArray itemsJson = reader.getJSONArray(Place.ApiField.API_FIELD_ITEMS);
        for (int i = 0; i < itemsJson.length(); i++) {
            JSONObject itemJson = itemsJson.getJSONObject(i);
            places.add(new Place(itemJson));
        }
        return places;
    }
}
