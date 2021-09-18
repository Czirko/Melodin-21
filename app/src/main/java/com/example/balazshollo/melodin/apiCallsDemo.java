package com.example.balazshollo.melodin;

import android.util.Log;

import com.example.balazshollo.melodin.api.MelodinApi;
import com.example.balazshollo.melodin.api.model.Category;
import com.example.balazshollo.melodin.api.model.Restaurant;
import com.example.balazshollo.melodin.api.model.RestaurantDisplay;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class apiCallsDemo {

    /*
    //all restaurant
    Call<List<Restaurant>> restaurantCall = MelodinApi.getInstance().restaurant().restaurantsAll();
        restaurantCall.enqueue(new Callback<List<Restaurant>>() {
        @Override
        public void onResponse(Call<List<Restaurant>> call, retrofit2.Response<List<Restaurant>> response) {
            if(response.isSuccessful()){
                List<Restaurant> restaurantList = response.body();

                for (int i = 0; i<restaurantList.size(); i++){
                    Log.d("testTag",restaurantList.get(i).toString());
                }
            }
        }

        @Override
        public void onFailure(Call<List<Restaurant>> call, Throwable t) {
            Log.d("testTag", "resturant error");
        }
    });
    */

    /*
    //slecet one restaurant with id
    Call<Restaurant> restaurantCall = MelodinApi.getInstance().restaurant().restaurantGetOne("2");
        restaurantCall.enqueue(new Callback<Restaurant>() {
        @Override
        public void onResponse(Call<Restaurant> call, retrofit2.Response<Restaurant> response) {
            if(response.isSuccessful()){
                Restaurant body = response.body();

                Log.d("testTag",body.toString());
            }
        }

        @Override
        public void onFailure(Call<Restaurant> call, Throwable t) {
            Log.d("testTag", "resturant error");
        }
    });
        */

    /*
    // resturant display from selected resturant by id
    Call<List<RestaurantDisplay>> listCall = MelodinApi.getInstance().restaurant().restaurantDisplay("2");
        listCall.enqueue(new Callback<List<RestaurantDisplay>>() {
        @Override
        public void onResponse(Call<List<RestaurantDisplay>> call, retrofit2.Response<List<RestaurantDisplay>> response) {
            if(response.isSuccessful()){
                List<RestaurantDisplay> restaurantList = response.body();

                for (int i = 0; i<restaurantList.size(); i++){
                    Log.d("testTag",restaurantList.get(i).toString());
                }
            }
        }

        @Override
        public void onFailure(Call<List<RestaurantDisplay>> call, Throwable t) {
            Log.d("testTag", "resturant error");
        }
    });
    */

    /*
    //get one restaurant display
    Call<RestaurantDisplay> restaurantDisplayCall = MelodinApi.getInstance().restaurant().restaurantDisplayGetOne("10");
        restaurantDisplayCall.enqueue(new Callback<RestaurantDisplay>() {
        @Override
        public void onResponse(Call<RestaurantDisplay> call, retrofit2.Response<RestaurantDisplay> response) {
            if(response.isSuccessful()){
                RestaurantDisplay body = response.body();

                Log.d("testTag",body.toString());
            }
        }

        @Override
        public void onFailure(Call<RestaurantDisplay> call, Throwable t) {
            Log.d("testTag", "resturant error");
        }
    });
        */


    /*
    //display categories
    Call<Category> categoryCall = MelodinApi.getInstance().restaurant().categoryGet("11");
        categoryCall.enqueue(new Callback<Category>() {
        @Override
        public void onResponse(Call<Category> call, retrofit2.Response<Category> response) {
            if(response.isSuccessful()){
                Category body = response.body();

                Log.d("testTag",body.toString());
            }
        }

        @Override
        public void onFailure(Call<Category> call, Throwable t) {
            Log.d("testTag", "resturant error");
        }
    });
*/


}


