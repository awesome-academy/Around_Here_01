
package com.trunghoang.aroundhere.ui.main;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.trunghoang.aroundhere.R;
import com.trunghoang.aroundhere.service.SearchProvider;
import com.trunghoang.aroundhere.ui.discover.DiscoverFragment;
import com.trunghoang.aroundhere.ui.favorite.FavoriteFragment;

public class MainActivity extends AppCompatActivity implements
        DiscoverFragment.OnFragmentInteractionListener {

    private static final String TAG_DISCOVER = "discover";
    private String mLastQuery;
    private BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBottomNavigation();
        openDiscoverFragment(null);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    SearchProvider.AUTHORITY, SearchProvider.MODE);
            suggestions.saveRecentQuery(query, null);
            openDiscoverFragment(query);
        }
    }

    @Override
    public void onSearchClick(String lastQuery) {
        mLastQuery = lastQuery;
        onSearchRequested();
    }

    @Override
    public boolean onSearchRequested() {
        startSearch(mLastQuery, false, null, false);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        openDiscoverFragment(null);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() <= 1) {
            finish();
        } else {
            if (mBottomNavigationView.getSelectedItemId() != R.id.item_discover) {
                mBottomNavigationView.setSelectedItemId(R.id.item_discover);
            } else {
                super.onBackPressed();
            }
        }
    }

    private void initBottomNavigation() {
        mBottomNavigationView = findViewById(R.id.bottom_main);
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.item_discover:
                        openDiscoverFragment(null);
                        break;
                    case R.id.item_favorites:
                        openFavoriteFragment();
                        break;
                }
                return true;
            }
        });
    }

    private void openDiscoverFragment(String query) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        DiscoverFragment lastFragment = (DiscoverFragment) fragmentManager.findFragmentByTag(TAG_DISCOVER);
        if (lastFragment != null && query == null) {
            fragmentManager.popBackStackImmediate(TAG_DISCOVER, 0);
            fragmentTransaction.show(lastFragment).commit();
        } else {
            DiscoverFragment discoverFragment = DiscoverFragment.newInstance(query);
            fragmentTransaction.add(R.id.constraint_fragment_container, discoverFragment, TAG_DISCOVER);
            fragmentTransaction.addToBackStack(TAG_DISCOVER);
            fragmentTransaction.commit();
        }
    }

    private void openFavoriteFragment() {
        FavoriteFragment favoriteFragment = FavoriteFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.constraint_fragment_container, favoriteFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
