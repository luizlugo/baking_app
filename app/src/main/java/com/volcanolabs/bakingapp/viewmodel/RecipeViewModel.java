package com.volcanolabs.bakingapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.volcanolabs.bakingapp.entities.Recipe;
import com.volcanolabs.bakingapp.network.RecipeService;

import java.util.List;

import io.reactivex.rxjava3.observers.DisposableObserver;
import timber.log.Timber;

public class RecipeViewModel extends AndroidViewModel {
    private MutableLiveData<List<Recipe>> recipesObservable = new MutableLiveData<>();
    private RecipeService recipeService;

    public RecipeViewModel(@NonNull Application application) {
        super(application);
        recipeService = RecipeService.getInstance(application);
        getRecipes();
    }

    public LiveData<List<Recipe>> getRecipesObservable() {
        return recipesObservable;
    }

    private void getRecipes() {
        recipeService.getRecipes().subscribe(new DisposableObserver<List<Recipe>>() {
            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull List<Recipe> recipes) {
                recipesObservable.setValue(recipes);
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                Timber.i(e);
            }

            @Override
            public void onComplete() {

            }
        });
    }


}
