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
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.trunghoang.aroundhere.R;
import com.trunghoang.aroundhere.data.model.Place;
import com.trunghoang.aroundhere.util.CalcUtils;

import java.util.List;

public class PlacesAdapter extends BaseAdapter<Place, PlacesAdapter.PlaceViewHolder> {

    public PlacesAdapter(Context context, List<Place> places) {
        super(context, places);
    }

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_places, parent, false);
        return new PlaceViewHolder(getContext(), v);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder placeViewHolder, int position) {
        placeViewHolder.bindView(getData().get(position));
    }

    static class PlaceViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private ImageView mPlacePhoto;
        private TextView mDistance;
        private TextView mTextOpen;
        private TextView mTitle;
        private TextView mAddress;

        public PlaceViewHolder(@NonNull Context context, @NonNull View itemView) {
            super(itemView);
            mContext = context;
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
            int cornerRadius = mContext.getResources()
                    .getDimensionPixelSize(R.dimen.place_info_corner_radius);
            Glide.with(mContext)
                    .load(place.getPhoto())
                    .apply(new RequestOptions().transform(new RoundedCorners(cornerRadius)))
                    .into(mPlacePhoto);
            mDistance.setText(mContext.getString(R.string.place_info_distance,
                    CalcUtils.getKm(place.getDistance())));
            String placeIsOpenText;
            if (place.isOpen()) {
                placeIsOpenText = mContext.getString(R.string.place_opening);
            } else {
                placeIsOpenText = mContext.getString(R.string.place_closed);
            }
            mTextOpen.setText(placeIsOpenText);
            mTitle.setText(place.getTitle());
            mAddress.setText(place.getAddress());
        }
    }
}
