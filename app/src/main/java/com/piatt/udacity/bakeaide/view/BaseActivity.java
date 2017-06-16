package com.piatt.udacity.bakeaide.view;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.jakewharton.rxbinding2.support.v7.widget.RxToolbar;
import com.piatt.udacity.bakeaide.R;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseActivity extends RxAppCompatActivity {
    private final String LAYOUT_MANAGER_STATE = "layoutManagerState";

    private Snackbar snackbar;
    private RecyclerView.Adapter adapter;
    private boolean recyclerViewConfigured;

    @BindView(R.id.coordinator_layout) CoordinatorLayout coordinatorLayout;
    @Nullable @BindView(R.id.toolbar) Toolbar toolbar;
    @Nullable @BindView(R.id.recycler_view) RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getContentView());
        ButterKnife.bind(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (recyclerViewConfigured) {
            Parcelable stockViewState = recyclerView.getLayoutManager().onSaveInstanceState();
            outState.putParcelable(LAYOUT_MANAGER_STATE, stockViewState);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (recyclerViewConfigured && savedInstanceState != null
                && savedInstanceState.containsKey(LAYOUT_MANAGER_STATE)) {
            Parcelable stockViewState = savedInstanceState.getParcelable(LAYOUT_MANAGER_STATE);
            recyclerView.getLayoutManager().onRestoreInstanceState(stockViewState);
        }
    }

    protected abstract @LayoutRes int getContentView();

    protected void configureToolbar(boolean displayHomeAsUpEnabled, String title) {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(displayHomeAsUpEnabled);
            getSupportActionBar().setTitle(title);

            if (displayHomeAsUpEnabled) {
                RxToolbar.navigationClicks(toolbar)
                        .compose(bindToLifecycle())
                        .subscribe(click -> onBackPressed());
            }
        }
    }

    protected void configureRecyclerView(RecyclerView.Adapter adapter) {
        if (recyclerView != null) {
            this.adapter = adapter;
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
            recyclerViewConfigured = true;
        }
    }

    protected void showSnackbar(String message) {
        snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(R.string.dismiss_action, click -> snackbar.dismiss());
        snackbar.show();
    }

    protected void hideSnackbar() {
        if (snackbar != null) {
            snackbar.dismiss();
        }
    }
}