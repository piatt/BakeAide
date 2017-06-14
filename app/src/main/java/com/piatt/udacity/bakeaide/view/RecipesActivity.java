package com.piatt.udacity.bakeaide.view;

import android.os.Bundle;

import com.piatt.udacity.bakeaide.R;
import com.piatt.udacity.bakeaide.manager.RecipesManager;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class RecipesActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RecipesManager recipesManager = new RecipesManager();
        recipesManager.getRecipes()
                .compose(bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(recipes -> !recipes.isEmpty())
                .subscribe(recipes -> configureRecyclerView(new RecipesAdapter(recipes)));
    }

    @Override
    protected int getContentView() {
        return R.layout.recipes_activity;
    }
}