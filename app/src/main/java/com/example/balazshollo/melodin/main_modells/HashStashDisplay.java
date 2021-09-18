package com.example.balazshollo.melodin.main_modells;

import com.example.balazshollo.melodin.api.model.Food;
import com.example.balazshollo.melodin.api.model.MenuItemWithFoods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HashStashDisplay {
    public HashMap<Integer,List<Food>> innerHashMap;// = new HashMap<>();

    //kategoriák neve
    public List<MenuItemWithFoods> categories;// = new ArrayList<>();


    //categoria tipusa special vagy nem (special vagy carte)
    public boolean isSpecial = false;


    public HashStashDisplay() {
        this.innerHashMap = new HashMap<>();
        this.categories = new ArrayList<>();
    }

    /*public HashStashDisplay(HashMap<Integer, List<Food>> innerHashMap, List<MenuItemWithFoods> categories) {
        this.innerHashMap = innerHashMap;
        this.categories =
    }*/
}
