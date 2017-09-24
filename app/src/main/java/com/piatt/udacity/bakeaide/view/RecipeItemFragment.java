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
    private SimpleExoPlayer player;
    private boolean shouldAutoPlay;
    private int resumeWindow;
    private long resumePosition;

    @InjectExtra Step step;
    @BindView(R.id.player_view) SimpleExoPlayerView playerView;
    @Nullable @BindView(R.id.header_view) TextView headerView;
    @Nullable @BindView(R.id.description_layout) CardView descriptionLayout;
    @Nullable @BindView(R.id.description_view) TextView descriptionView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Dart.inject(this, getActivity());
        shouldAutoPlay = true;
        clearResumePosition();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_item_fragment, container, false);
        ButterKnife.bind(this, view);
        playerView.requestFocus();
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
                player.setPlayWhenReady(shouldAutoPlay);
            }
            String userAgent = Util.getUserAgent(getContext(), getString(R.string.app_name));
            MediaSource mediaSource = new ExtractorMediaSource(step.getVideoURI(),
                    new DefaultDataSourceFactory(getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            boolean haveResumePosition = resumeWindow != C.INDEX_UNSET;
            if (haveResumePosition) {
                player.seekTo(resumeWindow, resumePosition);
            }
            player.prepare(mediaSource, !haveResumePosition, false);
        } else if (step.hasThumbnailURL()) {
            // TODO: show image
        }
    }

    private void releasePlayer() {
        if (player != null) {
            shouldAutoPlay = player.getPlayWhenReady();
            updateResumePosition();
            player.release();
            player = null;
        }
    }

    private void updateResumePosition() {
        resumeWindow = player.getCurrentWindowIndex();
        resumePosition = Math.max(0, player.getContentPosition());
    }

    private void clearResumePosition() {
        resumeWindow = C.INDEX_UNSET;
        resumePosition = C.TIME_UNSET;
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