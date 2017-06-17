package com.piatt.udacity.bakeaide.manager;

import android.content.Context;
import android.support.annotation.StringRes;

import com.piatt.udacity.bakeaide.BakeAideApplication;
import com.piatt.udacity.bakeaide.R;
import com.piatt.udacity.bakeaide.model.FetchRecipesEvent;
import com.piatt.udacity.bakeaide.model.Recipe;

import java.util.List;
import java.util.concurrent.TimeUnit;

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
    private BehaviorSubject<FetchRecipesEvent> fetchRecipesEventBus;

    public RecipesManager(Context context) {
        this.context = context;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RecipesApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        recipesApi = retrofit.create(RecipesApi.class);
        fetchRecipesEventBus = BehaviorSubject.create();
    }

    public void fetchRecipes() {
        if (BakeAideApplication.getApp().isNetworkAvailable()) {
            recipesApi.getRecipes()
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(disposable -> postFetchRecipesEvent(new FetchRecipesEvent(true)))
                    .delay(3, TimeUnit.SECONDS)
                    .flatMap(this::getRecipesWithImages)
                    .subscribe(recipes -> {
                        if (recipes.isEmpty()) {
                            postFetchRecipesEvent(new FetchRecipesEvent(getString(R.string.empty_message)));
                        } else {
                            postFetchRecipesEvent(new FetchRecipesEvent(recipes));
                        }
                    }, error -> postFetchRecipesEvent(new FetchRecipesEvent(getString(R.string.error_message))));
        } else {
            postFetchRecipesEvent(new FetchRecipesEvent(getString(R.string.connection_message)));
        }
    }

    public Flowable<FetchRecipesEvent> onFetchRecipesEvent() {
        return fetchRecipesEventBus.toFlowable(BackpressureStrategy.LATEST);
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

    private void postFetchRecipesEvent(FetchRecipesEvent event) {
        if (!event.isSuccess() && fetchRecipesEventBus.hasValue()) {
            FetchRecipesEvent currentEvent = fetchRecipesEventBus.getValue();
            if (currentEvent.hasRecipes()) {
                event.setRecipes(currentEvent.getRecipes());
            }
        }
        fetchRecipesEventBus.onNext(event);
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