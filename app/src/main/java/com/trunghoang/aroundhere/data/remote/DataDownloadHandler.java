package com.trunghoang.aroundhere.data.remote;

import org.json.JSONException;

import java.io.IOException;

public interface DataDownloadHandler<T> {
    public T downloadUrl(String urlString) throws IOException, JSONException;

    public T parseRawToData(String in) throws JSONException;
}
