package com.trunghoang.aroundhere.ui.discover;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.trunghoang.aroundhere.R;
import com.trunghoang.aroundhere.data.model.Place;
import com.trunghoang.aroundhere.data.model.PlaceRepository;
import com.trunghoang.aroundhere.data.remote.PlaceRemoteDataSource;
import com.trunghoang.aroundhere.ui.adapter.PlaceClickListener;
import com.trunghoang.aroundhere.ui.adapter.PlacesAdapter;
import com.trunghoang.aroundhere.ui.place.PlaceActivity;
import com.trunghoang.aroundhere.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class DiscoverFragment extends Fragment implements DiscoverContract.View,
        OnSuccessListener<Location> {

    private static final int PERMISSION_REQUEST_FINE_LOCATION = 0;
    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private DiscoverContract.Presenter mPresenter;
    private Context mContext;
    private View mRootView;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private PlacesAdapter mPlacesAdapter;
    private TextView mSearchCount;

    public DiscoverFragment() {
        // Required empty public constructor
    }

    public static DiscoverFragment newInstance() {
        return new DiscoverFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mPresenter = new DiscoverPresenter(
                PlaceRepository.getInstance(PlaceRemoteDataSource.getInstance()),
                this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPlacesAdapter = new PlacesAdapter(getActivity(), new ArrayList<Place>());
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mContext);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_discover, container, false);
        mSearchCount = mRootView.findViewById(R.id.text_search_count);
        final RecyclerView recyclerView = mRootView.findViewById(R.id.recycler_place_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mPlacesAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.addOnItemTouchListener(new PlaceClickListener(mContext,
                new PlaceClickListener.OnPlaceClickCallback() {
            @Override
            public void onSingleTapUp(MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child == null) return;
                int adapterPosition = recyclerView.getChildAdapterPosition(child);
                showPlaceActivity(mPlacesAdapter.getItemAtPosition(adapterPosition));
            }
        }));
        mPresenter.start();
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mContext instanceof AppCompatActivity) {
            View rootView = getView();
            AppCompatActivity appCompatActivity = (AppCompatActivity) mContext;
            if (rootView != null) {
                Toolbar toolbar = rootView.findViewById(R.id.toolbar_discover);
                appCompatActivity.setSupportActionBar(toolbar);
                if (appCompatActivity.getSupportActionBar() != null) {
                    appCompatActivity.getSupportActionBar().setTitle(null);
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != PERMISSION_REQUEST_FINE_LOCATION) return;
        if (grantResults.length == PERMISSIONS.length
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            detectLocation();
        } else {
            //TODO: Show request failed status
        }
    }

    @Override
    public void setPresenter(@NonNull DiscoverContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showPlaces(List<Place> places) {
        showLoadingIndicator(false);
        mPlacesAdapter.setData(places);
        showSearchResultCount(places.size());
    }

    @Override
    public void showLoadingPlacesError(Exception e) {
        showLoadingIndicator(false);
        mSearchCount.setVisibility(View.VISIBLE);
        mSearchCount.setText(e.getMessage());
    }

    @Override
    public void showLoadingIndicator(boolean isLoading) {
        ProgressBar progressBar = mRootView.findViewById(R.id.progress_places);
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public boolean isLocationPermissionGranted() {
        return ActivityCompat.checkSelfPermission(mContext,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void requestLocationPermission() {
        requestPermissions(PERMISSIONS, PERMISSION_REQUEST_FINE_LOCATION);
    }

    @Override
    public void onSuccess(Location location) {
        mPresenter.loadPlaces(location);
    }

    @Override
    public void detectLocation() {
        if (getActivity() == null) return;
        if (isLocationPermissionGranted()) {
            mFusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(getActivity(), this);
        } else {
            requestLocationPermission();
        }
    }

    private void showPlaceActivity(Place place) {
        Intent intent = new Intent(mContext, PlaceActivity.class);
        intent.putExtra(Constants.EXTRA_PLACE, place);
        startActivity(intent);
    }

    private void showSearchResultCount(int number) {
        mSearchCount.setVisibility(View.VISIBLE);
        mSearchCount.setText(getString(R.string.sample_search_count, number));
    }
}
