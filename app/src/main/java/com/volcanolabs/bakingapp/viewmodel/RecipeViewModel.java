package com.volcanolabs.bakingapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.volcanolabs.bakingapp.network.RecipeService;

public class RecipeViewModel extends AndroidViewModel {
    private RecipeService recipeService;

    public RecipeViewModel(@NonNull Application application) {
        super(application);
        recipeService = RecipeService.getInstance(application);
    }

    public void getRecipes() {

    }


}
