package com.example.balazshollo.melodin.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PriceChangeUpload {
    @SerializedName("restaurantId")
    public Integer restaurantId;

    @SerializedName("price")
    public Double price;

    @SerializedName("reducedPrice")
    public Double reducedPrice;
}