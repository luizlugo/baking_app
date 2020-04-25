package com.volcanolabs.bakingapp.recipe;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.volcanolabs.bakingapp.adapters.RecipeStepsAdapter;
import com.volcanolabs.bakingapp.databinding.FragmentRecipeStepsBinding;
import com.volcanolabs.bakingapp.entities.Recipe;
import com.volcanolabs.bakingapp.entities.Step;
import com.volcanolabs.bakingapp.interfaces.RecipeStepsListener;
import com.volcanolabs.bakingapp.viewmodel.RecipeDetailViewModel;

public class RecipeStepsFragment extends Fragment implements RecipeStepsListener {
    FragmentRecipeStepsBinding binding;
    private RecyclerView rvSteps;
    private RecipeStepsAdapter adapter;
    private RecipeDetailViewModel viewModel;
    private RecipeStepsListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRecipeStepsBinding.inflate(inflater, container, false);
        rvSteps = binding.rvSteps;
        adapter = new RecipeStepsAdapter(this::onStepClicked);
        rvSteps.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity(), null).get(RecipeDetailViewModel.class);
        viewModel.getRecipeObservable().observe(this, this::onRecipeDataRetrieved);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof RecipeStepsListener) {
            listener = (RecipeStepsListener) context;
        }
    }

    private void onRecipeDataRetrieved(Recipe recipe) {
        adapter.setData(recipe.getSteps());
    }

    @Override
    public void onStepClicked(Step step) {
        if (listener != null) {
            listener.onStepClicked(step);
        }
    }
}
