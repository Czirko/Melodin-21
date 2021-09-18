package com.example.balazshollo.melodin.api.apiinterfaces;

import com.example.balazshollo.melodin.api.model.Member;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface MemberInterface {
    @GET("user/me?")
    Call<Member> currentLoggedMember(@Header("Authorization") String userToken);
}

