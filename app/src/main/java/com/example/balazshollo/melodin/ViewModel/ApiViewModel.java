package com.example.balazshollo.melodin.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.balazshollo.melodin.api.MelodinApi;
import com.example.balazshollo.melodin.api.model.DisplayDailyMenu;
import com.example.balazshollo.melodin.api.model.RestaurantDisplay;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ApiViewModel extends ViewModel {

    private MutableLiveData<DisplayDailyMenu> dailyMenu;

    public LiveData<DisplayDailyMenu> getDailyMenu(int restaurantId,String token) {
        if (dailyMenu == null) {
            dailyMenu = new MutableLiveData<DisplayDailyMenu>();
            loadMenu(restaurantId,token);
        }
        return dailyMenu;
    }

    private void loadMenu(int restaurantId,String token){

        Call<DisplayDailyMenu> menuCall=MelodinApi.getInstance().restaurant().getRestaurantDailyMenu(restaurantId,"Bearer "+token);
        menuCall.enqueue(new Callback<DisplayDailyMenu>() {
            @Override
            public void onResponse(Call<DisplayDailyMenu> call, retrofit2.Response<DisplayDailyMenu> response) {
                if(response.isSuccessful()){
                   dailyMenu.setValue(response.body());
                    Log.d("menuCall","Yeah");

                }
            }

            @Override
            public void onFailure(Call<DisplayDailyMenu> call, Throwable t) {
                Log.d("menuCall","baszhatod "+t);
            }
        });


    }





    //This method is using Retrofit to get the JSON data from URL

}
