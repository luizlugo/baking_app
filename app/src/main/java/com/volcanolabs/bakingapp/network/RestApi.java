package com.volcanolabs.bakingapp.network;

import com.volcanolabs.bakingapp.entities.Recipe;

import java.util.List;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface RestApi {
    @GET("topher/2017/May/59121517_baking/baking.json")
    Observable<List<Recipe>> getRecipes();
}
