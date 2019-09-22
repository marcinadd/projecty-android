package com.marcinadd.projecty;

import com.marcinadd.projecty.login.Token;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.marcinadd.projecty.client.NetworkClient.BASE_URL;

public class MockAuthorizedNetworkClient {
    //    TODO MAke MockAuthorizedNetworkClient singleton
    private Token token;

    public MockAuthorizedNetworkClient(Token token) {
        this.token = token;
    }

    public Retrofit getRetrofitClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request.Builder ongoing = chain.request().newBuilder();
                        ongoing.addHeader("Authorization", "Bearer " + getToken());
                        return chain.proceed(ongoing.build());
                    }
                })
                .build();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
    }

    protected String getToken() {
        return token.getAccessToken();
    }
}
