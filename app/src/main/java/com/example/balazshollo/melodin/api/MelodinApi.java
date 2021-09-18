package com.example.balazshollo.melodin.api;



import com.example.balazshollo.melodin.api.apiinterfaces.FoodApiInterface;
import com.example.balazshollo.melodin.api.apiinterfaces.MemberInterface;
import com.example.balazshollo.melodin.api.apiinterfaces.RestaurantInterface;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.SharedPreferences;

public class MelodinApi {
    public static final String API_URL = "https://melodin-server.iworkshop.hu/api/";
    private static MelodinApi mIstance;
    private Retrofit retrofit;
    private  OkHttpClient okHttpClient;

    public String token = "";

    private MelodinAuthenticatInterceptor melodinAuthenticatInterceptor;

    public void changeToken(String token){
        this.token = token;
        melodinAuthenticatInterceptor.changeToken(token);
    }

    private MelodinApi(){

       // String token = preferences.getString("token","");
       // SharedPreferences preferences = getSharedPreferences("TOKKEN_PREF", MODE_PRIVATE);
        /*okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new MelodinAuthenticatInterceptor(token))
                .build();*/

        melodinAuthenticatInterceptor = new MelodinAuthenticatInterceptor(token);
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(melodinAuthenticatInterceptor)
                .build();



        retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())


                //.client(okHttpClient)
                .build();
    }

    public static synchronized MelodinApi getInstance(){
        if (mIstance == null){
            mIstance = new MelodinApi();
        }
        return mIstance;
    }

    public MelodinApiInterface getApi(){
        return retrofit.create(MelodinApiInterface.class);
    }

    public RestaurantInterface restaurant(){
        return retrofit.create(RestaurantInterface.class);
    }


    public FoodApiInterface foods(){
        return retrofit.create(FoodApiInterface.class);
    }

    public MemberInterface member(){
        return retrofit.create(MemberInterface.class);
    }


    /*
    //private OkHttpClient okHttpClient;
    private static Retrofit retrofit = null;

    public static Retrofit getRetrofitInstance(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(
                        new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                Request original = chain.request();
                                Request.Builder requestbuilder = original.newBuilder()
                                        .addHeader("Authorization: Bearer ", token)
                                        .method(original.method(), original.body());
                                Request request = requestbuilder.build();
                                return chain.proceed(request);
                            }
                        }
                        ).build();

        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }*/


}
