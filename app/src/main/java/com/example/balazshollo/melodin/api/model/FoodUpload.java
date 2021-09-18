package com.example.balazshollo.melodin.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class FoodUpload {

    @SerializedName("foodIds")
    public List<Integer> foodIds;

    @SerializedName("specMenus")
    public List<SpecMenuIdFoods> specMenus;

    public FoodUpload() {
        this.foodIds = new ArrayList<> ();
        this.specMenus = new ArrayList<>();

    }
}
