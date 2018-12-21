package com.tmob.yakson.util;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;

import com.crashlytics.android.Crashlytics;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import io.fabric.sdk.android.Fabric;

public class App extends Application {

    private static App instance;
    private AppCompatActivity currentActivity;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        registerActivityLifecycleCallbacks(new AppLifecycleCallback());
        //initFabric();
        initPicasso();
    }

    public static App getInstance(){
        return instance;
    }

    public AppCompatActivity getCurrentActivity() {
        return currentActivity;
    }

    public void setCurrentActivity(AppCompatActivity activity){
        currentActivity = activity;
    }

    private void initFabric(){
        Fabric.with(this, new Crashlytics());
    }

    private void initPicasso(){
        Picasso picasso =  new Picasso.Builder(this).downloader(new OkHttp3Downloader(getCacheDir())).build();
        Picasso.setSingletonInstance(picasso);
    }
}