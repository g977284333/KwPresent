package com.kw_support.utils;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: 葛晨  
 * @类   说   明: 
 * @version 1.0
 * @创建时间：2014-11-5 下午4:55:06
 * 
 */
@SuppressLint("SimpleDateFormat")
public class TimeUtil {
	
	public static SimpleDateFormat DEFUALT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat DATE_FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 自定义dateFormat，格式化long型时间
	 */
	public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
		return dateFormat.format(new Date(timeInMillis));
	}
	
	/**
	 * 使用默认dateFormat，格式化long型时间
	 */
	public static String getTime(long timeInMills) {
		return getTime(timeInMills, DEFUALT_DATE_FORMAT);
	}
	
	/**
	 * 获取当前long类型时间
	 */
	public static long getCurrentTimeInLong() {
		return System.currentTimeMillis();
	}
	
	/**
	 * 获取当前String类型时间
	 */
	public static String getCurrentTimeInString() {
		return getTime(getCurrentTimeInLong());
	}
	
	/**
	 * 自定义格式输出当前时间
	 */
	public static String getCurrentTimeInString(SimpleDateFormat dateFormat) {
		return getTime(getCurrentTimeInLong(), dateFormat);
	}
	
	/**
	 * 获取当前日期
	 */
	public static String getCurrentDate() {
		return getTime(getCurrentTimeInLong(), DATE_FORMAT_DATE);
	}
}
