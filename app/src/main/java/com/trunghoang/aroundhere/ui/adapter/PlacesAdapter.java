package com.trunghoang.aroundhere.ui.adapter;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.trunghoang.aroundhere.R;
import com.trunghoang.aroundhere.data.model.Place;

import java.util.List;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.PlaceViewHolder> {
    private List<Place> mPlaces;

    public PlacesAdapter(List<Place> places) {
        mPlaces = places;
    }

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new PlaceViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder placeViewHolder, int position) {
        placeViewHolder.bindView(mPlaces.get(position));
    }

    @Override
    public int getItemCount() {
        return mPlaces == null ? 0 : mPlaces.size();
    }

    static class PlaceViewHolder extends RecyclerView.ViewHolder {
        private ImageView mPlacePhoto;
        private TextView mDistance;
        private TextView mTextOpen;
        private TextView mTitle;
        private TextView mAddress;

        public PlaceViewHolder(@NonNull View itemView) {
            super(itemView);
            mPlacePhoto = itemView.findViewById(R.id.image_place_photo);
            mDistance = itemView.findViewById(R.id.text_distance);
            mTextOpen = itemView.findViewById(R.id.text_is_open);
            mTitle = itemView.findViewById(R.id.text_title);
            mAddress = itemView.findViewById(R.id.text_address);
        }

        public void bindView(Place place) {
            if (place == null) {
                return;
            }
            Picasso.get()
                    .load(place.getPhoto())
                    .into(mPlacePhoto);
            String distance = String.valueOf(place.getDistance());
            mDistance.setText(distance);
            String placeIsOpenText;
            if (place.isOpen()) {
                placeIsOpenText = Resources.getSystem().getString(R.string.place_opening);
            } else {
                placeIsOpenText = Resources.getSystem().getString(R.string.place_closed);
            }
            mTextOpen.setText(placeIsOpenText);
            mTitle.setText(place.getTitle());
            mAddress.setText(place.getAddress());
        }
    }
}
