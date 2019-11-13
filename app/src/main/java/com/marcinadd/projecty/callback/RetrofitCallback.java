package com.marcinadd.projecty.callback;

import com.marcinadd.projecty.listener.RetrofitListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitCallback<T> implements Callback<T> {
    private final RetrofitListener<T> listener;
    private String TAG;

    public RetrofitCallback(RetrofitListener<T> listener) {
        this.listener = listener;
    }

    public RetrofitCallback(RetrofitListener<T> listener, String TAG) {
        this.listener = listener;
        this.TAG = TAG;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful())
            listener.onResponseSuccess(response.body(), TAG);
        else
            listener.onResponseFailed(TAG);
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        listener.onResponseFailed(TAG);
    }
}
