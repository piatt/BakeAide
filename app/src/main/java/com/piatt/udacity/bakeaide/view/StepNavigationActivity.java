package com.piatt.udacity.bakeaide.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.f2prateek.dart.Dart;
import com.f2prateek.dart.InjectExtra;
import com.jakewharton.rxbinding2.view.RxView;
import com.piatt.udacity.bakeaide.R;
import com.piatt.udacity.bakeaide.model.Recipe;
import com.piatt.udacity.bakeaide.model.Step;

import butterknife.BindView;

public abstract class StepNavigationActivity extends BaseActivity {
    private final String STEP_NUMBER = "stepNumber";

    @InjectExtra Recipe recipe;
    @Nullable @InjectExtra int stepNumber;
    @Nullable @BindView(R.id.previous_button) FloatingActionButton previousButton;
    @Nullable @BindView(R.id.next_button) FloatingActionButton nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Dart.inject(this);

        if (hasState) {
            stepNumber = savedInstanceState.getInt(STEP_NUMBER, 0);
        }
        configureToolbar(true, recipe.getName());
        configureNavigationButtons();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(STEP_NUMBER, stepNumber);
        super.onSaveInstanceState(outState);
    }

    protected void updateStepView(int position) {
        stepNumber = position;
        Step step = recipe.getSteps().get(stepNumber);
        updateNavigationButtonVisibility();

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

    private void configureNavigationButtons() {
        if (hasNavigationButtons()) {
            RxView.clicks(previousButton)
                    .compose(bindToLifecycle())
                    .subscribe(click -> updateStepView(stepNumber - 1));

            RxView.clicks(nextButton)
                    .compose(bindToLifecycle())
                    .subscribe(click -> updateStepView(stepNumber + 1));
        }
    }

    private void updateNavigationButtonVisibility() {
        if (hasNavigationButtons()) {
            int stepsSize = recipe.getSteps().size();
            previousButton.setVisibility(stepNumber > 0 ? View.VISIBLE : View.GONE);
            nextButton.setVisibility(stepsSize > 1 && stepNumber < stepsSize - 1 ? View.VISIBLE : View.GONE);
        }
    }

    private boolean hasNavigationButtons() {
        return previousButton != null && nextButton != null;
    }
}