package com.piatt.udacity.bakeaide.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.piatt.udacity.bakeaide.R;
import com.piatt.udacity.bakeaide.model.Recipe;
import com.piatt.udacity.bakeaide.view.RecipesAdapter.RecipeViewHolder;
import com.squareup.picasso.Picasso;

import butterknife.BindView;

public class RecipesAdapter extends BaseAdapter<Recipe, RecipeViewHolder> {
    public RecipesAdapter(OnItemClickListener<Recipe> listener) {
        super(R.layout.recipe_item_layout, listener);
    }

    @Override
    protected RecipeViewHolder getViewHolder(View view) {
        return new RecipeViewHolder(view);
    }

    public class RecipeViewHolder extends BaseViewHolder<Recipe> {
        @BindView(R.id.recipe_image_view) ImageView recipeImageView;
        @BindView(R.id.recipe_name_view) TextView recipeNameView;

        public RecipeViewHolder(View itemView) {
            super(itemView);

            RxView.clicks(itemView).subscribe(click -> {
                Recipe recipe = getItem(getAdapterPosition());
                getOnItemClickListener().onItemClick(recipe);
            });
        }

        @Override
        protected void onBind(Recipe recipe) {
            if (recipe.hasImage()) {
                Picasso.with(itemView.getContext()).load(recipe.getImage()).fit().centerCrop().into(recipeImageView);
            } else {
                Picasso.with(itemView.getContext()).load(R.drawable.image_placeholder).fit().centerCrop().into(recipeImageView);
            }
            recipeNameView.setText(recipe.getName());
        }
    }
}