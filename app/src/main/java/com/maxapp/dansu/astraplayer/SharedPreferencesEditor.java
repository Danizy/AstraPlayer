package com.maxapp.dansu.astraplayer;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.maxapp.dansu.astraplayer.folder_browser.MyDirectory;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Dansu on 16.11.2017.
 */

public class SharedPreferencesEditor {

    private Activity act;

    public SharedPreferencesEditor(Activity act){
        this.act = act;
    }

    public void WriteBoolean(String pref, Boolean val){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(act);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(pref, val);
        editor.apply();
    }


    public boolean GetBoolean(String pref){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(act);
        return sharedPref.getBoolean(pref, false);
    }

    public void WriteStringList(List<String> list, String tag){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(act);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();

        String json = gson.toJson(list);
        editor.putString(tag, json);
        editor.apply();
    }

    public List<String> ReadStringList(String tag){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(act);
        String json = sharedPref.getString(tag, null);
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public void WriteDirectories(List<MyDirectory> list, String tag){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(act);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();

        String json = gson.toJson(list);
        editor.putString(tag, json);
        editor.apply();
    }

    public List<MyDirectory> ReadDirectories(String tag){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(act);
        String json = sharedPref.getString(tag, null);
        Gson gson = new Gson();
        Type type = new TypeToken<List<MyDirectory>>() {}.getType();
        return gson.fromJson(json, type);
    }
}
