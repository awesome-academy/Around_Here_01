package com.trunghoang.aroundhere.ui.place;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.trunghoang.aroundhere.R;
import com.trunghoang.aroundhere.data.model.Place;
import com.trunghoang.aroundhere.data.model.PlaceRepository;
import com.trunghoang.aroundhere.data.model.Review;
import com.trunghoang.aroundhere.data.remote.PlaceRemoteDataSource;
import com.trunghoang.aroundhere.ui.adapter.ReviewAdapter;
import com.trunghoang.aroundhere.util.CalcUtils;
import com.trunghoang.aroundhere.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class PlaceActivity extends AppCompatActivity implements PlaceContract.View {

    private PlaceContract.Presenter mPresenter;
    private ReviewAdapter mReviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        initToolbar();
        initRecyclerView();
        Intent intent = getIntent();
        Place place = null;
        if (intent != null && intent.hasExtra(Constants.EXTRA_PLACE)) {
            place = intent.getParcelableExtra(Constants.EXTRA_PLACE);
            bindInitView(place);
        }
        mPresenter = new PlacePresenter(place,
                PlaceRepository.getInstance(PlaceRemoteDataSource.getInstance()), this);
        mPresenter.start();
    }

    @Override
    public void setPresenter(@NonNull PlaceContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showPlace(Place place) {
        TextView textPrice = findViewById(R.id.text_price);
        TextView textTime = findViewById(R.id.text_time);
        textPrice.setText(getString(R.string.place_info_price,
                place.getPriceMin(),
                place.getPriceMax()));
        textTime.setText(getString(R.string.place_info_time,
                place.getStartTime(),
                place.getEndTime()));
    }

    @Override
    public void showReviews(List<Review> reviews) {
        showLoadingIndicator(false);
        TextView textLoadingStatus = findViewById(R.id.text_no_reviews);
        textLoadingStatus.setVisibility(reviews.size() == 0 ? View.VISIBLE : View.GONE);
        mReviewAdapter.setData(reviews);
    }

    @Override
    public void showLoadingError(Exception e) {
        showLoadingIndicator(false);
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoadingIndicator(boolean isLoading) {
        ProgressBar progressBar = findViewById(R.id.progress_reviews);
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE);
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(null);
        }
    }

    private void initRecyclerView() {
        mReviewAdapter = new ReviewAdapter(this, new ArrayList<Review>());
        RecyclerView recyclerView = findViewById(R.id.recycler_review);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(mReviewAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    private void bindInitView(Place place) {
        TextView textTitle = findViewById(R.id.text_title);
        TextView textAddress = findViewById(R.id.text_address);
        TextView textDistance = findViewById(R.id.text_distance);
        TextView textIsOpen = findViewById(R.id.text_is_open);
        ImageView imagePlace = findViewById(R.id.app_bar_image);
        Glide.with(this)
                .load(place.getPhoto())
                .into(imagePlace);
        textTitle.setText(place.getTitle());
        textAddress.setText(place.getAddress());
        textDistance.setText(getString(R.string.place_info_distance,
                CalcUtils.getKm(place.getDistance())));
        String placeIsOpenText;
        if (place.isOpen()) {
            placeIsOpenText = getString(R.string.place_opening);
        } else {
            placeIsOpenText = getString(R.string.place_closed);
        }
        textIsOpen.setText(placeIsOpenText);
    }
}
