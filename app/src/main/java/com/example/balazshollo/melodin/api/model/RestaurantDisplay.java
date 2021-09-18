package com.example.balazshollo.melodin.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class RestaurantDisplay {

    @SerializedName("id")
    public int id;

    @SerializedName("accessToken")
    public String accessToken;

    @SerializedName("name")
    public String name;

    @SerializedName("type")
    public String type;

    @SerializedName("contents")
    public List<Integer> contents;

    @SerializedName("createdAt")
    public Date createdAt;

    @SerializedName("updatedAt")
    public Date updatedAt;

    @SerializedName("restaurantId")
    public int restaurantId;

    /*
    @Override
    public String toString() {
        return "RestaurantDisplay{" +
                "id=" + id +
                ", accessToken='" + accessToken + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", contents=" + contents.toString() +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", restaurantId=" + restaurantId +
                '}';
    }*/
}
