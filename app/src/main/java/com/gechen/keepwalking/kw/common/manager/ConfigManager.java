package com.gechen.keepwalking.kw.common.manager;

import com.gechen.keepwalking.kw.KwApplication;
import com.gechen.keepwalking.kw.constants.AppConfig;
import com.kw_support.manager.IManager;
import com.kw_support.utils.KwSharedPreferences;

/**
 * Created by G-chen on 2015-7-16.
 */
public class ConfigManager implements IManager {

    KwSharedPreferences mAppSharePreferences;
    KwSharedPreferences mUserPreferences;

    @Override
    public void onInit() {
        mAppSharePreferences = new KwSharedPreferences(KwApplication.getInstance(), AppConfig.SHARED_PREFERENCES_APP);
        mAppSharePreferences = new KwSharedPreferences(KwApplication.getInstance(), AppConfig.SHARED_PREFERENCES_USER);
    }

    @Override
    public void onExit() {
    }

    public void setFirstRun(boolean isFirstRun) {
        mAppSharePreferences.putBoolean("app_first_run", isFirstRun);
    }

    public boolean getFirstRun() {
        return mAppSharePreferences.getBoolean("is_first_run", true);
    }


    /**
     * 提醒设置
     *
     * @return
     */
    public void setNofifyMsg(boolean msg) {
        mAppSharePreferences.putBoolean("setting_notify_msg", msg);
    }

    public boolean isNotifyMsg() {
        return mAppSharePreferences.getBoolean("setting_notify_msg", true);
    }

    /**
     * 声音提醒
     *
     * @return
     */
    public void setNotifySound(boolean sound) {
        mAppSharePreferences.putBoolean("setting_notify_sound", sound);
    }


    public boolean isNotifySound() {
        return mAppSharePreferences.getBoolean("setting_notify_sound", true);
    }

    /**
     * 振动提醒
     *
     * @return
     */
    public void setNotifyVibrate(boolean vibrate) {
        mAppSharePreferences.putBoolean("setting_notify_vibrate", vibrate);
    }

    public boolean isNotifyVibrate() {
        return mAppSharePreferences.getBoolean("setting_notify_vibrate", false);
    }

    /**
     * 上传图片质量设置
     * 自动、原图、高（小于700kb）、中（小于300kb），低（小雨100kb）
     * @return
     */
    public void setUploadSetting(int mode) {
        mAppSharePreferences.putInt("setting_upload_pic_quality", mode);
    }

    public int getUploadSetting() {
        return mAppSharePreferences.getInt("setting_upload_pic_quality", 0);
    }

    /**
     * 图片加载模式
     * 大图、小图
     * @return
     */
    public void setPictureMode(int mode) {
        mAppSharePreferences.putInt("setting_load_pic_quality", mode);
    }

    public int getPictureMode() {
        return mAppSharePreferences.getInt("setting_load_pic_quality", 0);
    }

    /**
     * 默认加载原图
     *
     * @return
     */
    public void setLoadOrigPic(boolean loadOrigPic) {
        mAppSharePreferences.putBoolean("setting_load_orig_pic", loadOrigPic);
    }

    public boolean isLoadOrigPic() {
        return mAppSharePreferences.getBoolean("setting_load_orig_pic", false);
    }

    /**
     * 无图模式
     *
     * @return
     */
    public void setPicNone(boolean picNone) {
        mAppSharePreferences.putBoolean("setting_pic_none", picNone);
    }

    public boolean isPicNone() {
        return mAppSharePreferences.getBoolean("setting_pic_none", true);
    }

    /**
     * 关闭缓存
     *
     * @return
     */
    public void setDisableCache(boolean disableCache) {
        mAppSharePreferences.putBoolean("setting_disable_cache", disableCache);
    }

    public boolean isDisableCache() {
        return mAppSharePreferences.getBoolean("setting_disable_cache", false);
    }

    /**
     * 崩溃日志上传
     *
     * @return
     */
    public void setCrashLogUpload(boolean crashLogUpload) {
        mAppSharePreferences.putBoolean("setting_crashlog_upload", crashLogUpload);
    }

    public boolean isCrashLogUpload() {
        return mAppSharePreferences.getBoolean("setting_crashlog_upload", true);
    }

    /**
     * 手势返回方向设置
     *
     * @return
     */
    public void setSwipebackEdgeMode(int mode) {
        mAppSharePreferences.putInt("setting_swip_back_edge_mode", mode);
    }

    public int getSwipebackEdgeMode() {
        return mAppSharePreferences.getInt("setting_swip_back_edge_mode", 0);
    }
}

