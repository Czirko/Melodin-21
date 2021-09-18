package com.example.balazshollo.melodin.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Category {
    @SerializedName("assigned")
    public List<CategoryIdName> assigned;

    @SerializedName("available")
    public List<CategoryIdName> available;

/*
    @Override
    public String toString() {
        return "Category{" +
                "assigned=" + assigned +
                ", available=" + available +
                '}';
    }*/
}
