package com.trunghoang.aroundhere.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class PlaceClickListener extends RecyclerView.SimpleOnItemTouchListener {
    private GestureDetectorCompat mGestureDetectorCompat;
    private OnPlaceClickCallback mCallback;

    public PlaceClickListener(Context context, OnPlaceClickCallback callback) {
        mCallback = callback;
        mGestureDetectorCompat = new GestureDetectorCompat(context,
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onDown(MotionEvent e) {
                        return super.onDown(e);
                    }

                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        mCallback.onSingleTapUp(e);
                        return super.onSingleTapUp(e);
                    }
                });
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        mGestureDetectorCompat.onTouchEvent(e);
        return false;
    }

    public static interface OnPlaceClickCallback {
        public void onSingleTapUp(MotionEvent e);
    }
}
