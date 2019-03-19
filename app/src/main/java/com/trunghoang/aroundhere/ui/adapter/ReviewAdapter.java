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
import com.bumptech.glide.request.RequestOptions;
import com.trunghoang.aroundhere.R;
import com.trunghoang.aroundhere.data.model.Review;

import java.util.List;

public class ReviewAdapter extends BaseAdapter<Review, ReviewAdapter.ReviewViewHolder> {

    public ReviewAdapter(Context context, List<Review> data) {
        super(context, data);
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reviews, parent, false);
        return new ReviewViewHolder(getContext(), v);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder reviewViewHolder, int position) {
        reviewViewHolder.bindView(getData().get(position));
    }

    public static class ReviewViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private TextView mTextName;
        private TextView mTextTime;
        private TextView mTextDes;
        private TextView mTextRating;
        private ImageView mImageAvatar;

        public ReviewViewHolder(Context context, @NonNull View itemView) {
            super(itemView);
            mContext = context;
            mTextName = itemView.findViewById(R.id.text_name);
            mTextTime = itemView.findViewById(R.id.text_time);
            mTextDes = itemView.findViewById(R.id.text_review);
            mTextRating = itemView.findViewById(R.id.text_score);
            mImageAvatar = itemView.findViewById(R.id.image_avatar);
        }

        public void bindView(Review review) {
            if (review == null) return;
            Glide.with(mContext)
                    .load(review.getUser().getAvatar())
                    .apply(RequestOptions.circleCropTransform())
                    .into(mImageAvatar);
            mTextName.setText(review.getUser().getDisplayName());
            mTextTime.setText(review.getCreatedTimeDiff());
            mTextRating.setText(String.valueOf(review.getAvgRating()));
            mTextDes.setText(review.getDescription());
        }
    }
}
