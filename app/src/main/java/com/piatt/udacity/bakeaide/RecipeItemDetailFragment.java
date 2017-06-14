package com.piatt.udacity.bakeaide;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.f2prateek.dart.Dart;
import com.f2prateek.dart.InjectExtra;
import com.piatt.udacity.bakeaide.model.Ingredient;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A fragment representing a single RecipeItem detail screen.
 * This fragment is either contained in a {@link RecipeItemsActivity}
 * in two-pane mode (on tablets) or a {@link RecipeItemDetailActivity}
 * on handsets.
 */
public class RecipeItemDetailFragment extends Fragment {
    @InjectExtra Ingredient ingredient;
    @BindView(R.id.recipeitem_detail) TextView details;

    public RecipeItemDetailFragment() {}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Dart.inject(this, getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipeitem_detail, container, false);
        ButterKnife.bind(this, rootView);

        details.setText(ingredient.getIngredient());

        return rootView;
    }
}
