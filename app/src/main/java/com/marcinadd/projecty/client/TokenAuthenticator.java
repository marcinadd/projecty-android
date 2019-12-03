package com.marcinadd.projecty.client;

import android.content.Context;

import androidx.annotation.Nullable;

import com.marcinadd.projecty.helper.ServerHelper;
import com.marcinadd.projecty.login.AuthClient;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Call;
import retrofit2.Retrofit;

public class TokenAuthenticator implements Authenticator {
    private Context mContext;

    TokenAuthenticator(Context context) {
        mContext = context;
    }

    @Nullable
    @Override
    public Request authenticate(@Nullable Route route, Response response) throws IOException {
        String refreshToken = TokenHelper.getRefreshToken(mContext);
        Retrofit retrofit = NetworkClient.getRetrofitClient(ServerHelper.getServerURL(mContext));
        AuthClient authClient = retrofit.create(AuthClient.class);
        if (response.code() == 401) {
            Call<Token> refreshCall = authClient.refresh("refresh_token", refreshToken);
            Token token = refreshCall.execute().body();
            if (token != null) {
                TokenHelper.saveToken(token, mContext);
            }
            return response.request().newBuilder()
                    .header("Authorization", "Bearer " + (token != null ? token.getAccessToken() : null))
                    .build();
        } else {
            return null;
        }
    }
}
