package com.gln.codenum1.chapter14.utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.gln.codenum1.MyApplication;

/**
 * Created by guolina on 2017/6/7.
 */
public class SharedPreferenceUtils {

    public static final String KEY_WEATHER = "weather";
    public static final String KEY_IMAGE_URL = "image_url";

    public static String getWeatherString() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
        String weatherString = sharedPreferences.getString(KEY_WEATHER, null);
        return weatherString;
    }

    public static void putWeatherString(String weatherString) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_WEATHER, weatherString);
        editor.apply();
    }

    public static String getImageUrl() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
        String url = sharedPreferences.getString(KEY_IMAGE_URL, null);
        return url;
    }

    public static void putImageUrl(String url) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_IMAGE_URL, url);
        editor.apply();
    }
}
