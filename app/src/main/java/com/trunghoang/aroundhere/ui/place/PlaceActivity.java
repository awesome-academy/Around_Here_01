package com.trunghoang.aroundhere.ui.place;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.trunghoang.aroundhere.R;

public class PlaceActivity extends AppCompatActivity implements PlaceContract.View {

    private PlaceContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(null);
        }
    }

    @Override
    public void setPresenter(@NonNull PlaceContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
