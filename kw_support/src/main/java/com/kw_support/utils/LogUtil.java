package com.kw_support.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.StringWriter;
import java.util.Calendar;

import android.text.TextUtils;
import android.util.Log;

import com.kw_support.constants.GlobalConfig;


/**
 * @author gechen
 * @类说明：the wrapper of Log
 * @创建时间：2014-11-14 下午16:30:19
 */
public class LogUtil {

    private static final String FILE_NAME = "/log_gechen.htm"; // 记录log的文件名
    private static final String FILE_PATH = SdCardUtil.getAbsolutePath(GlobalConfig.LOG_PATH);

    public static final String LOG_COLOR_BLUE = "#0000ff";// 蓝色
    public static final String LOG_COLOR_RED = "#ff0000";// 红色
    public static final String LOG_COLOR_BLACK = "#000000";// 默认为黑色
    public static final String LOG_COLOR_MEGENTA = "#ff00ff";// 紫红色

    public static void debugD(String tag, String msg) {
        if (GlobalConfig.DEV_MODE) {
            Log.d(tag, msg);
        }
    }

    public static void debugE(String tag, String msg) {
        if (GlobalConfig.DEV_MODE) {
            Log.e(tag, msg);
        }
    }

    public static void debugI(String tag, String msg) {
        if (GlobalConfig.DEV_MODE) {
            Log.i(tag, msg);
        }
    }

    public static void debugW(String tag, String msg) {
        if (GlobalConfig.DEV_MODE) {
            Log.w(tag, msg);
        }
    }

    /**
     * @return void    返回类型
     * @throws
     * @Title: logToFile
     * @说 明: 将异常的msg输入到日志文件中
     * @参 数: @param tag
     * @参 数: @param msg
     */
    public static void logToFile(String tag, String msg) {
        logToFile(tag, msg, null);
    }

    /**
     * @return void    返回类型
     * @throws
     * @Title: logToFile
     * @说 明: 将异常输入到日志文件中
     * @参 数: @param tag
     * @参 数: @param e  异常
     */
    public static void logToFile(String tag, Exception e) {
        logToFile(tag, getErrorInfoFromException(e), null);
    }

    /**
     * @return void    返回类型
     * @throws
     * @Title: logToFile
     * @说 明: 将异常输入到日志文件中
     * @参 数: @param tag
     * @参 数: @param e  异常
     */
    public static void logToFile(String tag, Exception e, String color) {
        logToFile(tag, getErrorInfoFromException(e), color);
    }

    /**
     * 持久化Log(以htm的形式持久化到本地)
     */
    public static void logToFile(String tag, String msg, String color) {
        if (null == color) {
            color = LOG_COLOR_BLACK;
        }

        debugE(tag, msg);
        try {
            if (GlobalConfig.DEV_MODE) {
                String head = "";
                File logFile = new File(FILE_PATH, FILE_NAME);
                if (!logFile.exists()) {
                    // 创建目录
                    File destDir = new File(FILE_PATH);
                    if (destDir.exists()) {
                        destDir.mkdirs();
                    }
                    logFile.createNewFile();
                    head = "<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"></head>";
                }

                // 标识日志日期
                Calendar calendar = Calendar.getInstance();
                String time = String.format("%2d.%2d %2d:%2d:%2d.%2d",
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH),
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        calendar.get(Calendar.SECOND),
                        calendar.get(Calendar.MILLISECOND));
                String before = "<p><span lang=\"zh-cn\">";
                String after = "</span></p>";
                if (!TextUtils.isEmpty(color)) {
                    before += "<font color=\"" + color + "\">";
                    after = "</font>" + after;
                }
                saveToFile(FILE_PATH + FILE_NAME, head + before + time + "   ["
                        + tag + "]  " + msg + after);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveToFile(String fileName, String content) {
        try {
            RandomAccessFile rf = new RandomAccessFile(fileName, "rw");
            rf.seek(rf.length());
            rf.write(content.getBytes("utf-8"));
            rf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return String    返回类型
     * @throws
     * @Title: getErrorInfoFromException
     * @说 明: 将Exception信息转换成字符串
     * @参 数: @param e
     * @参 数: @return
     */
    public static String getErrorInfoFromException(Exception e) {
        try {
            StringWriter sw = new StringWriter();
            PrintWriter printStream = new PrintWriter(sw);
            e.printStackTrace(printStream);
            return "\r\n" + sw.toString() + "\r\n";
        } catch (Exception e1) {
            return "bad getErrorInfoFromException";
        }
    }

    /**
     * 删除log文件
     */
    public static void clearLogFile() {
        File logFile = new File(FILE_PATH, FILE_NAME);
        logFile.delete();
    }
}
