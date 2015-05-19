package com.kw_support.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.widget.Toast;

import com.kw_support.constants.GlobalConfig;

/**
 * @author: 葛晨  
 * @类   说   明:	UncaughtException处理类，当程序发生Uncaught异常时，由该类来接管程序，并记录发送错误报告
 * @version 1.0
 * @创建时间：2014-11-17 下午10:32:18
 * 
 */
public class CrashHandler implements UncaughtExceptionHandler {
	public static final String TAG = "CrashHandler";
	
	private UncaughtExceptionHandler mDefaultHandler;
	
	private static CrashHandler INSTANCE = new CrashHandler();
	
	private Context mContext;
	
	// 用来存储设备信息和异常信息
	private Map<String, String>  infos = new HashMap<String, String>();
	
	private DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	
	private CrashHandler() {};
	
	public static CrashHandler getInstance() {
		return INSTANCE;
	}
	
	public void init(Context context) {
		mContext = context;
		// 获取系统默认的UncaughtException处理器
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}
	
	//当UncaughtException发生时会转入该函数来处理
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		if(!handleException(ex) && null != mDefaultHandler) {
			// 如果用户没有处理则让系统默认的异常处理器来处理
			mDefaultHandler.uncaughtException(thread, ex);
		} else {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				LogUtil.debugE(TAG, "error: " + e.getMessage());
			}
			
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(1);
		}
	}

	private boolean handleException(Throwable ex) {
		if(null == ex) {
			return false;
		}
		
		new Thread() {
			
			@Override
			public void run() {
				Looper.prepare();
				Toast.makeText(mContext, "很抱歉，程序出现异常，即将退出！", Toast.LENGTH_SHORT).show();
				Looper.loop();
			};
		}.start();
		
		// 收集设备参数信息
		collectDeviceInfo(mContext);
		// 保存异常到日志文件
		saveCrashInfoToFile(ex);
		return true;
	}

	/**
	 * @Title: saveCrashInfoToFile 
	 * @说       明: 保存异常到日志文件
	 * @参       数: @param ex   
	 * @return void    返回类型 
	 * @throws
	 */
	private String saveCrashInfoToFile(Throwable ex) {
		StringBuffer sb = new StringBuffer();
		for(Map.Entry<String, String> entry : infos.entrySet()) {
			sb.append(entry.getKey() + " = " + entry.getValue() + "\n");
		}
		
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);

		ex.printStackTrace(printWriter);
		Throwable cause = ex.getCause();
		while(null != cause) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		printWriter.close();
		
		String result = writer.toString();
		long timeStamp = System.currentTimeMillis();
		try {
			String time = format.format(new Date(timeStamp));
			sb.append("----------------------------------<exception start>--------------------------------------------<\\n");
			sb.append("Time: ");
			sb.append(timeStamp);
			sb.append("\n");
			sb.append(result);
			sb.append("\n");
			sb.append("----------------------------------<exception end>--------------------------------------------");
			String fileName = "crach-" + time + "-" + timeStamp + "-exception.log";
			if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
				String path = SdCardUtil.getSdCardPath() + GlobalConfig.LOG_PATH;
				File filePath = new File(path);
				if(!filePath.exists()) {
					filePath.mkdirs();
				}
				FileOutputStream fos = new FileOutputStream(filePath + fileName);
				fos.write(sb.toString().getBytes("UTF-8"));
				fos.close();
			}
			return fileName;
		} catch (Exception e) {
			LogUtil.debugE(TAG, "an error occured while writing file...");
		}
		return null;
	}

	/**
	 * @Title: collectDeviceInfo 
	 * @说       明: 手机设备参数信息
	 * @参       数: @param mContext   
	 * @return void    返回类型 
	 * @throws
	 */
	private void collectDeviceInfo(Context mContext) {
		try {
			PackageManager pm = mContext.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
			if(null != pi) {
				String versionName = pi.versionName == null ? "null" : pi.versionName;
				String versionCode = pi.versionCode + "";
				infos.put("VersionName", versionName);
				infos.put("VersionCode", versionCode);
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			LogUtil.debugE(TAG, "an error occured when collect package info");
		}
		
		Field[] fields = Build.class.getDeclaredFields();
		for(Field field : fields) {
			try {
				field.setAccessible(true);
				infos.put(field.getName(), field.get(null).toString());
				LogUtil.debugD(TAG, field.getName() + " : " + field.get(null));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				LogUtil.debugD(TAG, "an error occured when collect crash info"); 
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				LogUtil.debugD(TAG, "an error occured when collect crash info"); 
			}
		}
	}
}
