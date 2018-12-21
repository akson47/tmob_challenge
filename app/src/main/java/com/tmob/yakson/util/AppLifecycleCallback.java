package com.tmob.yakson.util;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tmob.yakson.helper.ViewHelper;
import com.tmob.yakson.interfaces.ActivityInterface;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AppLifecycleCallback implements Application.ActivityLifecycleCallbacks {

    private final Map<Activity, Unbinder> unbinders = new HashMap<>();

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        boolean isInstanceOfActivityInterface = activity instanceof ActivityInterface;
        if(!isInstanceOfActivityInterface){
            return;
        }
        activity.setContentView(layoutId(activity));
        unbinders.put(activity, ButterKnife.bind(activity));
        App.getInstance().setCurrentActivity((AppCompatActivity) activity);
        ActivityInterface activityInterface = (ActivityInterface) activity;
        activityInterface.getDataFromIntent();
        activityInterface.initViews();
        activityInterface.initViewModel();
        activityInterface.initObservers();
        activityInterface.initActivity();
    }

    @Override
    public void onActivityStarted(Activity activity) {}

    @Override
    public void onActivityResumed(Activity activity) {
        App.getInstance().setCurrentActivity((AppCompatActivity) activity);
    }

    @Override
    public void onActivityPaused(Activity activity) {}

    @Override
    public void onActivityStopped(Activity activity) {
        ViewHelper.getInstance().hideKeyboard();
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {}

    @Override
    public void onActivityDestroyed(Activity activity) {
        if(unbinders.containsKey(activity)){
            unbinders.remove(activity).unbind();
        }
    }

    private int layoutId(Activity activity) {
        return ((ActivityInterface) activity).getLayoutId();
    }

}

