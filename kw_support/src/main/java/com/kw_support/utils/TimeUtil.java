package com.kw_support.utils;

import android.annotation.SuppressLint;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @version 1.0
 * @author: 葛晨
 * @类说明:
 * @创建时间：2014-11-5 下午4:55:06
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

    public static String timeFormat(long timeMillis, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.CHINA);
        return format.format(new Date(timeMillis));
    }

    public static String formatPhotoDate(long time) {
        return timeFormat(time, "yyyy-MM-dd");
    }

    public static String formatPhotoDate(String path) {
        File file = new File(path);
        if (file.exists()) {
            long time = file.lastModified();
            return formatPhotoDate(time);
        }
        return "1970-01-01";
    }
}
