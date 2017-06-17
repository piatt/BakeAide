package com.piatt.udacity.bakeaide.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.piatt.udacity.bakeaide.R;
import com.piatt.udacity.bakeaide.model.Recipe;
import com.piatt.udacity.bakeaide.view.RecipesAdapter.RecipeViewHolder;

import butterknife.BindView;
import butterknife.OnClick;

public class RecipesAdapter extends BaseAdapter<Recipe, RecipeViewHolder> {
    public RecipesAdapter() {
        super(R.layout.recipe_item_layout);
    }

    @Override
    protected RecipeViewHolder getViewHolder(View view) {
        return new RecipeViewHolder(view);
    }

    public class RecipeViewHolder extends BaseViewHolder<Recipe> {
        @BindView(R.id.recipe_name_view) TextView recipeNameView;

        public RecipeViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBind(Recipe item) {
            recipeNameView.setText(item.getName());
        }

        @OnClick(R.id.recipe_name_view)
        public void onRecipeNameViewClick(View view) {
            Context context = view.getContext();
            Recipe recipe = getItem(getAdapterPosition());

            Intent intent = Henson.with(context)
                    .gotoRecipeItemsActivity()
                    .recipe(recipe)
                    .build();

            context.startActivity(intent);
        }
    }
}