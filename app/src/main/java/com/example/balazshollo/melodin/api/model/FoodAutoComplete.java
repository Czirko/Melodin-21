package com.example.balazshollo.melodin.api.model;

import com.google.gson.annotations.SerializedName;

public class FoodAutoComplete {
    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String name;

    @SerializedName("category")
    public FoodAutoCompleteCategory category;


    // name just because in autocomplete textview show real name
    @Override
    public String toString() {
        return name;
    }
}
