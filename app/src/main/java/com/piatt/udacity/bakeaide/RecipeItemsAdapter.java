package com.piatt.udacity.bakeaide;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.piatt.udacity.bakeaide.RecipeItemsAdapter.RecipeItemViewHolder;
import com.piatt.udacity.bakeaide.model.Ingredient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecipeItemsAdapter extends RecyclerView.Adapter<RecipeItemViewHolder> {
    private OnRecipeItemClickListener listener;
    private List<Ingredient> ingredients = new ArrayList<>();

    public RecipeItemsAdapter(OnRecipeItemClickListener listener, List<Ingredient> ingredients) {
        this.listener = listener;
        this.ingredients = ingredients;
    }

    @Override
    public RecipeItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item, parent, false);
        return new RecipeItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeItemViewHolder holder, int position) {
        holder.onBind(ingredients.get(position));
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public interface OnRecipeItemClickListener {
        void onRecipeItemClick(Ingredient ingredient);
    }

    protected class RecipeItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recipe_name_view) TextView recipeNameView;

        public RecipeItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void onBind(Ingredient ingredient) {
            recipeNameView.setText(ingredient.getIngredient());
        }

        @OnClick(R.id.recipe_name_view)
        public void onRecipeNameViewClick() {
            Ingredient ingredient = ingredients.get(getAdapterPosition());
            listener.onRecipeItemClick(ingredient);
        }
    }
}