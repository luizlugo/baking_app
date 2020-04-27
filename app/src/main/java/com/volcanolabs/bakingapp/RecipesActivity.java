package com.volcanolabs.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.idling.CountingIdlingResource;

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
    // The Idling Resource which will be null in production.
    private CountingIdlingResource mIdlingResource = new CountingIdlingResource("DATA_LOADER");

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
        toolbar = binding.appBar;

        mIdlingResource.increment();

        recipesViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(RecipeViewModel.class);
        recipesViewModel.getRecipesObservable().observe(this, this::onRecipesRetrieved);
        setSupportActionBar(toolbar);
    }

    private void onRecipesRetrieved(List<Recipe> recipes) {
        adapter.setData(recipes);
        mIdlingResource.decrement();
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

    /**
     * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
     */
    @VisibleForTesting
    public IdlingResource getIdlingResource() {
        return mIdlingResource;
    }
}
