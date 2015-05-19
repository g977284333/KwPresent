package com.kw_support.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;

/**
 * @类说明:SD卡工具类(内存工具类)
 * @author gechen
 *
 */
public class SdCardUtil {

	// sd卡是否挂载
	public static boolean isMounted() {
		return Environment.MEDIA_MOUNTED
				.equals(Environment.getExternalStorageState());
	}
	
	// sd卡路径
	public static String getSdCardPath() {
		if(isMounted()) {
			return Environment.getExternalStorageDirectory().getAbsolutePath();			
		} 
		return null;
	}

    // 获取在sd卡上的绝对路径
    public static String getAbsolutePath(String path) {
        return getSdCardPath() + path;
    }
	
	// 获取sd卡容量
	public static long getWholeSdSize() {
		return getStorageSize(getSdCardPath());
	}
	
	// 获取sd卡可用容量
	public static long getAvailSdSize() {
		return getAvailStorageSize(getSdCardPath());
	}
	
	// 手机内存路径
	public static String getDataDirectoryPath() {
		return Environment.getDataDirectory().getAbsolutePath();
	}	
	
	// 获取手机内存容量
	public static long getWholeRAMSize() {
		return getStorageSize(getDataDirectoryPath());
	}
	
	// 获取手机内存可用容量
	public static long getAvailMemorySize() {
		return getAvailStorageSize(getDataDirectoryPath());
	}
	
	// 获取制定存储器的容量
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private static long getStorageSize(String path) {
		StatFs statFs = new StatFs(path);
		
		long blockCount = AppUtil.getCurOsVerson() > 17 ? statFs.getAvailableBlocksLong() : statFs.getBlockCount();
		long blockSize = AppUtil.getCurOsVerson() > 17 ? statFs.getBlockCountLong() : statFs.getBlockSize();
		
		return blockSize * blockCount;	
	}
	
	// 获取指定存储器的可用容量
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private static long getAvailStorageSize(String path) {
		StatFs statFs = new StatFs(path);
		
		long blockCount = AppUtil.getCurOsVerson() > 17 ? statFs.getAvailableBlocksLong() : statFs.getBlockCount();
		long availSize = AppUtil.getCurOsVerson() > 17 ? statFs.getAvailableBlocksLong() : statFs.getAvailableBlocks();
		
		return availSize * blockCount;
	}
	
	/**
	 * 格式化输出内存大小
	 * 有待优化
	 */
	//FIXME
	public static String formatMemorySize(long size) {
		StringBuilder sb = new StringBuilder();
		double newSize = size * 1.0;
		if(size >= 0 && size < 1024) {
			sb.append(newSize + "B");
		} else if(size >= 1024 && size < 1024 * 1024) {
			newSize = newSize / 1024;
			sb.append(newSize + "kb");
		} else if(size >= 1024 * 1024 && size < 1024 * 1024 * 1024) {
			newSize = newSize / (1024 * 1024);
			sb.append(newSize + "M");
		} else if(size >= 1024 * 1024 * 1024){
			newSize = newSize / (1024 * 1024 * 1024);
			sb.append(newSize + "G");
		}
		return sb.toString();
	}
	
	
	/**
	 * 
	 * @Title: extraSdCard 
	 * @说       明: 判断双SD卡 待测
	 * @参       数: @param context   
	 * @return void    返回类型 
	 * @throws
	 */
	//FIXME
	public static void extraSdCard(Context context) {
		StorageManager sm = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
		
		try {
			Class<?>[] parameterTypes = {};
			Method getVolumePathsMethod = StorageManager.class.getMethod("getVolumePaths", parameterTypes);
			getVolumePathsMethod.setAccessible(true);
			Object[] params = {};
			Object invoke = getVolumePathsMethod.invoke(sm, params);
			for(int i = 0; i < ((String[])invoke).length; i++) {
				StatFs statFs = new StatFs(((String[])invoke)[i]);
				LogUtil.debugE("这是第" + i + "SD卡，总容量为：", formatMemorySize(statFs.getBlockSize()));
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}



























