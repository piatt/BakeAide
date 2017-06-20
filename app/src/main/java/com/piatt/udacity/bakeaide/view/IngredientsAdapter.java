package com.piatt.udacity.bakeaide.view;

import android.view.View;
import android.widget.TextView;

import com.piatt.udacity.bakeaide.R;
import com.piatt.udacity.bakeaide.model.Ingredient;
import com.piatt.udacity.bakeaide.view.IngredientsAdapter.IngredientViewHolder;

import java.util.List;

import butterknife.BindView;

public class IngredientsAdapter extends BaseAdapter<Ingredient, IngredientViewHolder> {
    public IngredientsAdapter(List<Ingredient> items) {
        super(R.layout.ingredient_item_layout, items);
    }

    @Override
    protected IngredientViewHolder getViewHolder(View view) {
        return new IngredientViewHolder(view);
    }

    public class IngredientViewHolder extends BaseViewHolder<Ingredient> {
        @BindView(R.id.ingredient_name_view) TextView ingredientNameView;

        public IngredientViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBind(Ingredient item) {
            ingredientNameView.setText(item.toString());
        }
    }
}