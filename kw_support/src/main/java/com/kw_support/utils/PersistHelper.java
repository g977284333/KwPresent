package com.kw_support.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import com.kw_support.constants.GlobalConfig;

import java.util.Set;

/**
 * the wrapper of SharedPreferences
 */
public class PersistHelper {

    private static SharedPreferences mPreference = null;

    private static boolean isInited = false;

    public synchronized static void init(Context context) {
        if (mPreference == null) {
            mPreference = context.getSharedPreferences(GlobalConfig.SHARED_PREFERENCE_NAME,
                    Context.MODE_PRIVATE);
        }
        isInited = true;
    }

    public static boolean isInited() {
        return isInited;
    }

    public static void saveBoolean(String name, boolean value) {
        SharedPreferences.Editor editor = mPreference.edit();
        editor.putBoolean(name, value);
        editor.commit();
    }

    public static boolean getBoolean(String name, boolean defaultValue) {
        return mPreference.getBoolean(name, defaultValue);
    }

    public static void saveLong(String name, long value) {
        SharedPreferences.Editor editor = mPreference.edit();
        editor.putLong(name, value);
        editor.commit();
    }

    public static long getLong(String name, long defaultValue) {
        return mPreference.getLong(name, defaultValue);
    }

    public static void saveInt(String name, int value) {
        SharedPreferences.Editor editor = mPreference.edit();
        editor.putInt(name, value);
        editor.commit();
    }

    public static int getInt(String name, int defaultValue) {
        return mPreference.getInt(name, defaultValue);
    }

    public static void saveFloat(String name, float value) {
        SharedPreferences.Editor editor = mPreference.edit();
        editor.putFloat(name, value);
        editor.commit();
    }

    public static float getFloat(String name, float defaultValue) {
        return mPreference.getFloat(name, defaultValue);
    }

    public static boolean saveString(String name, String value) {
        boolean flag = false;
        SharedPreferences.Editor editor = mPreference.edit();
        editor.putString(name, value);
        flag = editor.commit();
        return flag;
    }

    public static String getString(String name, String defaultValue) {
        return mPreference.getString(name, defaultValue);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static boolean saveSet(String key, Set<String> values) {
        boolean bRet = false;
        SharedPreferences.Editor editor = mPreference.edit();
        editor.putStringSet(key, values);
        bRet = editor.commit();
        return bRet;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static Set<String> getSet(String key, Set<String> defValues) {
        return mPreference.getStringSet(key, defValues);
    }

    public static boolean clear() {
        return mPreference.edit().clear().commit();
    }
}
