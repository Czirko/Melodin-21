package com.example.balazshollo.melodin.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class MenuItemWithFoods {

    //közös

    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String name;

    @SerializedName("foods")
    public List<Food> foods;

    //közös


    // special type

    @SerializedName("priority")
    public int priority;

    @SerializedName("media")
    public String media;

    @SerializedName("date")
    public Date date;

    @SerializedName("price")
    public int price;

    @SerializedName("reducedPrice")
    public int reducedPrice;

    // special type



    // carte type

    @SerializedName("created_at")
    public Date created_at;

    @SerializedName("updated_at")
    public Date updated_at;

    @SerializedName("image")
    public String image;

    @SerializedName("priceSmallName")
    public String priceSmallName;

    @SerializedName("priceNormalName")
    public String priceNormalName;


    // carte type



/*

    @Override
    public String toString() {
        return "MenuItemWithFoods{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                ", image='" + image + '\'' +
                ", priceSmallName='" + priceSmallName + '\'' +
                ", priceNormalName='" + priceNormalName + '\'' +
                ", foods=" + foods.toString() +
                '}';
    }*/
}
