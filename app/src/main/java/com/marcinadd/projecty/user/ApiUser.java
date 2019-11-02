package com.marcinadd.projecty.user;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiUser {
    @GET("/user/{username}/avatar")
    Call<ResponseBody> getUserAvatar(@Path("username") String username);
}
