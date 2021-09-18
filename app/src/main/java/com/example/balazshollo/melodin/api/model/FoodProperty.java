package com.example.balazshollo.melodin.api.model;

import com.google.gson.annotations.SerializedName;

public class FoodProperty {
    @SerializedName("name")
    public String name;

    @SerializedName("media")
    public String media;

    @SerializedName("status")
    public int status;


    @Override
    public String toString() {
        return "FoodProperty{" +
                "name='" + name + '\'' +
                ", media='" + media + '\'' +
                ", status=" + status +
                '}';
    }
}
