package com.example.balazshollo.melodin.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class MelodinAuthenticatInterceptor implements Interceptor {

    //String token = "8886fdee-37a9-419a-ad9e-2b730dcb8354"; //

    String token = "";

    public MelodinAuthenticatInterceptor(String token) {
        //this.token = token;
    }


    public void changeToken(String token) {
        this.token = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request authenticatiorRequest = request.newBuilder()
                .header("Authorization","Bearer " + token)
                .build();

        return chain.proceed(authenticatiorRequest);
    }
}
