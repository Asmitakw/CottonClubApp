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

    /*Save device Id*/
    public void savePartyList(Context context, Set<String> partyList) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(PARTY_LIST, partyList);
        editor.commit();
    }

    public Set<String> getPartyList(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sharedPreferences.getStringSet(PARTY_LIST, Collections.singleton(""));
    }

}
