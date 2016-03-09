package com.kw_support.constants;

/**
 * Created by G-chen on 2015-3-15.
 */
public class LibConfig {

    // 开发模式
    public static final boolean DEV_MODE = true;

    // 应用根目录
    public static final String PATH_ROOT = "/keepwalking";

    // 应用log日志目录
    public static final String PATH_LOG = PATH_ROOT + "/logs/";

    // 图片目录
    public static final String PATH_IMAGE = PATH_ROOT + "/images/";

    // 缓存目录
    public static final String PATH_CAHCHE = PATH_ROOT + "/cache/";

    // SharedPreference文件名
    public static final String SHARED_PREFERENCE_NAME = "default_sp_name";

    public static final int DISK_CACHE_CAPACITY = 10 * 1024 * 1024;
}
