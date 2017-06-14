package com.piatt.udacity.bakeaide;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.f2prateek.dart.Dart;
import com.f2prateek.dart.InjectExtra;
import com.jakewharton.rxbinding2.support.v7.widget.RxToolbar;
import com.piatt.udacity.bakeaide.model.Ingredient;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * An activity representing a single RecipeItem detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link RecipeItemsActivity}.
 */
public class RecipeItemDetailActivity extends RxAppCompatActivity {
    @InjectExtra Ingredient ingredient;
    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_item_detail_activity);
        Dart.inject(this);
        ButterKnife.bind(this);
        configureToolbar();

        if (savedInstanceState == null) {
            getIntent().putExtras(Henson.with(this)
                    .gotoRecipeItemDetailFragment()
                    .ingredient(ingredient)
                    .build());
            getSupportFragmentManager().beginTransaction().replace(R.id.detail_layout, new RecipeItemDetailFragment()).commit();
        }
    }

    private void configureToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(ingredient.getIngredient());

        RxToolbar.navigationClicks(toolbar)
                .compose(bindToLifecycle())
                .subscribe(click -> onBackPressed());
    }
}