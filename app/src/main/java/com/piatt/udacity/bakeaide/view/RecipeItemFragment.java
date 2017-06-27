package com.piatt.udacity.bakeaide.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.f2prateek.dart.Dart;
import com.f2prateek.dart.InjectExtra;
import com.piatt.udacity.bakeaide.R;
import com.piatt.udacity.bakeaide.model.Step;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A fragment representing a single RecipeItem detail screen.
 * This fragment is either contained in a {@link RecipeItemsActivity}
 * in two-pane mode (on tablets) or a {@link RecipeItemActivity}
 * on handsets.
 */
public class RecipeItemFragment extends Fragment {
    @InjectExtra Step step;
    @Nullable @BindView(R.id.description_view) TextView descriptionView;

    public RecipeItemFragment() {}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Dart.inject(this, getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_item_fragment, container, false);
        ButterKnife.bind(this, view);

        if (descriptionView != null) {
            descriptionView.setText(step.getDescription());
        }

        return view;
    }
}