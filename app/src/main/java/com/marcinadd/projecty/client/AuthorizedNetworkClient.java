package com.marcinadd.projecty.client;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.marcinadd.projecty.helper.ServerHelper;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthorizedNetworkClient {
    private static Retrofit INSTANCE = null;

    public static Retrofit getRetrofitClient(final Context context) {
        if (INSTANCE == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient httpClient = MyOkHttpClient.getInstance(context).getClient();
            Gson gson = new GsonBuilder()
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
