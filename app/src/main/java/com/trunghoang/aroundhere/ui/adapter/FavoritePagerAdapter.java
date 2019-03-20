package com.trunghoang.aroundhere.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.trunghoang.aroundhere.R;
import com.trunghoang.aroundhere.ui.favorite.FavoritePageFragment;
import com.trunghoang.aroundhere.util.FavoriteType;

public class FavoritePagerAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private static final int PAGE_FAVORITES = 0;
    private static final int PAGE_VISITED = 1;
    private static final int[] PAGE_TYPES = { PAGE_FAVORITES, PAGE_VISITED };

    public FavoritePagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        String favoriteType = null;
        switch (position) {
            case PAGE_VISITED:
                favoriteType = FavoriteType.ARGUMENT_VALUE_VISITED;
                break;
            case PAGE_FAVORITES:
                favoriteType = FavoriteType.ARGUMENT_VALUE_FAVORITES;
                break;
        }
        return FavoritePageFragment.newInstance(favoriteType);
    }

    @Override
    public int getCount() {
        return PAGE_TYPES.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        switch (position) {
            case PAGE_VISITED:
                title = mContext.getString(R.string.favorites_title_visited);
                break;
            case PAGE_FAVORITES:
                title = mContext.getString(R.string.favorites_title_favorites);
                break;
        }
        return title;
    }
}
