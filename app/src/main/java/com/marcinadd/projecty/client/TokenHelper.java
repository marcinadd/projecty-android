package com.marcinadd.projecty.client;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class TokenHelper {
    private static final String ACCESS_TOKEN = "access_token";
    private static final String REFRESH_TOKEN = "refresh_token";

    public static void saveToken(Token token, Context context) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putString(ACCESS_TOKEN, token.getAccessToken()).apply();
        sharedPreferences.edit().putString(REFRESH_TOKEN, token.getRefreshToken()).apply();
    }

    static String getAccessToken(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(ACCESS_TOKEN, "");
    }

    static String getRefreshToken(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(REFRESH_TOKEN, "");
    }
}
