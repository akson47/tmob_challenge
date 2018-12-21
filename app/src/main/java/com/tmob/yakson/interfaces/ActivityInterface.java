package com.tmob.yakson.interfaces;

public interface ActivityInterface {

    int getLayoutId();

    void getDataFromIntent();

    void initViews();

    void initViewModel();

    void initObservers();

    void initActivity();
}
