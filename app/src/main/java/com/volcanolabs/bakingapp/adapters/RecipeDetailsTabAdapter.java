package com.volcanolabs.bakingapp.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.volcanolabs.bakingapp.recipe.RecipeIngredientsFragment;
import com.volcanolabs.bakingapp.recipe.RecipeStepsFragment;

public class RecipeDetailsTabAdapter extends FragmentStateAdapter {
    public RecipeDetailsTabAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return (position == 0) ? new RecipeIngredientsFragment() : new RecipeStepsFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
