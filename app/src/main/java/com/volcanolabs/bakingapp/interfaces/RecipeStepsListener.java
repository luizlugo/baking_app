package com.volcanolabs.bakingapp.interfaces;

import com.volcanolabs.bakingapp.entities.Step;

public interface RecipeStepsListener {
    void onStepClicked(Step step);
}
