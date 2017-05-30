package com.piatt.udacity.bakeaide;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipesActivity extends Activity {
    private final String RECIPES_VIEW_KEY = "RECIPES_VIEW_KEY";

    @BindView(R.id.recipes_view) RecyclerView recipesView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
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
//        recipesAdapter = new RecipesAdapter(this);
//        recipesView.setAdapter(recipesAdapter);
    }
}