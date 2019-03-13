
package com.trunghoang.aroundhere.ui.main;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.trunghoang.aroundhere.R;
import com.trunghoang.aroundhere.ui.discover.DiscoverFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        DiscoverFragment discoverFragment = DiscoverFragment.newInstance();
        fragmentTransaction.add(R.id.constraint_fragment_container, discoverFragment);
        fragmentTransaction.commit();
    }
}
