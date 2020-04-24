package com.volcanolabs.bakingapp.interfaces;

import com.volcanolabs.bakingapp.entities.Recipe;

public interface RecipesListener {
    void onRecipeClicked(Recipe recipe);
}
