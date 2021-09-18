package com.example.balazshollo.melodin.api.model;

import com.google.gson.annotations.SerializedName;

public class Member {
    @SerializedName("id")
    int mId;

    @SerializedName("email")
    String email;

    @SerializedName("firstName")
    String firstName;

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public Member(int mId, String email, String firstName) {
        this.mId = mId;
        this.email = email;
        this.firstName = firstName;
    }
}