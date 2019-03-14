package com.trunghoang.aroundhere.data.remote;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.trunghoang.aroundhere.data.model.OnDataLoadedCallback;
import com.trunghoang.aroundhere.util.Constants;

import org.json.JSONException;

import java.io.IOException;

public class DownloadTask<T> extends AsyncTask<String, Integer, T> {

    private OnDataLoadedCallback<T> mCallback;
    private DataDownloadHandler<T> mDownloadHandler;
    private Exception mException;

    public DownloadTask(@NonNull DataDownloadHandler<T> downloadHandler,
                        @NonNull OnDataLoadedCallback<T> callback) {
        mDownloadHandler = downloadHandler;
        mCallback = callback;
        mException = null;
    }

    @Override
    protected T doInBackground(String... urls) {
        if (isCancelled() || urls == null || urls.length == 0) return null;
        T result = null;
        String urlString = urls[0];
        try {
            result = mDownloadHandler.downloadUrl(urlString);
            if (result == null) throw new IOException(Constants.EXCEPTION_IO_NO_RESPONSE);
        } catch (IOException e) {
            mException = e;
        } catch (JSONException e) {
            mException = e;
        }
        return result;
    }

    @Override
    protected void onPostExecute(T result) {
        if (mException != null) {
            mCallback.onDataNotAvailable(mException);
        } else {
            mCallback.onDataLoaded(result);
        }
    }
}
