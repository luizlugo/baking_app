package com.volcanolabs.bakingapp.network;

import android.content.Context;

import com.volcanolabs.bakingapp.entities.Recipe;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class RecipeService {
    private RestApi mRestApi;
    private static RecipeService sInstance;

    public RecipeService(Context mContext) {
        mRestApi = NetClientInstance.getRestApi(mContext);
    }

    public static RecipeService getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new RecipeService(context);
        }

        return sInstance;
    }

    public Observable<List<Recipe>> getRecipes() {
        return mRestApi.getRecipes();
    }

}
