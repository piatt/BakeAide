package com.piatt.udacity.bakeaide;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.piatt.udacity.bakeaide.manager.RecipesManager;

import lombok.Getter;

public class BakeAideApplication extends Application {
    private ConnectivityManager connectivityManager;
    @Getter RecipesManager recipesManager;
    @Getter static BakeAideApplication app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        recipesManager = new RecipesManager(getApplicationContext());
    }

    public boolean isNetworkAvailable() {
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}