package com.piatt.udacity.bakeaide.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.f2prateek.dart.Dart;
import com.f2prateek.dart.InjectExtra;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
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
    private SimpleExoPlayer player;

    @InjectExtra Step step;
    @BindView(R.id.player_view) SimpleExoPlayerView playerView;
    @Nullable @BindView(R.id.header_view) TextView headerView;
    @Nullable @BindView(R.id.description_layout) CardView descriptionLayout;
    @Nullable @BindView(R.id.description_view) TextView descriptionView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Dart.inject(this, getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_item_fragment, container, false);
        ButterKnife.bind(this, view);

        configurePlayer();
        configureViews();

        return view;
    }

    @Override
    public void onDestroy() {
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
        super.onDestroy();
    }

    private void configurePlayer() {
        if (step.hasVideoURL() && player == null) {
            player = ExoPlayerFactory.newSimpleInstance(getContext(), new DefaultTrackSelector());
            playerView.setPlayer(player);
//            player.addListener(this);
            String userAgent = Util.getUserAgent(getContext(), getString(R.string.app_name));
            MediaSource mediaSource = new ExtractorMediaSource(step.getVideoURI(),
                    new DefaultDataSourceFactory(getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            player.prepare(mediaSource);
            player.setPlayWhenReady(true);
        } else if (step.hasThumbnailURL()) {

        }
    }

    private void configureViews() {
        if (headerView != null && descriptionLayout != null && descriptionView != null) {
            headerView.setText(step.getShortDescription());
            if (step.hasDescription()) {
                descriptionView.setText(step.getDescription());
            } else {
                descriptionLayout.setVisibility(View.GONE);
            }
        }
    }
}