package com.tmob.yakson.api.factory;

import android.arch.lifecycle.MutableLiveData;

import com.tmob.yakson.api.ApiRequest;
import com.tmob.yakson.api.ApiResponse;
import com.tmob.yakson.api.ApiService;
import com.tmob.yakson.model.GetVenueDetailsResponseModel;
import com.tmob.yakson.model.SearchVenuesResponseModel;
import com.tmob.yakson.util.AppConstant;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VenueFactory {

    private static VenueFactory venueFactory;

    public static VenueFactory getInstance(){
        if(venueFactory == null){
            venueFactory = new VenueFactory();
        }
        return venueFactory;
    }

    public MutableLiveData<ApiRequest<SearchVenuesResponseModel>> searchVenues(HashMap<String, Object> params){
        final MutableLiveData<ApiRequest<SearchVenuesResponseModel>> apiResponse = new MutableLiveData<>();
        apiResponse.setValue(new ApiRequest<SearchVenuesResponseModel>(true));
        ApiService
                .apiInterface
                .searchVenues(params)
                .enqueue(new Callback<SearchVenuesResponseModel>() {
                    @Override
                    public void onResponse(Call<SearchVenuesResponseModel> call, Response<SearchVenuesResponseModel> response) {
                        apiResponse.setValue(new ApiRequest<SearchVenuesResponseModel>(false));
                        apiResponse.setValue(new ApiRequest<>(new ApiResponse<>(response)));
                    }

                    @Override
                    public void onFailure(Call<SearchVenuesResponseModel> call, Throwable t) {
                        apiResponse.setValue(new ApiRequest<SearchVenuesResponseModel>(false));
                        apiResponse.setValue(new ApiRequest<>(new ApiResponse<SearchVenuesResponseModel>(t)));
                    }
                });
        return apiResponse;
    }

    public MutableLiveData<ApiRequest<GetVenueDetailsResponseModel>> getVenueDetails(String venueId){

        final MutableLiveData<ApiRequest<GetVenueDetailsResponseModel>> apiResponse = new MutableLiveData<>();
        apiResponse.setValue(new ApiRequest<GetVenueDetailsResponseModel>(true));

        HashMap<String, Object> params = new HashMap<>();
        params.put("client_id", AppConstant.FOURSQUARE_CLIENT_ID);
        params.put("client_secret", AppConstant.FOURSQUARE_CLIENT_SECRET);
        params.put("v", AppConstant.FOURSQUARE_QUERY_PARAM_V);

        ApiService
                .apiInterface
                .getVenueDetails(venueId, params)
                .enqueue(new Callback<GetVenueDetailsResponseModel>() {
                    @Override
                    public void onResponse(Call<GetVenueDetailsResponseModel> call, Response<GetVenueDetailsResponseModel> response) {
                        apiResponse.setValue(new ApiRequest<GetVenueDetailsResponseModel>(false));
                        apiResponse.setValue(new ApiRequest<>(new ApiResponse<>(response)));
                    }

                    @Override
                    public void onFailure(Call<GetVenueDetailsResponseModel> call, Throwable t) {
                        apiResponse.setValue(new ApiRequest<GetVenueDetailsResponseModel>(false));
                        apiResponse.setValue(new ApiRequest<>(new ApiResponse<GetVenueDetailsResponseModel>(t)));
                    }
                });
        return apiResponse;
    }
}
