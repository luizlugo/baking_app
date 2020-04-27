package com.volcanolabs.bakingapp.recipe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.volcanolabs.bakingapp.adapters.RecipeIngredientsAdapter;
import com.volcanolabs.bakingapp.databinding.FragmentRecipeIngredientsBinding;
import com.volcanolabs.bakingapp.entities.Recipe;
import com.volcanolabs.bakingapp.viewmodel.RecipeDetailViewModel;


public class RecipeIngredientsFragment extends Fragment {
    FragmentRecipeIngredientsBinding binding;
    RecyclerView rvIngredients;
    RecipeDetailViewModel viewModel;
    RecipeIngredientsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRecipeIngredientsBinding.inflate(inflater, container, false);
        rvIngredients = binding.rvIngredients;
        adapter = new RecipeIngredientsAdapter();
        rvIngredients.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity(), null).get(RecipeDetailViewModel.class);
        viewModel.getRecipeObservable().observe(this, this::onRecipeRetrieved);
    }

    private void onRecipeRetrieved(Recipe recipe) {
        adapter.setData(recipe.getIngredients());
    }
}
