package com.tmob.yakson.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class VenueModel implements Serializable {

    private String id;
    private String name;
    private VenueLocationModel location;
    private VenueCategoryModel venueCategoryModel;
    private String description;
    @SerializedName("bestPhoto")
    private VenuePhotoModel venuePhotoModel;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public VenueLocationModel getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public VenueCategoryModel getVenueCategoryModel() {
        return venueCategoryModel;
    }

    public VenuePhotoModel getVenuePhotoModel() {
        return venuePhotoModel;
    }

    public class VenueLocationModel implements Serializable {

        private String address;
        private String crossStreet;
        private double lat;
        private double lng;
        private double distance;
        private String postalCode;
        private String cc;
        private String city;
        private String state;
        private String country;
        private ArrayList<String> formattedAddress;

        public String getAddress() {
            return address;
        }

        public String getCrossStreet() {
            return crossStreet;
        }

        public double getLat() {
            return lat;
        }

        public double getLng() {
            return lng;
        }

        public double getDistance() {
            return distance;
        }

        public String getPostalCode() {
            return postalCode;
        }

        public String getCc() {
            return cc;
        }

        public String getCity() {
            return city;
        }

        public String getState() {
            return state;
        }

        public String getCountry() {
            return country;
        }

        public ArrayList<String> getFormattedAddress() {
            return formattedAddress;
        }
    }

    public class VenueCategoryModel implements Serializable {

        private String id;
        private String name;
        private String pluralName;
        private String shortName;
        private double distance;
        private String postalCode;
        private String cc;
        private String city;
        private String state;
        private String country;
        private ArrayList<String> formattedAddress;

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getPluralName() {
            return pluralName;
        }

        public String getShortName() {
            return shortName;
        }

        public double getDistance() {
            return distance;
        }

        public String getPostalCode() {
            return postalCode;
        }

        public String getCc() {
            return cc;
        }

        public String getCity() {
            return city;
        }

        public String getState() {
            return state;
        }

        public String getCountry() {
            return country;
        }

        public ArrayList<String> getFormattedAddress() {
            return formattedAddress;
        }
    }

    public class VenuePhotoModel implements Serializable {

        private String prefix;
        private String suffix;
        private int width;
        private int height;

        public String getImageUrl(){
            return prefix + width + "x" + height + suffix;
        }
    }
}
