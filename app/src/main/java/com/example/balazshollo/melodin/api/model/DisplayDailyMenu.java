package com.example.balazshollo.melodin.api.model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class DisplayDailyMenu {

    @SerializedName("dailyMenuId")
    public int dailyMenuId;

    /*
    @SerializedName("display")
    public RestaurantDisplay display;*/

    @SerializedName("date")
    public Date date;

    /*@SerializedName("locale")
    public String locale;*/

    @SerializedName("data")
    @Expose
    public List<MenuItemWithFoods> data;


    @SerializedName("specialData")
    public List<MenuItemWithFoods> specialData;

    @Expose(serialize = false, deserialize = false)
    public String customType;

/*
    "data":[
    {
        "id": 2,
            "created_at": "2018-08-06T21:22:00+02:00",
            "updated_at": "2018-08-06T21:22:00+02:00",
            "image": "/assets/media/levesek.jpg",
            "name": "Levesek",
            "priceSmallName": "ár/dl",
            "priceNormalName": "Tányér",
            "foods": [
        {
            "id": 3,
                "name": "Narancsos görög gyümölcsleves",
                "category": {
            "name": "Levesek",
                    "translations": [
            {
                "name": "Soups",
                    "locale": "en"
            },
            {
                "name": "Levesek",
                    "locale": "hu"
            }
                        ]
        },
                    ]




        "specialData": [
        {
            "id": 6,
                "name": "Tudatosoknak",
                "priority": null,
                "media": "/assets/media/levesek.jpg",
                "date": "2018-10-31T16:55:59+01:00",
                "price": 1000,
                "reducedPrice": 500,
                "translations": [
            {
                "name": "Tudatosoknak",
                    "locale": "en"
            },
            {
                "name": "Tudatosoknak",
                    "locale": "hu"
            }
            ],
            "foods": [
            {
                "id": 16,
                    "name": "Jázmin rizs",
                    "category": {
                    */
/*

    @Override
    public String toString() {
        return "DisplayDailyMenu{" +
                "dailyMenuId=" + dailyMenuId +
                ", display=" + display.toString() +
                ", date=" + date +
                ", locale='" + locale + '\'' +
                ", data=" + data.toString() +
                '}';
    }


*/


}
