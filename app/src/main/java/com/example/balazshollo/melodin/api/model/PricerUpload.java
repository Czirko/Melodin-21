package com.example.balazshollo.melodin.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PricerUpload {

    @SerializedName("foodIds")
    public List<Integer> foodIds;

    public PricerUpload(List<Integer> foodIds) {
        this.foodIds = new ArrayList<>();
    }
}
