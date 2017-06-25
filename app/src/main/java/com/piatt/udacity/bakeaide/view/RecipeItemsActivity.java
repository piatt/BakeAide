package com.piatt.udacity.bakeaide.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;

import com.f2prateek.dart.Dart;
import com.f2prateek.dart.InjectExtra;
import com.piatt.udacity.bakeaide.R;
import com.piatt.udacity.bakeaide.model.Recipe;
import com.piatt.udacity.bakeaide.model.Step;
import com.piatt.udacity.bakeaide.view.BaseAdapter.OnItemClickListener;

import butterknife.BindView;

/**
 * An activity representing a list of RecipeItems. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link RecipeItemActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class RecipeItemsActivity extends BaseActivity implements OnItemClickListener<Step> {
    private boolean twoPaneLayout;

    @InjectExtra Recipe recipe;
    @BindView(R.id.ingredients_view) RecyclerView ingredientsView;
    @BindView(R.id.steps_view) RecyclerView stepsView;
    @Nullable @BindView(R.id.detail_layout) FrameLayout detailLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Dart.inject(this);
        configureToolbar(true, recipe.getName());
        configureIngredientsView();
        configureStepsView();
        twoPaneLayout = detailLayout != null;
    }

    @Override
    protected int getContentView() {
        return R.layout.recipe_items_activity;
    }

    @Override
    public void onItemClick(Step step) {
        if (twoPaneLayout) {
            Intent intent = Henson.with(this)
                    .gotoRecipeItemFragment()
                    .step(step)
                    .build();

            getIntent().putExtras(intent);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.detail_layout, new RecipeItemFragment())
                    .commit();
        } else {
            Intent intent = Henson.with(this)
                    .gotoRecipeItemActivity()
                    .step(step)
                    .build();

            startActivity(intent);
        }
    }

    private void configureIngredientsView() {
        ingredientsView.setLayoutManager(new LinearLayoutManager(this));
        ingredientsView.setAdapter(new IngredientsAdapter(recipe.getIngredients()));
        ingredientsView.setNestedScrollingEnabled(false);
    }

    private void configureStepsView() {
        stepsView.setLayoutManager(new LinearLayoutManager(this));
        stepsView.setAdapter(new StepsAdapter(recipe.getSteps(), this));
        stepsView.setNestedScrollingEnabled(false);
    }
}