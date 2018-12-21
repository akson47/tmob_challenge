package com.tmob.yakson.viewcontroller.main;

import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.tmob.yakson.api.ApiRequest;
import com.tmob.yakson.api.factory.VenueFactory;
import com.tmob.yakson.model.SearchVenuesResponseModel;

import java.util.HashMap;

public class MainViewModel extends ViewModel {

    private MediatorLiveData<ApiRequest<SearchVenuesResponseModel>> searchVenuesRequestLiveData = new MediatorLiveData<>();

    public void searchVenues(HashMap<String, Object> params) {
        searchVenuesRequestLiveData.addSource(
                VenueFactory.getInstance().searchVenues(params),
                new Observer<ApiRequest<SearchVenuesResponseModel>>() {
                    @Override
                    public void onChanged(@Nullable ApiRequest<SearchVenuesResponseModel> apiRequest) {
                        searchVenuesRequestLiveData.setValue(apiRequest);
                    }
                }
        );
    }

    public MediatorLiveData<ApiRequest<SearchVenuesResponseModel>> getSearchVenuesRequestLiveData() {
        return searchVenuesRequestLiveData;
    }
}
