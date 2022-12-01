package com.example.utt;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


// A class that stores to android's shared preferences to autologin users after one sign in
public class CookieLogin {
    static final String PREF_USER_NAME= "username";
    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserName(Context ctx, String userName) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.apply();
    }


    public static String getUserName(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }

}
