package com.marcinadd.projecty.client;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.marcinadd.projecty.login.TokenAuthenticator;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.marcinadd.projecty.client.NetworkClient.BASE_URL;

public class AuthorizedNetworkClient {
    public static Retrofit INSTANCE = null;

    private static String getToken(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("access_token", "");
    }

    public static Retrofit getRetrofitClient(final Context context) {
        if (INSTANCE == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request.Builder ongoing = chain.request().newBuilder();
                            ongoing.addHeader("Authorization", "Bearer " + getToken(context));
                            return chain.proceed(ongoing.build());
                        }
                    })
                    .authenticator(new TokenAuthenticator(context))
                    .build();

            INSTANCE = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient)
                    .build();
        }
        return INSTANCE;
    }
}
