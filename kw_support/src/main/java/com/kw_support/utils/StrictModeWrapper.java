package com.kw_support.utils;

import android.content.Context;
import android.os.StrictMode;

import com.kw_support.constants.LibConfig;

/**
 * @version 1.0
 * @author:     葛晨
 * @类说明:	   StrictMode封装类
 * @创建时间：2014-11-14 下午11:30:19
 */
public class StrictModeWrapper {
    private static final String TAG = "StrictModeWraper";

    public static void init(Context context) {
        if (LibConfig.DEV_MODE) {
            try {
                StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                        .detectAll()
                        .penaltyDialog()
//			.permitDiskReads()
                        .penaltyLog()
                        .build());

                StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                        .detectAll()
                        .penaltyLog()
                        .build());
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                Logger.e(TAG, "StrictMode is not Available!");
            }
        }
    }
}
