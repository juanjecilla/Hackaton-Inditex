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

    public void setUserPrevToken(String userPrevToken){
        settings_editor.putString("userPrevToken", userPrevToken);
        settings_editor.commit();
    }

    public String getUserPrevToken(){
        return settings.getString("userPrevToken", "");
    }

    public void setCountryId(int countryId){
        settings_editor.putInt("countryId", countryId);
        settings_editor.commit();
    }

    public int getCountryId(){
        return settings.getInt("countryId", 0);
    }

    public void setFilterCountryId(int filterCountryId){
        settings_editor.putInt("filterCountryId", filterCountryId);
        settings_editor.commit();
    }

    public int getProfileCountryId(){
        return settings.getInt("profileCountryId", 0);
    }

    public void setProfileCountryId(int profileCountryId){
        settings_editor.putInt("profileCountryId", profileCountryId);
        settings_editor.commit();
    }

    public int getFilterCountryId(){
        return settings.getInt("filterCountryId", 0);
    }

    public void setUserId(int userId){
        settings_editor.putInt("userId", userId);
        settings_editor.commit();
    }

    public int getUserId(){
        return settings.getInt("userId", 0);
    }

    public void setUserPrevId(int userPrevId){
        settings_editor.putInt("userPrevId", userPrevId);
        settings_editor.commit();
    }

    public int getUserPrevId(){
        return settings.getInt("userPrevId", 0);
    }

    public void clearPreferences(){
        settings_editor.clear();
        settings_editor.commit();
    }

    public void setUserType(String userType){
        settings_editor.putString("userType", userType);
        settings_editor.commit();
    }

    public String getUserType(){
        return settings.getString("userType", "");
    }

    public void setDictionary(String dictionary){
        settings_editor.putString("dictionary", dictionary);
        settings_editor.commit();
    }

    public String getDictionary(){
        return settings.getString("dictionary", "");
    }

    public void setUserPlan(String userPlan){
        settings_editor.putString("userPlan", userPlan);
        settings_editor.commit();
    }

    public String getUserPlan(){
        return settings.getString("userPlan", "");
    }

    public void setProfilePicChanged(boolean profilePicChanged){
        settings_editor.putBoolean("profilePicChanged", profilePicChanged);
        settings_editor.commit();
    }

    public boolean getProfilePicChanged(){
        return  settings.getBoolean("profilePicChanged", false);
    }

    public void setCurrentTimeProfilePic(String currentTimeProfilePic){
        settings_editor.putString("currentTimeProfilePic", currentTimeProfilePic);
        settings_editor.commit();
    }

    public String getCurrentTimeProfilePic(){
        return  settings.getString("currentTimeProfilePic", "");
    }

    public void setTutorialFinished(boolean tutorialFinished){
        settings_editor.putBoolean("tutorialFinished", tutorialFinished);
        settings_editor.commit();
    }

    public boolean getTutorialFinished(){
        return  settings.getBoolean("tutorialFinished", false);
    }

    public void setProMode(boolean proMode){
        settings_editor.putBoolean("proMode", proMode);
        settings_editor.commit();
    }

    public boolean getProMode(){
        return  settings.getBoolean("proMode", false);
    }

    public void setFirstProMode(boolean firstProMode){
        settings_editor.putBoolean("firstProMode", firstProMode);
        settings_editor.commit();
    }

    public boolean getFirstProMode(){
        return  settings.getBoolean("firstProMode", true);
    }

    public void setFirstVibukMode(boolean firstVibukMode){
        settings_editor.putBoolean("firstVibukMode", firstVibukMode);
        settings_editor.commit();
    }

    public boolean getFirstVibukMode(){
        return  settings.getBoolean("firstVibukMode", true);
    }


    public void setNewNotification(boolean newNotification){
        settings_editor.putBoolean("newNotification", newNotification);
        settings_editor.commit();
    }

    public boolean getNewNotification(){
        return  settings.getBoolean("newNotification", true);
    }


}
