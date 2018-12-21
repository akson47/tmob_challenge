package com.tmob.yakson.viewcontroller.splash;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.tmob.michallange.R;
import com.tmob.yakson.interfaces.ActivityInterface;
import com.tmob.yakson.viewcontroller.main.MainActivity;

public class SplashActivity extends AppCompatActivity implements ActivityInterface {

    private SplashViewModel splashViewModel;

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash_activity;
    }

    @Override
    public void getDataFromIntent() {

    }

    @Override
    public void initViews() {

    }

    @Override
    public void initViewModel() {
        splashViewModel = ViewModelProviders.of(this).get(SplashViewModel.class);
    }

    @Override
    public void initObservers() {

    }

    @Override
    public void initActivity() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, 2000);
    }

}
