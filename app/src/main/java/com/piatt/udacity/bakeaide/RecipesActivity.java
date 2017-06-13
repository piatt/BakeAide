package com.piatt.udacity.bakeaide;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;

import com.trello.rxlifecycle2.components.RxActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class RecipesActivity extends RxActivity {
    private final String RECIPES_VIEW_KEY = "recipesView";

    private RecipesManager recipesManager;
    private RecipesAdapter recipesAdapter;

    @BindView(R.id.recipes_view) RecyclerView recipesView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipes_activity);
        ButterKnife.bind(this);
        configureRecipesView();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Parcelable stockViewState = recipesView.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(RECIPES_VIEW_KEY, stockViewState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.containsKey(RECIPES_VIEW_KEY)) {
            Parcelable stockViewState = savedInstanceState.getParcelable(RECIPES_VIEW_KEY);
            recipesView.getLayoutManager().onRestoreInstanceState(stockViewState);
        }
    }

    private void configureRecipesView() {
        recipesManager = new RecipesManager();
        recipesManager.getRecipes()
                .compose(bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(recipes -> !recipes.isEmpty())
                .subscribe(recipes -> {
                    recipesAdapter = new RecipesAdapter(recipes);
                    recipesView.setAdapter(recipesAdapter);
                });
    }
}