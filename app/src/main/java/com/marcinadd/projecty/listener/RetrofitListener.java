package com.marcinadd.projecty.listener;

import androidx.annotation.Nullable;

public interface RetrofitListener<T> {
    void onResponseSuccess(T response, @Nullable String TAG);
    void onResponseFailed(@Nullable String TAG);
}
