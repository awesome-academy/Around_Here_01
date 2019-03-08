package com.trunghoang.aroundhere.data.remote;

import com.trunghoang.aroundhere.util.Constants;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public abstract class BaseDataDownloadHandler<T> implements DataDownloadHandler<T> {

    public BaseDataDownloadHandler() {
    }

    @Override
    public T downloadUrl(String urlString) throws IOException, JSONException {
        URL url = new URL(urlString);
        InputStream inputStream = null;
        HttpsURLConnection connection = null;
        T data = null;
        try {
            connection = (HttpsURLConnection) url.openConnection();
            buildUrlConnection(connection);
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpsURLConnection.HTTP_OK) {
                throw new IOException(String.format(Constants.EXCEPTION_IO_HTTP, responseCode));
            }
            inputStream = connection.getInputStream();
            if (inputStream != null) {
                data = parseRawToData(readStream(inputStream));
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return data;
    }

    public void buildUrlConnection(HttpsURLConnection connection) throws IOException {
        connection.setRequestMethod(Constants.METHOD_GET);
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
}
