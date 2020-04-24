package com.volcanolabs.bakingapp.recipe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.volcanolabs.bakingapp.databinding.FragmentRecipeStepDetailsBinding;
import com.volcanolabs.bakingapp.entities.Step;

public class RecipeStepDetailsFragment extends Fragment {
    private static final String STEP_KEY = "stepKey";
    FragmentRecipeStepDetailsBinding binding;
    private Step step;

    public static RecipeStepDetailsFragment newInstance(Step step) {
        Bundle args = new Bundle();
        args.putParcelable(STEP_KEY, step);
        RecipeStepDetailsFragment fragment = new RecipeStepDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         binding = FragmentRecipeStepDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            step = getArguments().getParcelable(STEP_KEY);
        }
    }
}
