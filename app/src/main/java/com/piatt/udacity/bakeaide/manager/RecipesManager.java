package com.piatt.udacity.bakeaide.manager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.piatt.udacity.bakeaide.R;
import com.piatt.udacity.bakeaide.model.Recipe;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class RecipesManager {
    private Context context;
    private RecipesApi recipesApi;
    private ConnectivityManager connectivityManager;

    public RecipesManager(Context context) {
        this.context = context;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RecipesApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        recipesApi = retrofit.create(RecipesApi.class);
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public Single<List<Recipe>> getRecipes() {
        if (isNetworkAvailable()) {
            return recipesApi.getRecipes()
                    .subscribeOn(Schedulers.io())
                    .flatMap(this::getRecipesWithImages);
        }
        return Single.just(Collections.singletonList(new Recipe()));
    }

    private Single<List<Recipe>> getRecipesWithImages(List<Recipe> recipes) {
        String[] recipeImageUrls = context.getResources().getStringArray(R.array.recipe_image_urls);

        return Observable.just(recipes)
                .flatMap(Observable::fromIterable)
                .doOnNext(recipe -> {
                    if (recipe.getId() < recipeImageUrls.length) {
                        recipe.setImage(recipeImageUrls[recipe.getId() - 1]);
                    }
                }).toList();
    }

    private boolean isNetworkAvailable() {
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    private interface RecipesApi {
        String BASE_URL = "http://go.udacity.com/";
        String RECIPES_URL = "android-baking-app-json";

        @GET(RECIPES_URL)
        Single<List<Recipe>> getRecipes();
    }
}