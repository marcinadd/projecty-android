package com.marcinadd.projecty.client;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AuthorizedNetworkClientImpl extends AuthorizedNetworkClient {
    private Context context;

    public AuthorizedNetworkClientImpl(Context context) {
        this.context = context;
    }

    @Override
    public String getToken() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString("access_token", "");
    }
}
