package com.volcanolabs.bakingapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.volcanolabs.bakingapp.entities.Recipe;
import com.volcanolabs.bakingapp.entities.Step;

public class RecipeDetailViewModel extends ViewModel {
    private MutableLiveData<Recipe> recipeObservable = new MutableLiveData<>();
    private MutableLiveData<Step> stepObservable = new MutableLiveData<>();

    public LiveData<Recipe> getRecipeObservable() {
        return recipeObservable;
    }

    public LiveData<Step> getStepObservable() {
        return stepObservable;
    }

    public void setCurrentRecipe(Recipe recipe) {
        recipeObservable.setValue(recipe);
    }

    public void setCurrentStep(Step step) {
        stepObservable.setValue(step);
    }
}
