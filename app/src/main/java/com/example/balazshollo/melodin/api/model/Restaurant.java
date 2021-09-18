package com.example.balazshollo.melodin.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Restaurant {

    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String name;

    @SerializedName("slug")
    public String slug;

    @SerializedName("street")
    public String street;

    @SerializedName("city")
    public String city;

    @SerializedName("postalCode")
    public String postalCode;

    @SerializedName("country")
    public String country;

    @SerializedName("contactName")
    public String contactName;

    @SerializedName("contactPhone")
    public String contactPhone;

    @SerializedName("contactEmail")
    public String contactEmail;

    @SerializedName("status")
    public boolean status;

    @SerializedName("createdAt")
    public Date createdAt;

    @SerializedName("updatedAt")
    public Date updatedAt;

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", slug='" + slug + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", country='" + country + '\'' +
                ", contactName='" + contactName + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    public String getLocation(){
        return postalCode+" "+ city+", "+street;
    }

}
