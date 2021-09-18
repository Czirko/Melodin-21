package com.example.balazshollo.melodin.api.apiinterfaces;

import com.example.balazshollo.melodin.api.model.Category;
import com.example.balazshollo.melodin.api.model.DisplayDailyMenu;
import com.example.balazshollo.melodin.api.model.Pricer;
import com.example.balazshollo.melodin.api.model.Restaurant;
import com.example.balazshollo.melodin.api.model.RestaurantDisplay;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestaurantInterface {

    @GET("user/restaurants")
    Call<ArrayList<Restaurant>> restaurantsAll(@Header("Authorization") String userToken);


    // nem fog kelleni
    @GET("user/restaurants/{id}")
    Call<Restaurant> restaurantGetOne(@Path("id") String restaurantId, @Header("Authorization") String userToken);

   /* @GET("user/restaurants/{id}/pricers")
    Call<Pricer> pricerAll(@Path("id") Integer restaurantId,@Header("Authorization") String userToken);*/

   @GET("user/restaurants/{id}/pricers")
   Call<Object> pricerAll(@Path("id") Integer restaurantId,@Header("Authorization") String userToken);






    @GET("user/restaurants/{id}/displays")
    Call<List<RestaurantDisplay>> restaurantDisplay(@Path("id") String restaurantId, @Header("Authorization") String userToken);

    @GET("user/displays/{id}")
    Call<RestaurantDisplay> restaurantDisplayGetOne(@Path("id") String displayId);

    @GET("user/displays/{id}/categories")
    Call<Category> categoryGet(@Path("id") String displayId, @Header("Authorization") String userToken);



    /* // ez jó volt
    @GET("user/displays/{id}/daily-menus?") //@GET("user/displays/{id}/daily-menus?date=2019-01-03")
    Call<DisplayDailyMenu> showDisplayMenuAtDate(@Path("id") String displayId, @Query("date") String date);
*/

    @GET("/api/user/restaurants/{id}/daily-menus")
    Call<DisplayDailyMenu>getRestaurantDailyMenu(@Path("id")Integer restaurantId,@Header("Authorization") String userToken);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("displays/daily-menu/?") //@GET("user/displays/{id}/daily-menus?date=2019-01-03")
    Call<DisplayDailyMenu> showDisplayMenuAtDate(/*@Path("id") String displayId, */@Query("date") String date, @Header("Authorization") String authHeader);
}