package com.piatt.udacity.bakeaide.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.f2prateek.dart.HensonNavigable;
import com.piatt.udacity.bakeaide.R;
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

@HensonNavigable
public class RecipeItemsActivity extends StepNavigationActivity implements OnItemClickListener<Step> {
    private boolean twoPaneLayout;

    @BindView(R.id.ingredients_header_view) TextView ingredientsHeaderView;
    @BindView(R.id.servings_view) TextView servingsView;
    @BindView(R.id.steps_header_view) TextView stepsHeaderView;
    @BindView(R.id.ingredients_view) RecyclerView ingredientsView;
    @BindView(R.id.steps_view) RecyclerView stepsView;
    @Nullable @BindView(R.id.detail_layout) FrameLayout detailLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        twoPaneLayout = detailLayout != null;
        if (twoPaneLayout) {
            updateStepView(stepNumber);
        }
        configureIngredientsViews();
        configureStepsViews();
    }

    @Override
    protected int getContentView() {
        return R.layout.recipe_items_activity;
    }

    @Override
    public void onItemClick(Step step, int position) {
        if (twoPaneLayout) {
            updateStepView(position);
        } else {
            Intent intent = Henson.with(this)
                    .gotoRecipeItemActivity()
                    .recipe(recipe)
                    .stepNumber(position)
                    .build();

            startActivity(intent);
        }
    }

    private void configureIngredientsViews() {
        if (recipe.hasIngredients()) {
            ingredientsHeaderView.setVisibility(View.VISIBLE);
            ingredientsView.setLayoutManager(new LinearLayoutManager(this));
            ingredientsView.setAdapter(new IngredientsAdapter(recipe.getIngredients()));
            ingredientsView.setNestedScrollingEnabled(false);
        }
        if (recipe.hasServings()) {
            servingsView.setVisibility(View.VISIBLE);
            servingsView.setText(getResources().getQuantityString(R.plurals.servings_message, recipe.getServings(), recipe.getServings()));
        }
    }

    private void configureStepsViews() {
        if (recipe.hasSteps()) {
            stepsHeaderView.setVisibility(View.VISIBLE);
            stepsView.setLayoutManager(new LinearLayoutManager(this));
            stepsView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
            stepsView.setAdapter(new StepsAdapter(recipe.getSteps(), this));
            stepsView.setNestedScrollingEnabled(false);

            if (!hasState && twoPaneLayout) {
                stepsView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        stepsView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        stepsView.findViewHolderForAdapterPosition(0).itemView.performClick();
                    }
                });
            }
        }
    }
}