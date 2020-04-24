package com.volcanolabs.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.volcanolabs.bakingapp.adapters.RecipesAdapter;
import com.volcanolabs.bakingapp.adapters.RecipesItemDecorator;
import com.volcanolabs.bakingapp.databinding.ActivityRecipesBinding;
import com.volcanolabs.bakingapp.entities.Recipe;
import com.volcanolabs.bakingapp.interfaces.RecipesListener;
import com.volcanolabs.bakingapp.recipe.RecipeDetailsActivity;
import com.volcanolabs.bakingapp.viewmodel.RecipeViewModel;

import java.util.List;

public class RecipesActivity extends AppCompatActivity implements RecipesListener {
    private ActivityRecipesBinding binding;
    private RecipesAdapter adapter;
    private RecyclerView rvRecipes;
    private RecipeViewModel recipesViewModel;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecipesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupUI(binding);
    }

    private void setupUI(ActivityRecipesBinding binding) {
        int cardSpacing = getResources().getDimensionPixelSize(R.dimen.card_margin);
        adapter = new RecipesAdapter(this);
        rvRecipes = binding.rvRecipes;
        rvRecipes.setHasFixedSize(true);
        rvRecipes.setAdapter(adapter);
        rvRecipes.setLayoutManager(new GridLayoutManager(this, numberOfColumns(), LinearLayoutManager.VERTICAL, false));
        rvRecipes.addItemDecoration(new RecipesItemDecorator(numberOfColumns(), cardSpacing, true));
        recipesViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(RecipeViewModel.class);
        recipesViewModel.getRecipesObservable().observe(this, this::onRecipesRetrieved);
        toolbar = binding.appBar;
        setSupportActionBar(toolbar);
    }

    private void onRecipesRetrieved(List<Recipe> recipes) {
        adapter.setData(recipes);
    }

    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // You can change this divider to adjust the size of the item
        int widthDivider = 400;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if (nColumns < 2) return 2; //to keep the grid aspect
        return nColumns;
    }

    @Override
    public void onRecipeClicked(Recipe recipe) {
        Intent recipeDetails = new Intent(this, RecipeDetailsActivity.class);
        recipeDetails.putExtra(RecipeDetailsActivity.RECIPE_DETAIL_KEY, recipe);
        startActivity(recipeDetails);
    }
}
