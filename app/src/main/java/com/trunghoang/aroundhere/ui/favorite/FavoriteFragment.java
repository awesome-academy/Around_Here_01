package com.trunghoang.aroundhere.ui.favorite;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trunghoang.aroundhere.R;
import com.trunghoang.aroundhere.ui.adapter.FavoritePagerAdapter;

public class FavoriteFragment extends Fragment {

    private Context mContext;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    public static FavoriteFragment newInstance() {
        return new FavoriteFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorite, container, false);
        ViewPager viewPager = rootView.findViewById(R.id.pager_favorites);
        FavoritePagerAdapter adapter = new FavoritePagerAdapter(mContext, getChildFragmentManager());
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = rootView.findViewById(R.id.tab_favorites);
        tabLayout.setupWithViewPager(viewPager);
        return rootView;
    }
}
