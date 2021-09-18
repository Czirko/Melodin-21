package com.example.balazshollo.melodin.api.model;

import com.google.gson.annotations.SerializedName;

public class Auth {
    @SerializedName("token")
    String token;

    @SerializedName("expireAt")
    String expireAt;

    public Auth(String token, String expireAt) {
        this.token = token;
        this.expireAt = expireAt;
    }

    public String getToken() {
        return token;
    }

    public String getExpireAt() {
        return expireAt;
    }
}
