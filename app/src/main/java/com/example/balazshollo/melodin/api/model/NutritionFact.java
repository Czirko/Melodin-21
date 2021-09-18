package com.example.balazshollo.melodin.api.model;

import com.google.gson.annotations.SerializedName;

public class NutritionFact {

    @SerializedName("name")
    public String name;

    @SerializedName("value")
    public String value;


    @Override
    public String toString() {
        return "NutritionFact{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
