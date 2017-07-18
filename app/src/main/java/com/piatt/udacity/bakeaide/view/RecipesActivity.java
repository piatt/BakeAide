package com.piatt.udacity.bakeaide.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.piatt.udacity.bakeaide.BakeAideApplication;
import com.piatt.udacity.bakeaide.R;
import com.piatt.udacity.bakeaide.manager.RecipesManager;
import com.piatt.udacity.bakeaide.model.Recipe;
import com.piatt.udacity.bakeaide.view.BaseAdapter.OnItemClickListener;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class RecipesActivity extends BaseActivity implements OnItemClickListener<Recipe> {
    private RecipesManager recipesManager;
    private RecipesAdapter recipesAdapter;

    @BindView(R.id.empty_view) ImageView emptyView;
    @BindView(R.id.refresh_layout) SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recipes_view) RecyclerView recipesView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recipesManager = BakeAideApplication.getApp().getRecipesManager();
        configureRefreshViews();
        configureRecipesView();
        configureRecipes(savedInstanceState != null);
    }

    @Override
    protected int getContentView() {
        return R.layout.recipes_activity;
    }

    @Override
    public void onItemClick(Recipe recipe) {
        Intent intent = Henson.with(this)
                .gotoRecipeItemsActivity()
                .recipe(recipe)
                .build();

        startActivity(intent);
    }

    private void configureRefreshViews() {
        refreshLayout.setColorSchemeColors(Color.WHITE);
        refreshLayout.setProgressBackgroundColorSchemeResource(R.color.colorAccent);
        refreshLayout.setOnRefreshListener(() -> recipesManager.fetchRecipes());
    }

    private void configureRecipesView() {
        recipesAdapter = new RecipesAdapter(this);
        recipesView.setAdapter(recipesAdapter);
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
                    emptyView.setVisibility(View.GONE);
                    recipesAdapter.setItems(recipes);
                });
    }
}