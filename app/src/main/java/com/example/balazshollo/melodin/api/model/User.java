package com.example.balazshollo.melodin.api.model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    int mId;

    @SerializedName("email")
    String email;

    @SerializedName("password")
    String password;

    public User(int id, String email, String password) {
        this.mId = id;
        this.email = email;
        this.password = password;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public int getmId() {
        return mId;
    }

    public String getEmail() {

        return email;
    }

    @Override
    public String toString() {
        return "User{" +
                "mId=" + mId +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
