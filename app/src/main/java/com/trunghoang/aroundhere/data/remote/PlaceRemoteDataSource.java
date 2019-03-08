package com.trunghoang.aroundhere.data.remote;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.trunghoang.aroundhere.data.model.ApiHelper;
import com.trunghoang.aroundhere.data.model.Place;
import com.trunghoang.aroundhere.data.model.PlaceDataSource;
import com.trunghoang.aroundhere.util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class PlaceRemoteDataSource implements PlaceDataSource {
    private static PlaceRemoteDataSource sInstance;

    private PlaceRemoteDataSource() {
    }

    public static PlaceRemoteDataSource getInstance() {
        if (sInstance == null) {
            sInstance = new PlaceRemoteDataSource();
        }
        return sInstance;
    }

    @Override
    public void getPlaces(@NonNull LoadPlacesCallback callback) {
        DownloadTask downloadTask = new DownloadTask(callback);
        downloadTask.execute(ApiHelper.getApiUrl());
    }

    static class DownloadTask extends AsyncTask<String, Integer, List<Place>> {

        private LoadPlacesCallback mCallback;
        private Exception mException;

        public DownloadTask(@NonNull LoadPlacesCallback callback) {
            mCallback = callback;
            mException = null;
        }

        @Override
        protected List<Place> doInBackground(String... urls) {
            if (isCancelled() || urls == null || urls.length == 0) return null;
            List<Place> result = null;
            String urlString = urls[0];
            try {
                result = downloadUrl(urlString);
                if (result == null) throw new IOException(Constants.EXCEPTION_IO_NO_RESPONSE);
            } catch (IOException e) {
                mException = e;
            } catch (JSONException e) {
                mException = e;
            }
            return result;
        }

        @Override
        protected void onPostExecute(List<Place> result) {
            if (mException != null && mCallback != null) {
                mCallback.OnDataNotAvailable(mException);
            } else {
                if (result == null || mCallback == null) return;
                mCallback.OnPlacesLoaded(result);
            }
        }

        private List<Place> downloadUrl(String urlString) throws IOException, JSONException {
            URL url = new URL(urlString);
            InputStream inputStream = null;
            HttpsURLConnection connection = null;
            List<Place> places = new ArrayList<>();
            try {
                connection = (HttpsURLConnection) url.openConnection();
                connection.setRequestMethod(Constants.METHOD_GET);
                connection.setRequestProperty(Constants.HEADER_ACCEPT, Constants.HEADER_ACCEPT_JSON);
                connection.setRequestProperty(Constants.HEADER_X_REQUEST, Constants.HEADER_X_REQUEST_XML);
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode != HttpsURLConnection.HTTP_OK) {
                    throw new IOException(String.format(Constants.EXCEPTION_IO_HTTP, responseCode));
                }
                inputStream = connection.getInputStream();
                if (inputStream != null) {
                    places = parseRawDataToPlaces(readStream(inputStream));
                }
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return places;
        }

        private String readStream(InputStream stream) throws IOException {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            reader.close();
            return builder.toString();
        }

        private List<Place> parseRawDataToPlaces(String in) throws JSONException {
            List<Place> places = new ArrayList<>();
            JSONObject reader = new JSONObject(in);
            JSONArray itemsJson = reader.getJSONArray(ApiHelper.API_FIELD_ITEMS);
            for (int i = 0; i < itemsJson.length(); i++) {
                JSONObject itemJson = itemsJson.getJSONObject(i);
                double distance = itemJson.getDouble(ApiHelper.API_FIELD_DISTANCE);
                boolean isOpen = itemJson.getBoolean(ApiHelper.API_FIELD_OPENING);
                String photo = itemJson.getString(ApiHelper.API_FIELD_PHOTO);
                String title = itemJson.getString(ApiHelper.API_FIELD_NAME);
                String address = itemJson.getString(ApiHelper.API_FIELD_ADDRESS);
                places.add(new Place.Builder()
                        .setDistance(distance)
                        .setOpen(isOpen)
                        .setPhoto(photo)
                        .setTitle(title)
                        .setAddress(address)
                        .build());
            }
            return places;
        }
    }
}
