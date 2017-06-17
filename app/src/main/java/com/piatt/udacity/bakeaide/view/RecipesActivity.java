package com.piatt.udacity.bakeaide.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ImageView;

import com.jakewharton.rxbinding2.view.RxView;
import com.piatt.udacity.bakeaide.BakeAideApplication;
import com.piatt.udacity.bakeaide.R;
import com.piatt.udacity.bakeaide.manager.RecipesManager;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class RecipesActivity extends BaseActivity {
    private RecipesManager recipesManager;

    @BindView(R.id.empty_view) ImageView emptyView;
    @BindView(R.id.refresh_layout) SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recipesManager = BakeAideApplication.getApp().getRecipesManager();
        configureRefreshViews();
        configureRecyclerView(new RecipesAdapter());
        configureRecipes(savedInstanceState != null);
    }

    @Override
    protected int getContentView() {
        return R.layout.recipes_activity;
    }

    private void configureRefreshViews() {
        refreshLayout.setColorSchemeColors(Color.WHITE);
        refreshLayout.setProgressBackgroundColorSchemeResource(R.color.colorAccent);
        refreshLayout.setOnRefreshListener(() -> recipesManager.fetchRecipes());
    }

    private void configureRecipes(boolean hasState) {
        if (!hasState) {
            recipesManager.fetchRecipes();
        }

        recipesManager.onFetchStatusEvent()
                .compose(bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(event -> {
                    refreshLayout.setRefreshing(event.isFetching());
                    if (event.isFetching()) {
                        hideSnackbar();
                    } else if (event.hasMessage()) {
                        showSnackbar(event.getMessage());
                    }
                });

        recipesManager.getRecipes()
                .compose(bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(recipes -> {
                    RxView.visibility(emptyView).accept(false);
                    updateRecyclerView(recipes);
                });
    }
}