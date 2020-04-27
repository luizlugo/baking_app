package com.volcanolabs.bakingapp.recipe;

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

        initializePlayer();

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
        player.stop(true);
        setupUI();
    }

    private void setupUI() {
        if (step != null) {
            if (step.getVideoUrl() != null && !step.getVideoUrl().isEmpty()
                    || step.getThumbnailUrl() != null && !step.getThumbnailUrl().isEmpty()) {
                String url = (!step.getVideoUrl().isEmpty()) ? step.getVideoUrl() : step.getThumbnailUrl();
                setVideoToPlayer(url);
                videoPlayer.setVisibility(View.VISIBLE);
                tvNoVideo.setVisibility(View.GONE);
            } else {
                videoPlayer.setVisibility(View.GONE);
                tvNoVideo.setVisibility(View.VISIBLE);
            }

            tvIngredients.setText(step.getDescription());
        }
    }

    private void initializePlayer() {
        player = new SimpleExoPlayer.Builder(requireActivity()).build();
        videoPlayer.setPlayer(player);
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
    public void onDestroy() {
        super.onDestroy();
        player.stop();
        player.release();
        player = null;
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
}
