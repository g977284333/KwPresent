package com.kw_support.utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;

import com.kw_support.constants.GlobalConfig;

/**
 * @author gechen
 * @类说明:SD卡工具类(内存工具类)
 */
public class SdCardUtil {

    public static boolean isMounted() {
        return Environment.MEDIA_MOUNTED
                .equals(Environment.getExternalStorageState());
    }

    public static String getExternalStorageAbsolutePath() {
        if (isMounted()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return null;
    }

    public static File getExternalStoragePath() throws IOException {
        if (!isMounted())
            throw new IOException("sd card is not exist!");
        File storageDirectory = Environment.getExternalStorageDirectory();
        File storagePath = new File(storageDirectory, GlobalConfig.ROOT_PATH);
        if (!storagePath.exists() && !storagePath.mkdirs())
            throw new IOException(String.format("%s cannot be created!", storagePath.toString()));
        if (!storagePath.isDirectory())
            throw new IOException(String.format("%s is not a directory!", storagePath.toString()));
        return storagePath;
    }

    public static String getAbsolutePath(String path) {
        return getExternalStorageAbsolutePath() + path;
    }

    public static long getWholeSdSize() {
        return getStorageSize(getExternalStorageAbsolutePath());
    }

    public static long getAvailSdSize() {
        return getAvailStorageSize(getExternalStorageAbsolutePath());
    }

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

    // 获取指定存储器的容量
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

    public static String formatMemorySize(long size) {
        StringBuilder sb = new StringBuilder();
        double newSize = size * 1.0;
        if (size >= 0 && size < 1024) {
            sb.append(newSize + "B");
        } else if (size >= 1024 && size < 1024 * 1024) {
            newSize = newSize / 1024;
            sb.append(newSize + "kb");
        } else if (size >= 1024 * 1024 && size < 1024 * 1024 * 1024) {
            newSize = newSize / (1024 * 1024);
            sb.append(newSize + "M");
        } else if (size >= 1024 * 1024 * 1024) {
            newSize = newSize / (1024 * 1024 * 1024);
            sb.append(newSize + "G");
        }
        return sb.toString();
    }


    /**
     * 判断双SD卡 待测
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
            for (int i = 0; i < ((String[]) invoke).length; i++) {
                StatFs statFs = new StatFs(((String[]) invoke)[i]);
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



























