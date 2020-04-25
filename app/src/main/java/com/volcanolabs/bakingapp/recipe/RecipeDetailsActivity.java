package com.volcanolabs.bakingapp.recipe;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.volcanolabs.bakingapp.R;
import com.volcanolabs.bakingapp.databinding.ActivityRecipesDetailBinding;
import com.volcanolabs.bakingapp.entities.Recipe;
import com.volcanolabs.bakingapp.entities.Step;
import com.volcanolabs.bakingapp.interfaces.RecipeStepsListener;
import com.volcanolabs.bakingapp.viewmodel.RecipeDetailViewModel;

public class RecipeDetailsActivity extends AppCompatActivity implements RecipeStepsListener {
    public static final String RECIPE_DETAIL_KEY = "recipeDetailKey";
    private ActivityRecipesDetailBinding binding;
    private Toolbar toolbar;
    private Recipe recipe;
    private boolean isTablet;
    private Fragment currentFragment;
    private RecipeDetailViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecipesDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        isTablet = (binding.vgTabletContainer != null);
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(RecipeDetailViewModel.class);
        Bundle params = getIntent().getExtras();
        if (params != null && params.containsKey(RECIPE_DETAIL_KEY)) {
            recipe = params.getParcelable(RECIPE_DETAIL_KEY);
            viewModel.setCurrentRecipe(recipe);

            if (savedInstanceState == null && isTablet) {
                setTabletLayout();
            }
        }
        setupUI();
    }

    private void setupUI() {
        toolbar = binding.appBar;
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(recipe.getName());
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setTabletLayout() {
        initStepDetailFragment();
        if (recipe != null && recipe.getSteps() != null && recipe.getSteps().size() > 0) {
            viewModel.setCurrentStep(recipe.getSteps().get(0));
        }
    }

    private void initStepDetailFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.vg_step_details_container, RecipeStepDetailsFragment.newInstance(true)).commit();
    }

    @Override
    public void onStepClicked(Step step) {
        if (!isTablet) {
            // Open step details activity
            Intent stepDetails = new Intent(this, RecipeStepDetailsActivity.class);
            stepDetails.putExtra(RecipeStepDetailsActivity.RECIPE_STEP_DETAILS_KEY, step);
            startActivity(stepDetails);
        } else {
            // Refresh step detail fragment with new fragment data
            viewModel.setCurrentStep(step);
        }
    }
}
