package com.marcinadd.projecty.client;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.marcinadd.projecty.helper.ServerHelper;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthorizedNetworkClient {
    private static Retrofit INSTANCE = null;

    public static Retrofit getRetrofitClient(final Context context) {
        if (INSTANCE == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request.Builder ongoing = chain.request().newBuilder();
                            ongoing.addHeader("Authorization", "Bearer " + TokenHelper.getAccessToken(context));
                            return chain.proceed(ongoing.build());
                        }
                    })
                    .authenticator(new TokenAuthenticator(context))
                    .build();
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd")
                    .create();
            INSTANCE = new Retrofit.Builder()
                    .baseUrl(ServerHelper.getServerURL(context))
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(httpClient)
                    .build();
        }
        return INSTANCE;
    }
}
