package com.piatt.udacity.bakeaide.view;

import android.os.Bundle;

import com.f2prateek.dart.HensonNavigable;
import com.piatt.udacity.bakeaide.R;

/**
 * An activity representing a single RecipeItem detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link RecipeItemsActivity}.
 */

@HensonNavigable
public class RecipeItemActivity extends StepNavigationActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        updateStepView(stepNumber);
    }

    @Override
    protected int getContentView() {
        return R.layout.recipe_item_activity;
    }
}