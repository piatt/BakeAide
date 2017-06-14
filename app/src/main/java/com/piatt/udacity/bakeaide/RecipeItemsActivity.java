package com.piatt.udacity.bakeaide;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;

import com.f2prateek.dart.Dart;
import com.f2prateek.dart.InjectExtra;
import com.piatt.udacity.bakeaide.RecipeItemsAdapter.OnRecipeItemClickListener;
import com.piatt.udacity.bakeaide.model.Ingredient;
import com.piatt.udacity.bakeaide.model.Recipe;

import butterknife.BindView;

/**
 * An activity representing a list of RecipeItems. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link RecipeItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class RecipeItemsActivity extends BaseActivity implements OnRecipeItemClickListener {

    private boolean twoPaneLayout;

    @InjectExtra Recipe recipe;
    @Nullable @BindView(R.id.detail_layout) FrameLayout detailLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Dart.inject(this);
        configureToolbar(true, recipe.getName());
        configureRecyclerView(new RecipeItemsAdapter(this, recipe.getIngredients()));
        twoPaneLayout = detailLayout != null;
    }

    @Override
    protected int getContentView() {
        return R.layout.recipe_items_activity;
    }

    @Override
    public void onRecipeItemClick(Ingredient ingredient) {
        if (twoPaneLayout) {
            getIntent().putExtras(Henson.with(this)
                    .gotoRecipeItemDetailFragment()
                    .ingredient(ingredient)
                    .build());
            getSupportFragmentManager().beginTransaction().replace(R.id.detail_layout, new RecipeItemDetailFragment()).commit();
        } else {
            Intent intent = Henson.with(this)
                    .gotoRecipeItemDetailActivity()
                    .ingredient(ingredient)
                    .build();
            startActivity(intent);
        }
    }
}