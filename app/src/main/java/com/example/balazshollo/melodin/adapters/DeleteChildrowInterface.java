package com.example.balazshollo.melodin.adapters;

import com.example.balazshollo.melodin.api.model.Food;

public interface DeleteChildrowInterface {
    //  menuOrNormal ha true akkor menüböl kell törölni, false esetén sima kajákból
    void deleteOneFood(Boolean menuOrNormal, int WhichDisplay, int category, int subelem);

    void deleteFoodFromDailyMenu(Boolean isMenu, int categoryId, int foodId);

    void changeFoodPrices(Food food);
}
