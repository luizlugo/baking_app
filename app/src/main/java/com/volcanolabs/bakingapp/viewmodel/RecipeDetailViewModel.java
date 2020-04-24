package com.volcanolabs.bakingapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.volcanolabs.bakingapp.entities.Recipe;

public class RecipeDetailViewModel extends ViewModel {
    private MutableLiveData<Recipe> recipeObservable = new MutableLiveData<>();

    public LiveData<Recipe> getRecipeObservable() {
        return recipeObservable;
    }

    public void setCurrentRecipe(Recipe recipe) {
        recipeObservable.setValue(recipe);
    }
}
