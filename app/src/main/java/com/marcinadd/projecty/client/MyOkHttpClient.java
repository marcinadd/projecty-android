package com.marcinadd.projecty.client;

import android.content.Context;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MyOkHttpClient {
    private static MyOkHttpClient instance;
    private OkHttpClient client;

    private MyOkHttpClient(Context context) {
        client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request.Builder ongoing = chain.request().newBuilder();
                    try {
                        ongoing.addHeader("Authorization", "Bearer " + TokenHelper.getAccessToken(context));
                    } catch (BlankTokenException e) {
                        e.printStackTrace();
                    }
                    return chain.proceed(ongoing.build());
                })
                .authenticator(new TokenAuthenticator(context))
                .build();
    }

    public static MyOkHttpClient getInstance(Context context) {
        if (instance == null) {
            instance = new MyOkHttpClient(context);
        }
        return instance;
    }

    public OkHttpClient getClient() {
        return client;
    }
}
