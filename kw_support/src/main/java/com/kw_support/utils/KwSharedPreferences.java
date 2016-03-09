package com.kw_support.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;

import com.kw_support.constants.LibConfig;

import java.util.Set;

/**
 * the wrapper of SharedPreferences
 */
public class KwSharedPreferences {

    SharedPreferences mSharedPreferences;

    public KwSharedPreferences(Context context, String name) {
        if(TextUtils.isEmpty(name)) {
            name = LibConfig.SHARED_PREFERENCE_NAME;
        }
        mSharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public void putBoolean(String name, boolean value) {
         mSharedPreferences.edit().putBoolean(name, value).commit();
    }

    public boolean getBoolean(String name, boolean defaultValue) {
        return mSharedPreferences.getBoolean(name, defaultValue);
    }

    public void putLong(String name, long value) {
        mSharedPreferences.edit().putLong(name, value).commit();
    }

    public long getLong(String name, long defaultValue) {
        return mSharedPreferences.getLong(name, defaultValue);
    }

    public  void putInt(String name, int value) {
         mSharedPreferences.edit().putInt(name, value).commit();
    }

    public  int getInt(String name, int defaultValue) {
        return mSharedPreferences.getInt(name, defaultValue);
    }

    public  void putFloat(String name, float value) {
        mSharedPreferences.edit().putFloat(name, value).commit();
    }

    public float getFloat(String name, float defaultValue) {
        return mSharedPreferences.getFloat(name, defaultValue);
    }

    public void putString(String name, String value) {
       mSharedPreferences.edit().putString(name, value).commit();
    }

    public String getString(String name, String defaultValue) {
        return mSharedPreferences.getString(name, defaultValue);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public  void putStringSet(String key, Set<String> values) {
        mSharedPreferences.edit().putStringSet(key, values).commit();
    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public  Set<String> getSet(String key, Set<String> defValues) {
        return mSharedPreferences.getStringSet(key, defValues);
    }

    public  boolean clear() {
        return mSharedPreferences.edit().clear().commit();
    }
}
