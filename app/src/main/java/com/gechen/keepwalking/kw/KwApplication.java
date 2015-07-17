package com.gechen.keepwalking.kw
        ;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.gechen.keepwalking.kw.manager.ConfigManager;
import com.kw_support.base.BaseApplication;
import com.kw_support.utils.CrashHandler;
import com.kw_support.utils.StrictModeWrapper;

/**
 * Created by G-chen on 2015-3-15.
 */
public class KwApplication extends BaseApplication {
    public static KwApplication mInstance = null;
    private ConfigManager mConfigManager;

    public KwApplication() {
        mInstance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        SDKInitializer.initialize(this);
        CrashHandler.getInstance().init(this);
        StrictModeWrapper.init(this);

        mConfigManager = new ConfigManager();
        mConfigManager.onInit();
    }

    public static KwApplication getInstance() {
        return mInstance;
    }

    public ConfigManager getConfigManager() {
        return mConfigManager;
    }
}
