package com.piatt.udacity.bakeaide;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.piatt.udacity.bakeaide.RecipesAdapter.RecipeViewHolder;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item, parent, false);
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

    protected class RecipeViewHolder extends RecyclerView.ViewHolder {
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
            Recipe recipe = recipes.get(getAdapterPosition());
            Intent intent = Henson.with(view.getContext())
                    .gotoRecipeItemsActivity()
                    .recipe(recipe)
                    .build();
            view.getContext().startActivity(intent);
        }
    }
}
