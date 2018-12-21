package com.tmob.yakson.model;

import com.google.gson.annotations.SerializedName;

public class GetVenueDetailsResponseModel {

    private VenueDetailsResponseModel response;

    public VenueDetailsResponseModel getResponse() {
        return response;
    }

    public class VenueDetailsResponseModel {

        @SerializedName("venue")
        private VenueModel venueModel;

        public VenueModel getVenueModel() {
            return venueModel;
        }
    }
}
