package com.volcanolabs.bakingapp.recipe;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.volcanolabs.bakingapp.databinding.FragmentRecipeStepDetailsBinding;
import com.volcanolabs.bakingapp.entities.Step;
import com.volcanolabs.bakingapp.viewmodel.RecipeDetailViewModel;

public class RecipeStepDetailsFragment extends Fragment {
    private static final String STEP_KEY = "stepKey";
    private static final String IS_TABLET_LAYOUT = "isTabletLayout";
    private static final String PLAY_WHEN_READY_KEY = "playWhenReadyKey";
    private static final String PLAYBACK_POSITION_KEY = "playbackPositionKey";
    private static final String CURRENT_WINDOW_KEY = "currentWindowKey";
    private PlayerView videoPlayer;
    private SimpleExoPlayer player;
    private TextView tvIngredients;
    private Step step;
    private RecipeDetailViewModel viewModel;
    private Dialog mFullScreenDialog;
    private boolean mExoPlayerFullscreen;
    private ViewGroup vgMediaFrame;
    private boolean isTablet;
    private TextView tvNoVideo;
    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;
    private String videoUrl;

    public static RecipeStepDetailsFragment newInstance(Step step) {
        Bundle args = new Bundle();
        args.putParcelable(STEP_KEY, step);
        RecipeStepDetailsFragment fragment = new RecipeStepDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static RecipeStepDetailsFragment newInstance(boolean isTablet) {
        Bundle args = new Bundle();
        args.putBoolean(IS_TABLET_LAYOUT, isTablet);
        RecipeStepDetailsFragment fragment = new RecipeStepDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            playWhenReady = savedInstanceState.getBoolean(PLAY_WHEN_READY_KEY);
            playbackPosition = savedInstanceState.getLong(PLAYBACK_POSITION_KEY);
            currentWindow = savedInstanceState.getInt(CURRENT_WINDOW_KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentRecipeStepDetailsBinding binding = FragmentRecipeStepDetailsBinding.inflate(inflater, container, false);
        videoPlayer = binding.vgVideoplayer;
        vgMediaFrame = binding.vgMediaFrame;
        tvIngredients = binding.tvIngredients;
        tvNoVideo = binding.tvNoVideo;
        initFullscreenDialog();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            isTablet = getArguments().getBoolean(IS_TABLET_LAYOUT);

            if (isTablet) {
                viewModel = new ViewModelProvider(requireActivity(), null).get(RecipeDetailViewModel.class);
                viewModel.getStepObservable().observe(this, this::onStepRetrieved);
            } else {
                step = getArguments().getParcelable(STEP_KEY);
                setupUI();
            }
        }
    }

    private void onStepRetrieved(Step step) {
        this.step = step;

        setupUI();

        if (player != null) {
            player.stop(true);
            setVideoToPlayer(videoUrl);
        }
    }

    private void setupUI() {
        if (step != null) {
            tvIngredients.setText(step.getDescription());

            if (step.getVideoUrl() != null && !step.getVideoUrl().isEmpty()
                    || step.getThumbnailUrl() != null && !step.getThumbnailUrl().isEmpty()) {
                videoUrl = (!step.getVideoUrl().isEmpty()) ? step.getVideoUrl() : step.getThumbnailUrl();
                videoPlayer.setVisibility(View.VISIBLE);
                tvNoVideo.setVisibility(View.GONE);
            } else {
                videoPlayer.setVisibility(View.GONE);
                tvNoVideo.setVisibility(View.VISIBLE);
            }
        }
    }

    private void initializePlayer() {
        if (videoUrl != null && !videoUrl.isEmpty()) {
            player = new SimpleExoPlayer.Builder(requireActivity()).build();
            player.setPlayWhenReady(playWhenReady);
            player.seekTo(currentWindow, playbackPosition);
            videoPlayer.setPlayer(player);
            setVideoToPlayer(videoUrl);
        }
    }

    private void setVideoToPlayer(String videoUrl) {
        DataSource.Factory dataFactory = new DefaultDataSourceFactory(requireActivity(), Util.getUserAgent(requireActivity(), "bakingApp"));
        Uri videoUri = Uri.parse(videoUrl);
        MediaSource videoSource = new ProgressiveMediaSource.Factory(dataFactory).createMediaSource(videoUri);
        player.prepare(videoSource, false, false);
    }

    private void initFullscreenDialog() {
        mFullScreenDialog = new Dialog(requireContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
            public void onBackPressed() {
                if (mExoPlayerFullscreen)
                    closeFullscreenDialog();
                super.onBackPressed();
            }
        };
    }

    private void openFullscreenDialog() {
        ((ViewGroup) videoPlayer.getParent()).removeView(videoPlayer);
        mFullScreenDialog.addContentView(videoPlayer, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mExoPlayerFullscreen = true;
        mFullScreenDialog.show();
    }

    private void closeFullscreenDialog() {
        ((ViewGroup) videoPlayer.getParent()).removeView(videoPlayer);
        vgMediaFrame.addView(videoPlayer);
        mExoPlayerFullscreen = false;
        mFullScreenDialog.dismiss();
    }

    @Override
    public void onStart() {
        super.onStart();

        if (Util.SDK_INT >= 24) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (Util.SDK_INT < 24 || player == null) {
            initializePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT >= 24) {
            releasePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (Util.SDK_INT < 24) {
            releasePlayer();
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (!isTablet) {
            if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                openFullscreenDialog();
            } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT && mExoPlayerFullscreen) {
                closeFullscreenDialog();
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(PLAY_WHEN_READY_KEY, playWhenReady);
        outState.putLong(PLAYBACK_POSITION_KEY, playbackPosition);
        outState.putInt(CURRENT_WINDOW_KEY, currentWindow);
    }

    private void releasePlayer() {
        if (player != null) {
            playWhenReady = player.getPlayWhenReady();
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            player.release();
            player = null;
            videoUrl = null;
        }
    }
}
