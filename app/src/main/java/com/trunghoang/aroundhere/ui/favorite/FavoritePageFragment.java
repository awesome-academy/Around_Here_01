package com.trunghoang.aroundhere.ui.favorite;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.trunghoang.aroundhere.R;
import com.trunghoang.aroundhere.data.db.AppDatabase;
import com.trunghoang.aroundhere.data.db.PlaceDAO;
import com.trunghoang.aroundhere.data.db.PlaceLocalDataSource;
import com.trunghoang.aroundhere.data.model.Place;
import com.trunghoang.aroundhere.data.model.PlaceRepository;
import com.trunghoang.aroundhere.data.remote.PlaceRemoteDataSource;
import com.trunghoang.aroundhere.ui.adapter.FavoriteAdapter;
import com.trunghoang.aroundhere.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class FavoritePageFragment extends Fragment implements FavoritePageContract.View {
    private static final String ARG_FAVORITE_TYPE = "ARG_FAVORITE_TYPE";
    private Context mContext;
    private FavoritePageContract.Presenter mPresenter;
    private String mFavoriteType;
    private FavoriteAdapter mAdapter;

    public FavoritePageFragment() {
        // Required empty public constructor
    }

    public static FavoritePageFragment newInstance(String favoriteType) {
        FavoritePageFragment fragment = new FavoritePageFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_FAVORITE_TYPE, favoriteType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        if (getActivity() != null) {
            PlaceDAO placeDAO = AppDatabase.getInstance(mContext).placeDAO();
            mPresenter = new FavoritePagePresenter(
                    PlaceRepository.getInstance(placeDAO,
                            PlaceRemoteDataSource.getInstance(),
                            PlaceLocalDataSource.getInstance(placeDAO)),
                    this);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mFavoriteType = getArguments().getString(ARG_FAVORITE_TYPE);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorite_page, container, false);
        initRecyclerView(rootView);
        switch (mFavoriteType) {
            case Constants.FavoriteType.ARG_VALUE_FAVORITES:
                mPresenter.loadFavoredPlaces();
                break;
            case Constants.FavoriteType.ARG_VALUE_VISITED:
                mPresenter.loadVisitedPlaces();
                break;
        }
        return rootView;
    }

    @Override
    public void setPresenter(@NonNull FavoritePageContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showPlaces(List<Place> places) {
        mAdapter.setData(places);
    }

    @Override
    public void showLoadingError(Exception e) {
        Toast.makeText(mContext,
                mContext.getResources().getString(R.string.local_loading_error),
                Toast.LENGTH_SHORT).show();
    }

    private void initRecyclerView(View rootView) {
        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_favorites);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new FavoriteAdapter(mContext, new ArrayList<Place>());
        recyclerView.setAdapter(mAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mContext,
                linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setNestedScrollingEnabled(false);
    }
}
