package com.piatt.udacity.bakeaide.manager;

import android.content.Context;
import android.support.annotation.StringRes;

import com.piatt.udacity.bakeaide.BakeAideApplication;
import com.piatt.udacity.bakeaide.R;
import com.piatt.udacity.bakeaide.model.FetchStatusEvent;
import com.piatt.udacity.bakeaide.model.Recipe;

import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class RecipesManager {
    private Context context;
    private RecipesApi recipesApi;
    private BehaviorSubject<FetchStatusEvent> fetchStatusEventBus;
    private BehaviorSubject<List<Recipe>> recipesEventBus;

    public RecipesManager(Context context) {
        this.context = context;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RecipesApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        recipesApi = retrofit.create(RecipesApi.class);
        fetchStatusEventBus = BehaviorSubject.create();
        recipesEventBus = BehaviorSubject.create();
    }

    public void fetchRecipes() {
        if (BakeAideApplication.getApp().isNetworkAvailable()) {
            recipesApi.getRecipes()
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(disposable -> fetchStatusEventBus.onNext(new FetchStatusEvent(true)))
                    .flatMap(this::getRecipesWithImages)
                    .subscribe(recipes -> {
                        if (recipes.isEmpty()) {
                            fetchStatusEventBus.onNext(new FetchStatusEvent(getString(R.string.empty_message)));
                        } else {
                            fetchStatusEventBus.onNext(new FetchStatusEvent(false));
                            recipesEventBus.onNext(recipes);
                        }
                    }, error -> fetchStatusEventBus.onNext(new FetchStatusEvent(getString(R.string.error_message))));
        } else {
            fetchStatusEventBus.onNext(new FetchStatusEvent(getString(R.string.connection_message)));
        }
    }

    public Flowable<FetchStatusEvent> onFetchStatusEvent() {
        return fetchStatusEventBus.toFlowable(BackpressureStrategy.LATEST);
    }

    public Flowable<List<Recipe>> getRecipes() {
        return recipesEventBus.toFlowable(BackpressureStrategy.LATEST);
    }

    /**
     * This method is only here to make the visual user experience nicer.
     * This method takes the list of recipes fetched from the network and adds pre-chosen image URLs,
     * since the predefined JSON has no image URLs attached to its recipes.
     */
    private Single<List<Recipe>> getRecipesWithImages(List<Recipe> recipes) {
        String[] recipeImageUrls = context.getResources().getStringArray(R.array.recipe_image_urls);

        return Observable.just(recipes)
                .flatMap(Observable::fromIterable)
                .doOnNext(recipe -> {
                    if (recipe.getId() <= recipeImageUrls.length) {
                        recipe.setImage(recipeImageUrls[recipe.getId() - 1]);
                    }
                }).toList();
    }

    private String getString(@StringRes int resourceId) {
        return context.getString(resourceId);
    }

    private interface RecipesApi {
        String BASE_URL = "http://go.udacity.com/";
        String RECIPES_ENDPOINT = "android-baking-app-json";

        @GET(RECIPES_ENDPOINT)
        Single<List<Recipe>> getRecipes();
    }
}