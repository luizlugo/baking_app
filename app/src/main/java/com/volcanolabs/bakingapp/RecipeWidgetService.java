package com.volcanolabs.bakingapp;

import android.content.Intent;
import android.widget.RemoteViewsService;

import com.volcanolabs.bakingapp.adapters.GridRemoteViewsFactory;

public class RecipeWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewsFactory(this.getApplicationContext());
    }
}
