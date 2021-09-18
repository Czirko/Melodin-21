package com.example.balazshollo.melodin.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SpecMenuIdFoods {
    @SerializedName("id")
    public int id;

    @SerializedName("foods")
    public List<Integer> foods;

    public SpecMenuIdFoods() {
        this.foods = new ArrayList<>();
    }
}
