package com.kw_support.utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author: 葛晨  
 * @类   说   明:	网络状态的工具类
 * @version 1.0
 * @创建时间：2014-11-11 下午10:53:43
 * 
 */
public class NetWorkUtil {
	public static enum NetType {
		WIFI, MOBILE, NONET, UNKNOWN;
	}
	
	/**
	 * 
	 * @Title: isNetWorkAvailable 
	 * @说       明: 判断网络是否可用
	 * @参       数: @param context
	 * @参       数: @return   
	 * @return boolean    返回类型 
	 * @throws
	 */
	public static boolean isNetWorkAvailable(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] mNetworkInfos = cm.getAllNetworkInfo();
		for(NetworkInfo info : mNetworkInfos) {
			if(NetworkInfo.State.CONNECTED == info.getState()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @Title: isNetWorkConnection 
	 * @说       明: 判断是否有网络连接
	 * @参       数: @param context
	 * @参       数: @return   
	 * @return boolean    返回类型 
	 * @throws
	 */
	public static boolean isNetWorkConnection(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mNetworkInfo = cm.getActiveNetworkInfo();
		if(null != mNetworkInfo) {
			return mNetworkInfo.isAvailable();
		}
		return false;
	}
	
	/**
	 * 
	 * @Title: isWifiAvailable 
	 * @说       明: 判断wifi是否可用
	 * @参       数: @param context
	 * @参       数: @return   
	 * @return boolean    返回类型 
	 * @throws
	 */
	public static boolean isWifiAvailable(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if(null != wifiInfo) {
			return wifiInfo.isAvailable();
		}
		return false;
	}
	
	/**
	 * 
	 * @Title: isMobileAvailable 
	 * @说       明: 判断Mobile网络是否可用
	 * @参       数: @param context
	 * @参       数: @return   
	 * @return boolean    返回类型 
	 * @throws
	 */
	public static boolean isMobileAvailable(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mNetworkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if(null != mNetworkInfo) {
			return mNetworkInfo.isAvailable();
		}
		return false;
	}
	
	/**
	 * 
	 * @Title: getNetType 
	 * @说       明: 获取网络类型
	 * @参       数: @param context
	 * @参       数: @return   
	 * @return NetType    返回类型 
	 * @throws
	 */
	public static NetType getNetType(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mNetworkInfo = cm.getActiveNetworkInfo();
		if(null == mNetworkInfo) {
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
	
	/**
	 * @Title: setSystemNetWork 
	 * @说       明: 调用系统网络设置界面
	 * @参       数:    
	 * @return void    返回类型 
	 * @throws
	 */
	public static void setSystemNetWork(Context context) {
		Intent intent = new Intent("android.settings.WIRELESS_SETTINGS");
		context.startActivity(intent);
	}
}
