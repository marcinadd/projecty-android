package com.marcinadd.projecty.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.annotation.Nullable;

import com.marcinadd.projecty.client.NetworkClient;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Call;
import retrofit2.Retrofit;

public class TokenAuthenticator implements Authenticator {
    private SharedPreferences sharedPreferences;

    public TokenAuthenticator(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Nullable
    @Override
    public Request authenticate(@Nullable Route route, Response response) throws IOException {
        String refreshToken = sharedPreferences.getString("refresh_token", null);
        Retrofit retrofit = NetworkClient.getRetrofitClient();
        AuthClient authClient = retrofit.create(AuthClient.class);
        if (response.code() == 401) {
            Call<Token> refreshCall = authClient.refresh("refresh_token", refreshToken);
            Token token = refreshCall.execute().body();
            sharedPreferences.edit().putString("refresh_token", Objects.requireNonNull(token).getRefreshToken()).apply();
            return response.request().newBuilder()
                    .header("Authorization", "Bearer " + token.getAccessToken())
                    .build();
        } else {
            return null;
        }
    }
}
