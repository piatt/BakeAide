package com.piatt.udacity.bakeaide;

import com.piatt.udacity.bakeaide.model.Recipe;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class RecipesManager {
    private RecipesApi recipesApi;

    public RecipesManager() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RecipesApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        recipesApi = retrofit.create(RecipesApi.class);
    }

    public Flowable<List<Recipe>> getRecipes() {
        return recipesApi.getRecipes().subscribeOn(Schedulers.io());
    }

    private interface RecipesApi {
        String BASE_URL = "http://go.udacity.com/";
        String RECIPES_URL = "android-baking-app-json";

        @GET(RECIPES_URL)
        Flowable<List<Recipe>> getRecipes();
    }
}