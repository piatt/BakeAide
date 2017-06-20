package com.piatt.udacity.bakeaide.view;

import android.content.Intent;
import android.os.Bundle;

import com.f2prateek.dart.Dart;
import com.f2prateek.dart.InjectExtra;
import com.piatt.udacity.bakeaide.R;
import com.piatt.udacity.bakeaide.model.Step;

/**
 * An activity representing a single RecipeItem detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link RecipeItemsActivity}.
 */
public class RecipeItemActivity extends BaseActivity {
    @InjectExtra Step step;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Dart.inject(this);
        configureToolbar(true, step.getShortDescription());

        if (savedInstanceState == null) {
            Intent intent = Henson.with(this)
                    .gotoRecipeItemFragment()
                    .step(step)
                    .build();

            getIntent().putExtras(intent);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.detail_layout, new RecipeItemFragment())
                    .commit();
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.recipe_item_activity;
    }
}