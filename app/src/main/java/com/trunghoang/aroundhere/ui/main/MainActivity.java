
package com.trunghoang.aroundhere.ui.main;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.trunghoang.aroundhere.R;
import com.trunghoang.aroundhere.service.SearchProvider;
import com.trunghoang.aroundhere.ui.discover.DiscoverFragment;

public class MainActivity extends AppCompatActivity implements DiscoverFragment.OnFragmentInteractionListener {

    private String mLastQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        return true;
    }

    private void openDiscoverFragment(String query) {
        DiscoverFragment discoverFragment = DiscoverFragment.newInstance(query);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.constraint_fragment_container, discoverFragment);
        if (query != null) fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
