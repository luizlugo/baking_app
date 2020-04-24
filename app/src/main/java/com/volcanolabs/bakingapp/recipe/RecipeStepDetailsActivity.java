package com.volcanolabs.bakingapp.recipe;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.volcanolabs.bakingapp.R;
import com.volcanolabs.bakingapp.databinding.ActivityRecipeStepDetailsBinding;
import com.volcanolabs.bakingapp.entities.Step;

public class RecipeStepDetailsActivity extends AppCompatActivity {
    public static final String RECIPE_STEP_DETAILS_KEY = "recipeStepDetailsKey";
    ActivityRecipeStepDetailsBinding binding;
    private Toolbar toolbar;
    private Step step;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecipeStepDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(RECIPE_STEP_DETAILS_KEY)) {
            step = bundle.getParcelable(RECIPE_STEP_DETAILS_KEY);
        }

        if (savedInstanceState == null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.vg_step_details, RecipeStepDetailsFragment.newInstance(step)).commit();
        }

        setupUI();
    }

    private void setupUI() {
        toolbar = binding.appBar;
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle(step.getShortDescription());
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
