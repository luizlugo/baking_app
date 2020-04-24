package com.volcanolabs.bakingapp.recipe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.volcanolabs.bakingapp.databinding.FragmentRecipeStepsBinding;

public class RecipeStepsFragment extends Fragment {
    FragmentRecipeStepsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRecipeStepsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
