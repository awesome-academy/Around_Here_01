package com.trunghoang.aroundhere.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.trunghoang.aroundhere.R;
import com.trunghoang.aroundhere.data.model.Place;

import java.util.List;

public class FavoriteAdapter extends BaseAdapter<Place, FavoriteAdapter.PlaceViewHolder> {

    public FavoriteAdapter(Context context, List<Place> data) {
        super(context, data);
    }

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_favorites, parent, false);
        return new PlaceViewHolder(parent.getContext(), v);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder placeViewHolder, int position) {
        placeViewHolder.bindView(getItemAtPosition(position));
    }

    static class PlaceViewHolder extends RecyclerView.ViewHolder {

        private Context mContext;
        private ImageView mPlacePhoto;
        private TextView mTitle;
        private TextView mAddress;

        public PlaceViewHolder(@NonNull Context context, @NonNull View itemView) {
            super(itemView);
            mContext = context;
            mPlacePhoto = itemView.findViewById(R.id.image_place_photo);
            mTitle = itemView.findViewById(R.id.text_title);
            mAddress = itemView.findViewById(R.id.text_address);
        }

        public void bindView(Place place) {
            Glide.with(mContext)
                    .load(place.getPhoto())
                    .into(mPlacePhoto);
            mTitle.setText(place.getTitle());
            mAddress.setText(place.getAddress());
        }
    }
}
