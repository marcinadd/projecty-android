package com.marcinadd.projecty.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class ServerHelper {
    public static final String SERVER = "server";

    public static String getServerURL(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(SERVER, "");
    }
}
