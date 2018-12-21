package com.tmob.yakson.api;

import com.tmob.yakson.model.GetVenueDetailsResponseModel;
import com.tmob.yakson.model.SearchVenuesResponseModel;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface ApiInterface {

    // VenueFactory
    @GET(ApiEndPoints.LN_API_SEARCH_VENUES)
    Call<SearchVenuesResponseModel> searchVenues(@QueryMap HashMap<String, Object> body);

    @GET(ApiEndPoints.LN_API_GET_VENUE_DETAILS + "{venueId}")
    Call<GetVenueDetailsResponseModel> getVenueDetails(@Path("venueId") String venueId,
                                                       @QueryMap HashMap<String, Object> body);

}
