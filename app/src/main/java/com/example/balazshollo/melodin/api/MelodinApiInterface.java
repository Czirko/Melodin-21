package com.example.balazshollo.melodin.api;

import com.example.balazshollo.melodin.api.model.Auth;
import com.example.balazshollo.melodin.api.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface MelodinApiInterface {

    @POST("user/login")
    Call<Auth> loginUser(@Body User user);

    //@GET("user/me")
    //Call<User> meUser(@Header("Authorization") String auth);

    @GET("user/me")
    Call<User> meUser();

}
