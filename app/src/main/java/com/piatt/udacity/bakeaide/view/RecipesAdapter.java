package com.piatt.udacity.bakeaide.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.piatt.udacity.bakeaide.R;
import com.piatt.udacity.bakeaide.view.RecipesAdapter.RecipeViewHolder;
import com.piatt.udacity.bakeaide.model.Recipe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecipesAdapter extends RecyclerView.Adapter<RecipeViewHolder> {
    private List<Recipe> recipes = new ArrayList<>();

    public RecipesAdapter(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item_layout, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.onBind(recipes.get(position));
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recipe_name_view) TextView recipeNameView;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void onBind(Recipe recipe) {
            recipeNameView.setText(recipe.getName());
        }

        @OnClick(R.id.recipe_name_view)
        public void onRecipeNameViewClick(View view) {
            Context context = view.getContext();
            Recipe recipe = recipes.get(getAdapterPosition());

            Intent intent = Henson.with(context)
                    .gotoRecipeItemsActivity()
                    .recipe(recipe)
                    .build();

            context.startActivity(intent);
        }
    }
}