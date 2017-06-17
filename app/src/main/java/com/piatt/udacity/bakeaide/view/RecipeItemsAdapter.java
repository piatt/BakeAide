package com.piatt.udacity.bakeaide.view;

import android.view.View;
import android.widget.TextView;

import com.piatt.udacity.bakeaide.R;
import com.piatt.udacity.bakeaide.model.Ingredient;
import com.piatt.udacity.bakeaide.view.RecipeItemsAdapter.RecipeItemViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class RecipeItemsAdapter extends BaseAdapter<Ingredient, RecipeItemViewHolder> {
    private OnRecipeItemClickListener listener;

    public RecipeItemsAdapter(List<Ingredient> items, OnRecipeItemClickListener listener) {
        super(R.layout.recipe_item_layout, items);
        this.listener = listener;
    }

    @Override
    protected RecipeItemViewHolder getViewHolder(View view) {
        return new RecipeItemViewHolder(view);
    }

    public interface OnRecipeItemClickListener {
        void onRecipeItemClick(Ingredient ingredient);
    }

    public class RecipeItemViewHolder extends BaseViewHolder<Ingredient> {
        @BindView(R.id.recipe_name_view) TextView recipeNameView;

        public RecipeItemViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBind(Ingredient item) {
            recipeNameView.setText(item.getIngredient());
        }

        @OnClick(R.id.recipe_name_view)
        public void onRecipeNameViewClick() {
            Ingredient ingredient = getItem(getAdapterPosition());
            listener.onRecipeItemClick(ingredient);
        }
    }
}