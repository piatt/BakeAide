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
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
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
public class RecipeItemFragment extends Fragment implements Player.EventListener {
    private final String PLAYER_POSITION = "playerPosition";
    private final String AUTO_PLAY = "autoPlay";

    private long playerPosition;
    private boolean autoPlay = true;
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            playerPosition = savedInstanceState.getLong(PLAYER_POSITION, C.POSITION_UNSET);
            autoPlay = savedInstanceState.getBoolean(AUTO_PLAY, true);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(PLAYER_POSITION, playerPosition);
        outState.putBoolean(AUTO_PLAY, autoPlay);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_item_fragment, container, false);
        ButterKnife.bind(this, view);
        configureViews();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (player != null) {
            playerPosition = Math.max(0, player.getContentPosition());
            autoPlay = player.getPlayWhenReady();
        }
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
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

    private void initializePlayer() {
        if (step.hasVideoURL()) {
            if (player == null) {
                player = ExoPlayerFactory.newSimpleInstance(getContext(), new DefaultTrackSelector());
                player.addListener(this);
                playerView.setPlayer(player);
                player.setPlayWhenReady(autoPlay);
            }
            String userAgent = Util.getUserAgent(getContext(), getString(R.string.app_name));
            MediaSource mediaSource = new ExtractorMediaSource(step.getVideoURI(),
                    new DefaultDataSourceFactory(getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            player.seekTo(playerPosition);
            player.prepare(mediaSource);
        } else if (step.hasThumbnailURL()) {
            // TODO: show image
        }
    }

    private void releasePlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {}

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {}

    @Override
    public void onLoadingChanged(boolean isLoading) {}

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {}

    @Override
    public void onRepeatModeChanged(int repeatMode) {}

    @Override
    public void onPlayerError(ExoPlaybackException error) {
        releasePlayer();
    }

    @Override
    public void onPositionDiscontinuity() {}

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {}
}