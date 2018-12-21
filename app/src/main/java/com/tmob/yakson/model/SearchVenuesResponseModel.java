package com.tmob.yakson.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SearchVenuesResponseModel {

    private SearchVenuesListResponseModel response;

    public SearchVenuesListResponseModel getResponse() {
        return response;
    }

    public class SearchVenuesListResponseModel {

        @SerializedName("venues")
        private ArrayList<VenueModel> venueList;

        public ArrayList<VenueModel> getVenueList() {
            return venueList;
        }
    }
}
