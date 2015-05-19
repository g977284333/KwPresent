package com.kw_support.utils;

import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * @author: 葛晨  
 * @类   说   明:	应用程序的工具类
 * @version 1.0
 * @创建时间：2014-10-29 下午8:16:41
 * 
 */
public class AppUtil {
    private static final String TAG = "AppUtil";

	// 当前进程是否以processName命名
	public static boolean isNamedProcess(Context context, String processName) {
		if(null == context) {
			return false;
		}
		
		int pid = android.os.Process.myPid();
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> processInfoList = am.getRunningAppProcesses();
		if(null == processInfoList) {
			return true;
		}
		
		for(RunningAppProcessInfo info : processInfoList) {
			if(pid == info.pid && processName.equals(info.processName)) {
				return true;
			}
		}
		return false;
	}
	

	//  判断应用是否在后台
	public static boolean isApplicationBackground(Context context) {
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> taskList = am.getRunningTasks(1); // 1表示当前当前运行的task
		if(null != taskList && !taskList.isEmpty()) {
			ComponentName topActivity = taskList.get(0).topActivity;
			if(null != topActivity && topActivity.getPackageName().equals(context.getPackageName())) {
				return true;
			}
		}
		return false;
	}
	
	// 用来检索activity中是否正在运行某个service.
	public static boolean isServiceRunning(Context context, String serviceName) {
		ActivityManager manger = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningServiceInfo> rs = manger.getRunningServices(50);
		
		for(RunningServiceInfo rsi : rs) {
			String className = rsi.service.getClassName();
			if(className.equals(serviceName)) {
				return true;
			}
		}
		return false;
	}

	public static String getApplicationVersion(Context context) {
		PackageManager packageManager = context.getPackageManager();
		String packageName = context.getPackageName();
		String versionName = "";
		try {
			versionName = packageManager.getPackageInfo(packageName, 0).versionName;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionName;
	}

	// 获取设备号
	public static String getDeviceId(Context context) {
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getDeviceId();
	}

	// 获取mac地址
	public static String getMacAddress(Context context) {
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		return wifiManager.getConnectionInfo().getMacAddress();
	}

	// 清理内存
	public static void clearMemory() {
		System.gc();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			LogUtil.logToFile(TAG, "在可用内存不足的时候强制垃圾回收失败(PubUtil--clearMemory())");
			return ;
		}
		System.runFinalization(); // 在可用内存不足的时候由系统分配执行清理
		try {
			Thread.sleep(1000);
		} catch (InterruptedException ie) {
			LogUtil.logToFile(TAG,"在可用内存不足的时候由系统分配执行清理失败：");
			ie.printStackTrace();
			return;
		}
	}

	/**
	 * Activity中
	 *	public boolean dispatchTouchEvent(MotionEvent event) {
	 *	List<View> viewList = new ArrayList<View>();
	 *	viewList.add(mEditPhone);
	 *	viewList.add(mEditVerifyCode);

	 *	AppUtils.checkNeedHideSoftInput(this, (int) event.getX(), (int) event.getY(), viewList);
	 *	return super.dispatchTouchEvent(event);}
	 */
	public static void checkNeedHideSoftInput(Activity activity, int x, int y, List<View> viewList) {
		boolean needHide = true;

		Rect rect = new Rect();
		for(View view : viewList) {
			if(!view.isShown()) {
				continue;
			}

			view.getGlobalVisibleRect(rect);
			needHide = !rect.contains(x, y) && needHide;
		}

		if(needHide) {
			hideSoftInput(activity);
		}
	}

	public static void showSoftInput(Activity activity) {
		InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager.showSoftInput(activity.getCurrentFocus(), inputMethodManager.SHOW_FORCED);
	}

	public static void hideSoftInput(Activity activity) {
		InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(activity.getWindow().peekDecorView().getWindowToken(), 0);
	}

	public static int getCurOsVerson() {
		return VERSION.SDK_INT;
	}

	public static void gotoSettingNetWork(Context context) {
		Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
		context.startActivity(intent);
	}

	public static boolean isLocationEnable(Context context) {
		LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		// 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
		boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		// 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
		return network && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}

	public static void gotoSettingGPS(Context context) {
		Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		context.startActivity(intent);
	}

	public static boolean isBluetoothEnable() {
		BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		return bluetoothAdapter == null ? false : bluetoothAdapter.isEnabled();
	}

	public static void openBluetoothWithRequest(Activity activity, int RequestCode) {
		Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		activity.startActivityForResult(intent,RequestCode );
	}

	/**
	 * 获取Android各个版本的版本号
	 * 仅供参考
	 */
	@SuppressWarnings("unused")
	private String getSDKVersionInfo() {
	   StringBuffer sb = new StringBuffer();
	   sb.append("第一版：" + Build.VERSION_CODES.BASE + "/n");
	   sb.append("1.1版：" + Build.VERSION_CODES.BASE_1_1 + "/n");
	   sb.append("1.5版：" + Build.VERSION_CODES.CUPCAKE + "/n");
	   sb.append("此版官方未发布：" + Build.VERSION_CODES.CUR_DEVELOPMENT + "/n");
	   sb.append("1.6版：" + Build.VERSION_CODES.DONUT + "/n");
	   sb.append("2.0版：" + Build.VERSION_CODES.ECLAIR + "/n");
	   sb.append("2.0.1版：" + Build.VERSION_CODES.ECLAIR_0_1 + "/n");
	   sb.append("2.1版：" + Build.VERSION_CODES.ECLAIR_MR1 + "/n");
	   sb.append("2.2版：" + Build.VERSION_CODES.FROYO + "/n");
	   sb.append("2.3版：" + Build.VERSION_CODES.GINGERBREAD + "/n");
	   sb.append("2.3.3版：" + Build.VERSION_CODES.GINGERBREAD_MR1 + "/n");
	   sb.append("3.0版：" + Build.VERSION_CODES.HONEYCOMB + "/n");
	   sb.append("3.1版：" + Build.VERSION_CODES.HONEYCOMB_MR1 + "/n");
	   sb.append("3.2版：" + Build.VERSION_CODES.HONEYCOMB_MR2 + "/n");
	   sb.append("4.0版：" + Build.VERSION_CODES.ICE_CREAM_SANDWICH + "/n");
	   sb.append("4.0.3版：" + Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1
	        + "/n");
	   sb.append("4.1版：" + Build.VERSION_CODES.JELLY_BEAN + "/n");
	   sb.append("4.2版：" + Build.VERSION_CODES.JELLY_BEAN_MR1 + "/n");
	   sb.append("4.3版："+Build.VERSION_CODES.JELLY_BEAN_MR2+"/n");
	   sb.append("4.4版："+Build.VERSION_CODES.KITKAT);
	   return sb.toString();
	 }
}
