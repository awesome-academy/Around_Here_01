package com.trunghoang.aroundhere.data.db;

import android.os.AsyncTask;

import com.trunghoang.aroundhere.data.model.OnDataLoadedCallback;

public class DaoTask<P, T> extends AsyncTask<P, Void, T> {
    private PlaceDAO mPlaceDAO;
    private OnDataLoadedCallback<T> mCallback;
    private DaoHandler<P, T> mHandler;

    public DaoTask(DaoHandler<P, T> handler,
                   PlaceDAO placeDao,
                   OnDataLoadedCallback<T> callback) {
        mPlaceDAO = placeDao;
        mCallback = callback;
        mHandler = handler;
    }

    @Override
    protected T doInBackground(P[] ps) {
        return mHandler.execute(ps, mPlaceDAO);
    }

    @Override
    protected void onPostExecute(T data) {
        if (data == null) {
            mCallback.onDataNotAvailable(null);
        } else {
            mCallback.onDataLoaded(data);
        }
    }
}
