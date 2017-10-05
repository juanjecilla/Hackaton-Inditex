package com.monoloco.zaraqueue.preferences;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by root on 5/10/17.
 */

public class PreferencesManager {

    public SharedPreferences settings;
    public SharedPreferences.Editor settings_editor;
    public final String PREFS_NAME = "SharedPreferences";

    @SuppressLint("CommitPrefEdits")
    public PreferencesManager(Context context) {
        // Initialize SharedPrefences and its editor
        settings = context.getSharedPreferences(PREFS_NAME, 0);
        settings_editor = settings.edit();
    }

    public void setUserToken(String userToken){
        settings_editor.putString("userToken", userToken);
        settings_editor.commit();
    }

    public String getUserToken(){
        return settings.getString("userToken", "");
    }

    public void setIsInQueue(boolean isInQueue){
        settings_editor.putBoolean("isInQueue", isInQueue);
        settings_editor.commit();
    }

    public boolean getIsInQueue(){
        return settings.getBoolean("isInQueue", false);
    }
}
