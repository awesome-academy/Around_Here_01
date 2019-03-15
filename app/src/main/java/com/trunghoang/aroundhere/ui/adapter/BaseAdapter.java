package com.trunghoang.aroundhere.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public abstract class BaseAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    private List<T> mData;
    private Context mContext;

    public BaseAdapter(Context context, List<T> data) {
        mData = data;
        mContext = context;
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public T getItemAtPosition(int position) {
        return mData.get(position);
    }

    public Context getContext() {
        return mContext;
    }

    public List<T> getData() {
        return mData;
    }

    public void setData(List<T> data) {
        mData = data;
        notifyDataSetChanged();
    }
}
