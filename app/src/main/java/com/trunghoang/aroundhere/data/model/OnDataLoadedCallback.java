package com.trunghoang.aroundhere.data.model;

public interface OnDataLoadedCallback<T> {
    void onDataLoaded(T data);

    void onDataNotAvailable(Exception exception);
}
