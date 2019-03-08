package com.trunghoang.aroundhere.ui.discover;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trunghoang.aroundhere.R;
import com.trunghoang.aroundhere.data.model.Place;
import com.trunghoang.aroundhere.ui.adapter.PlacesAdapter;

import java.util.ArrayList;
import java.util.List;

public class DiscoverFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Context mContext;
    private View mRootView;

    public DiscoverFragment() {
        // Required empty public constructor
    }

    public static DiscoverFragment newInstance() {
        DiscoverFragment fragment = new DiscoverFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_discover, container, false);
        TextView searchCountView = mRootView.findViewById(R.id.text_search_count);
        searchCountView.setText(getString(R.string.sample_search_count));
        RecyclerView recyclerView = mRootView.findViewById(R.id.recycler_place_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<Place> dataSet = new ArrayList<>();
        PlacesAdapter placesAdapter = new PlacesAdapter(addSampleData(dataSet));
        recyclerView.setAdapter(placesAdapter);
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private List<Place> addSampleData(List<Place> dataSet) {
        Place place = new Place();
        place.setPhoto(getString(R.string.sample_photo));
        place.setDistance(1000L);
        place.setAddress(getString(R.string.sample_address));
        place.setTitle(getString(R.string.sample_title));
        place.setOpen(true);
        dataSet.add(place);
        dataSet.add(place);
        return dataSet;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
