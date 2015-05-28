package com.kw_support.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.StringWriter;
import java.sql.Time;
import java.util.Calendar;

import android.text.TextUtils;
import android.util.Log;

import com.kw_support.constants.GlobalConfig;


/**
 * @author gechen
 * @discription：the wrapper of Log
 * @date：2014-11-14 16:30:19
 */
public class Logger {
    private static final String FILE_PATH = SdCardUtil.getAbsolutePath(GlobalConfig.LOG_PATH);

    public static final String LOG_COLOR_BLUE = "#0000ff";// 蓝色
    public static final String LOG_COLOR_RED = "#ff0000";// 红色
    public static final String LOG_COLOR_BLACK = "#000000";// 默认为黑色
    public static final String LOG_COLOR_MEGENTA = "#ff00ff";// 紫红色

    public static void d(String tag, String msg) {
        if (GlobalConfig.DEV_MODE) {
            Log.d(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (GlobalConfig.DEV_MODE) {
            Log.e(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (GlobalConfig.DEV_MODE) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (GlobalConfig.DEV_MODE) {
            Log.w(tag, msg);
        }
    }

    public static void logToFile(String tag, String msg) {
        logToFile(tag, msg, null);
    }

    public static void logToFile(String tag, Exception e) {
        logToFile(tag, getErrorInfoFromException(e), null);
    }

    public static void logToFile(String tag, Exception e, String color) {
        logToFile(tag, getErrorInfoFromException(e), color);
    }

    public static void logToFile(String tag, String msg, String color) {
        if (null == color) {
            color = LOG_COLOR_BLACK;
        }

        e(tag, msg);

        try {
            if (GlobalConfig.DEV_MODE) {
                StringBuilder builder = new StringBuilder();
                String head = "";
                String fileName = getFileName();

                File logFile = new File(FILE_PATH, fileName);
                if (!logFile.exists()) {
                    File destDir = new File(FILE_PATH);
                    if (destDir.exists()) {
                        destDir.mkdirs();
                    }
                    logFile.createNewFile();
                    head = getHead();
                }

                String time = TimeUtil.getCurrentTimeInString();
                String before = "<p><span lang=\"zh-cn\">";
                String after = "</span></p>";
                before += "<font color=\"" + color + "\">";
                after = "</font>" + after;

                builder.append(head);
                builder.append(before);
                builder.append(time);
                builder.append("[");
                builder.append(tag);
                builder.append("]");
                builder.append(msg);
                builder.append(after);

                saveToFile(FILE_PATH + fileName, builder.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getFileName() {
        String timestamp = TimeUtil.getCurrentDate();
        return timestamp  + ".log";
    }

    private static String getHead() {
        return "<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"></head>";
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

}
