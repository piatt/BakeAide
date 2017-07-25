package com.piatt.udacity.bakeaide.view;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;

import com.jakewharton.rxbinding2.support.v7.widget.RxToolbar;
import com.piatt.udacity.bakeaide.R;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Getter;

public abstract class BaseActivity extends RxAppCompatActivity {
    @Getter boolean hasState;
    private Snackbar snackbar;

    @BindView(R.id.coordinator_layout) CoordinatorLayout coordinatorLayout;
    @Nullable @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getContentView());
        ButterKnife.bind(this);
        hasState = savedInstanceState != null;
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