package com.marcinadd.projecty.login;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AuthClient {
    @Headers("Authorization: Basic Y2xpZW50SWQ6Y2xpZW50U2VjcmV0")
    @POST("oauth/token")
    @FormUrlEncoded
    Call<Token> getToken(
            @Field("grant_type") String grantType,
            @Field("username") String username,
            @Field("password") String password
    );

    @GET("auth")
    Call<LoggedInUser> getLoggedInUser(@Header("Authorization") String authToken);

}
