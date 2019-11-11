package com.marcinadd.projecty.callback;

import com.marcinadd.projecty.listener.RetrofitListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//TODO Replace this class
public class VoidCallbackWithRetrofitListener implements Callback<Void> {
    private final RetrofitListener listener;

    public VoidCallbackWithRetrofitListener(RetrofitListener listener) {
        this.listener = listener;
    }

    @Override
    public void onResponse(Call<Void> call, Response<Void> response) {
        if (response.isSuccessful()) {
            listener.onResponseSuccess();
        } else {
            listener.onResponseFailed();
        }
    }

    @Override
    public void onFailure(Call<Void> call, Throwable t) {
        listener.onResponseFailed();
    }
}
