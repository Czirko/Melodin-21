package com.example.balazshollo.melodin.api.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Food implements Serializable {
    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String name;
/*
    @SerializedName("category")
    public String category;*/

    @SerializedName("media")
    public String media;

    @SerializedName("priceSmallName")
    public String priceSmallName;

    @SerializedName("defaultPriceSmall")
    public double defaultPriceSmall;

    @SerializedName("priceNormalName")
    public String priceNormalName;

    @SerializedName("defaultPriceNormal")
    public int defaultPriceNormal;

    @SerializedName("description")
    public String description;

    @SerializedName("foodProperties")
    public List<FoodProperty> foodProperties;

    @SerializedName("nutritionFacts")
    public List<NutritionFact> nutritionFacts;

    @SerializedName("ingredients")
    public List<String> ingredients;

/*
    @Override
    public String toString() {
        return "Food{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", media='" + media + '\'' +
                ", priceSmallName='" + priceSmallName + '\'' +
                ", defaultPriceSmall=" + defaultPriceSmall +
                ", priceNormalName='" + priceNormalName + '\'' +
                ", defaultPriceNormal=" + defaultPriceNormal +
                ", description='" + description + '\'' +
                ", foodProperties=" + foodProperties.toString() +
                ", nutritionFacts=" + nutritionFacts.toString() +
                ", ingredients=" + ingredients.toString() +
                '}';
    }*/

    @Override
    public String toString() {
        return name;
    }
}
