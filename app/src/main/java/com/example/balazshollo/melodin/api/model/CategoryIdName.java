package com.example.balazshollo.melodin.api.model;

import com.google.gson.annotations.SerializedName;

public class CategoryIdName {
    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String name;

    /*
    @Override
    public String toString() {
        return "CategoryIdName{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }*/
}
