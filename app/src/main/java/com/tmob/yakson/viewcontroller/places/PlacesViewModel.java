package com.tmob.yakson.viewcontroller.places;

import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.tmob.yakson.api.ApiRequest;
import com.tmob.yakson.api.factory.VenueFactory;
import com.tmob.yakson.model.GetVenueDetailsResponseModel;

public class PlacesViewModel extends ViewModel {

    private MediatorLiveData<ApiRequest<GetVenueDetailsResponseModel>> venueDetailsRequestLiveData = new MediatorLiveData<>();

    public void getVenueDetails(String venueId) {
        venueDetailsRequestLiveData.addSource(
                VenueFactory.getInstance().getVenueDetails(venueId),
                new Observer<ApiRequest<GetVenueDetailsResponseModel>>() {
                    @Override
                    public void onChanged(@Nullable ApiRequest<GetVenueDetailsResponseModel> apiRequest) {
                        venueDetailsRequestLiveData.setValue(apiRequest);
                    }
                }
        );
    }

    public MediatorLiveData<ApiRequest<GetVenueDetailsResponseModel>> getVenueDetailsRequestLiveData() {
        return venueDetailsRequestLiveData;
    }
}
