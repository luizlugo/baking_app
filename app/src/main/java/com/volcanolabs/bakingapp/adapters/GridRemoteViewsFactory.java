package com.volcanolabs.bakingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.volcanolabs.bakingapp.R;
import com.volcanolabs.bakingapp.entities.Recipe;
import com.volcanolabs.bakingapp.network.RecipeService;
import com.volcanolabs.bakingapp.recipe.RecipeDetailsActivity;

import java.util.List;

public class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;
    private RecipeService recipeService;
    private List<Recipe> mRecipes;

    public GridRemoteViewsFactory(Context context) {
        mContext = context;
        recipeService = RecipeService.getInstance(mContext);
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        mRecipes = recipeService.getRecipes().blockingFirst();

        if (mRecipes.size() > 10) {
            mRecipes = mRecipes.subList(0, 9);
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (mRecipes == null) return 0;
        return mRecipes.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        Recipe recipe = mRecipes.get(i);

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.recipe_widget_card);
        views.setTextViewText(R.id.tv_recipe_name, recipe.getName());
        Bundle extras = new Bundle();
        extras.putParcelable(RecipeDetailsActivity.RECIPE_DETAIL_KEY, recipe);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        views.setOnClickFillInIntent(R.id.iv_recipe, fillInIntent);
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
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
