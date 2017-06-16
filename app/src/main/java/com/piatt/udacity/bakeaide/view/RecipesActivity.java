package com.piatt.udacity.bakeaide.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ImageView;

import com.piatt.udacity.bakeaide.R;
import com.piatt.udacity.bakeaide.manager.RecipesManager;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class RecipesActivity extends BaseActivity {
    @BindView(R.id.empty_view) ImageView emptyView;
    @BindView(R.id.refresh_layout) SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        configureRefreshViews();
        fetchRecipes();
    }

    @Override
    protected int getContentView() {
        return R.layout.recipes_activity;
    }

    private void configureRefreshViews() {
        refreshLayout.setColorSchemeColors(Color.WHITE);
        refreshLayout.setProgressBackgroundColorSchemeResource(R.color.colorAccent);
        refreshLayout.setOnRefreshListener(this::fetchRecipes);
    }

    private void fetchRecipes() {
        RecipesManager recipesManager = new RecipesManager(this);
        recipesManager.getRecipes()
                .compose(bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    if (!refreshLayout.isRefreshing()) {
                        refreshLayout.setRefreshing(true);
                    }
                    hideSnackbar();
                })
                .subscribe(recipes -> {
                    refreshLayout.setRefreshing(false);
                    if (recipes.isEmpty()) {
                        showSnackbar(getString(R.string.empty_message));
                    } else if (recipes.size() == 1) {
                        showSnackbar(getString(R.string.connection_message));
                    } else {
                        configureRecyclerView(new RecipesAdapter(recipes));
                    }
                }, error -> {
                    refreshLayout.setRefreshing(false);
                    showSnackbar(getString(R.string.error_message));
                });
    }
}