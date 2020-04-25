package com.volcanolabs.bakingapp.adapters;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.volcanolabs.bakingapp.entities.Recipe;
import com.volcanolabs.bakingapp.network.RecipeService;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableObserver;

public class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    Context mContext;
    RecipeService recipeService;
    List<Recipe> mRecipes;

    public GridRemoteViewsFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {
        recipeService = RecipeService.getInstance(mContext);
    }

    @Override
    public void onDataSetChanged() {
        recipeService.getRecipes().subscribe(new DisposableObserver<List<Recipe>>() {
            @Override
            public void onNext(@NonNull List<Recipe> recipes) {
                mRecipes = recipes;
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (mRecipes==null) return 0;
        return mRecipes.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        return null;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
