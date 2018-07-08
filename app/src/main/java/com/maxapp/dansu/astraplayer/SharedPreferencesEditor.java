package com.maxapp.dansu.astraplayer;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

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
        SharedPreferences sharedPref = act.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(pref, val);
        editor.commit();
    }


    public boolean GetBoolean(String pref){
        SharedPreferences sharedPref = act.getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getBoolean(pref, false);
    }

    public void WriteStringList(List<String> list, String tag){
        SharedPreferences sharedPref = act.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();

        String json = gson.toJson(list);
        editor.putString(tag, json);
        editor.commit();
    }

    public List<String> ReadStringList(String tag){
        SharedPreferences sharedPref = act.getPreferences(Context.MODE_PRIVATE);
        String json = sharedPref.getString(tag, null);
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>() {}.getType();
        List<String> list = gson.fromJson(json, type);
        return list;
    }

    public void WriteDirectories(List<MyDirectory> list, String tag){
        SharedPreferences sharedPref = act.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();

        String json = gson.toJson(list);
        editor.putString(tag, json);
        editor.commit();
    }

    public List<MyDirectory> ReadDirectories(String tag){
        SharedPreferences sharedPref = act.getPreferences(Context.MODE_PRIVATE);
        String json = sharedPref.getString(tag, null);
        Gson gson = new Gson();
        Type type = new TypeToken<List<MyDirectory>>() {}.getType();
        List<MyDirectory> list = gson.fromJson(json, type);
        return list;
    }
}
