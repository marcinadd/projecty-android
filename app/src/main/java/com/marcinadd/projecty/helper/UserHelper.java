package com.marcinadd.projecty.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.marcinadd.projecty.client.AuthorizedNetworkClient;
import com.marcinadd.projecty.project.model.User;
import com.marcinadd.projecty.user.ApiUser;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserHelper {

    public static String getCurrentUserUsername(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("username", "");
    }

    public static Long getCurrentUserId(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return Long.parseLong(Objects.requireNonNull(sharedPreferences.getString("user_id", "")));
    }

    public static void setCurrentUserId(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Retrofit retrofit = AuthorizedNetworkClient.getRetrofitClient(context);
        ApiUser apiUser = retrofit.create(ApiUser.class);
        apiUser.getUser().enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
//                    if (response.isSuccessful())
                sharedPreferences.edit().putString("user_id", String.valueOf(response.body().getId())).apply();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

}
