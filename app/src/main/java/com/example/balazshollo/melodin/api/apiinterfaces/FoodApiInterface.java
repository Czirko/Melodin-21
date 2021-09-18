package com.example.balazshollo.melodin.api.apiinterfaces;

import com.example.balazshollo.melodin.api.model.DisplayDailyMenu;
import com.example.balazshollo.melodin.api.model.Food;
import com.example.balazshollo.melodin.api.model.FoodAutoComplete;
import com.example.balazshollo.melodin.api.model.FoodUpload;
import com.example.balazshollo.melodin.api.model.PriceChangeUpload;
import com.example.balazshollo.melodin.api.model.PricerUpload;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FoodApiInterface {

    //@GET("user/restaurants/{id}/foods-autocomplete?name={text}")
    @GET("user/restaurants/{id}/foods-autocomplete?")
    Call<List<FoodAutoComplete>> autoCompleteFood(@Path("id") String restaurantId, @Query("name") String foodText, @Header("Authorization") String userToken,@Header("Accept-Language") String lang);

    @PUT("user/restaurants/{resturant_id}/daily-menus/{daily_menuid}")
    Call<Void> foodChanges(@Path("resturant_id") Integer restaurantId, @Header("Authorization") String userToken, @Header("Accept-Language") String lang, @Body FoodUpload foodUpload/*@Body String foodUpload*/, @Path("daily_menuid") Integer dailyMenuId, @Header("Content-Type") String contenttype, @Header("Accept") String accept);

    @PUT("/api/user/restaurants/{resturant_id}/pricers")
    Call<Object> pricerUpdate(@Path("resturant_id") Integer restaurantId,@Header("Content-Type") String contenttype, @Header("Authorization") String userToken,  @Body JSONObject pricerUpload);


    @PATCH("user/foods/{food_id}/update-prices")
    Call<Void> foodPriceChanges(@Path("food_id") Integer foodId, @Header("Authorization") String userToken, @Body PriceChangeUpload priceChangeUpload);



}
