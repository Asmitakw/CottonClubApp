package com.cottonclub.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Collections;
import java.util.Set;

/**
 * Created by Asmita on 5/7/2017.
 */

public class AppSession {
    private static final String TAG = "tag";
    private static SharedPreferences sharedPreferences;
    private static AppSession appSession;
    private static String SAVE_DEVICE_ID = "save_device_id";
    private static String PARTY_LIST = "party_list";
    private static String IS_INTRO = "is_intro";
    private static  String SAVE_LOGGED_IN_USER = "logged_in_as";
    private static String IS_LOGIN = "is_login";
    private static String SAVE_FIREBASE_TOKEN = "refresh_token";


    private static String SAVE_MEMBER_ID = "member_id";

    public static AppSession getInstance() {
        if (sharedPreferences == null) {
            appSession = new AppSession();
        }
        return appSession;
    }

    public void clearSharedPreference(Context context) {
        String deviceId = getDeviceId(context);
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
        sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        saveDeviceId(context, deviceId);
    }


    /*Save device Id*/
    public void saveDeviceId(Context context, String deviceId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SAVE_DEVICE_ID, deviceId);
        editor.commit();
    }

    public String getDeviceId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_DEVICE_ID, "");
    }


    /*Save logged in User*/
    public void setSaveLoggedInUser(Context context, String loggedInAs) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SAVE_LOGGED_IN_USER, loggedInAs);
        editor.commit();
    }

    public String getSaveLoggedInUser(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_LOGGED_IN_USER, "");
    }

    /* Save Login details*/
    public void saveLoginStatus(Context context, boolean isLogin) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_LOGIN, isLogin);
        editor.commit();
    }


    public boolean getLoginStatus(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }


    /* Save Token*/
    public void saveRefreshToken(Context context, String isLogin) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SAVE_FIREBASE_TOKEN, isLogin);
        editor.commit();
    }


    public String getRefreshToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getString(SAVE_FIREBASE_TOKEN, "");
    }


}
