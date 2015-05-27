package com.kw_support.utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @version 1.0
 * @author:    葛晨
 * @类说明:	   网络状态的工具类
 * @创建时间：2014-11-11 下午10:53:43
 */
public class NetWorkUtil {
    public static enum NetType {
        WIFI, MOBILE, NONET, UNKNOWN;
    }

    public static boolean isNetWorkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] mNetworkInfos = cm.getAllNetworkInfo();
        for (NetworkInfo info : mNetworkInfos) {
            if (NetworkInfo.State.CONNECTED == info.getState()) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNetWorkConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = cm.getActiveNetworkInfo();
        if (null != mNetworkInfo) {
            return mNetworkInfo.isAvailable();
        }
        return false;
    }

    public static boolean isWifiAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (null != wifiInfo) {
            return wifiInfo.isAvailable();
        }
        return false;
    }

    public static boolean isMobileAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (null != mNetworkInfo) {
            return mNetworkInfo.isAvailable();
        }
        return false;
    }

    public static NetType getNetType(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = cm.getActiveNetworkInfo();
        if (null == mNetworkInfo) {
            return NetType.NONET;
        }

        int mNetType = mNetworkInfo.getType();
        switch (mNetType) {
            case ConnectivityManager.TYPE_MOBILE:
                return NetType.MOBILE;
            case ConnectivityManager.TYPE_WIFI:
                return NetType.WIFI;
            default:
                return NetType.UNKNOWN;
        }
    }

    public static void setSystemNetWork(Context context) {
        Intent intent = new Intent("android.settings.WIRELESS_SETTINGS");
        context.startActivity(intent);
    }
}
