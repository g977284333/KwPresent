package com.gechen.keepwalking.common.utils;

import android.content.Context;
import android.os.StrictMode;

import com.gechen.keepwalking.constants.GlobalConfig;

/**
 * @author: 葛晨  
 * @类   说   明:	StrictMode封装类
 * @version 1.0
 * @创建时间：2014-11-14 下午11:30:19
 * 
 */
public class StrictModeWrapper {
    private static final String TAG = "StrictModeWraper";

	public static void init(Context context) {
		if(GlobalConfig.DEV_MODE) {
			try {
				StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectAll()
//			.permitDiskReads()
				.penaltyLog()
				.build());
				
				StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectAll()
				.penaltyLog()
				.build());
			} catch (Throwable throwable) {
				throwable.printStackTrace();
				LogUtil.debugE(TAG, "StrictMode is not Available!");
			}
		}
	}
}
