package com.volcanolabs.bakingapp.recipe;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.volcanolabs.bakingapp.databinding.ActivityRecipesDetailBinding;
import com.volcanolabs.bakingapp.entities.Recipe;

public class RecipeDetailsActivity extends AppCompatActivity {
    public static final String RECIPE_DETAIL_KEY = "recipeDetailKey";
    private ActivityRecipesDetailBinding binding;
    private Toolbar toolbar;
    private Recipe recipe;
    private boolean isTablet;
    private Fragment currentFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecipesDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Bundle params = getIntent().getExtras();
        if (params != null && params.containsKey(RECIPE_DETAIL_KEY)) {
            recipe = params.getParcelable(RECIPE_DETAIL_KEY);
        }
        setupUI(binding);
    }

    private void setupUI(ActivityRecipesDetailBinding binding) {
        toolbar = binding.appBar;
        toolbar.setTitle(recipe.getName());
    }
}
