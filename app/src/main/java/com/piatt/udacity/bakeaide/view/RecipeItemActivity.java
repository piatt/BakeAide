package com.piatt.udacity.bakeaide.view;

import android.content.Intent;
import android.os.Bundle;

import com.f2prateek.dart.Dart;
import com.f2prateek.dart.InjectExtra;
import com.piatt.udacity.bakeaide.R;
import com.piatt.udacity.bakeaide.model.Ingredient;

/**
 * An activity representing a single RecipeItem detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link RecipeItemsActivity}.
 */
public class RecipeItemActivity extends BaseActivity {
    @InjectExtra Ingredient ingredient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Dart.inject(this);
        configureToolbar(true, ingredient.getIngredient());

        if (savedInstanceState == null) {
            Intent intent = Henson.with(this)
                    .gotoRecipeItemFragment()
                    .ingredient(ingredient)
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