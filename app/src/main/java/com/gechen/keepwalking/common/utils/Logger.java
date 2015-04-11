package com.gechen.keepwalking.common.utils;

import android.util.Log;

import com.gechen.keepwalking.constants.GlobalConfig;

/**
 * Created by G-chen on 2015-3-15.
 */
public class Logger {

    public static void debugI(String tag, String msg) {
        if(GlobalConfig.DEV_MODE) {
            Log.i(tag, msg);
        }
    }

    public static void debugD(String tag, String msg) {
        if(GlobalConfig.DEV_MODE) {
            Log.d(tag, msg);
        }
    }

    public static void debugW(String tag, String msg) {
        if(GlobalConfig.DEV_MODE) {
            Log.w(tag, msg);
        }
    }

    public static void debugE(String tag, String msg) {
        if(GlobalConfig.DEV_MODE) {
            Log.e(tag, msg);
        }
    }

}
