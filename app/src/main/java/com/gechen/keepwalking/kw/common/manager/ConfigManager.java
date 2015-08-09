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

    @Override
    public void onInit() {
        mAppSharePreferences = new KwSharedPreferences(KwApplication.getInstance(), AppConfig.SHARED_PREFERENCES_APP);
        mAppSharePreferences = new KwSharedPreferences(KwApplication.getInstance(), AppConfig.SHARED_PREFERENCES_USER);
    }

    @Override
    public void onExit() {
    }

    public void setFirstRun(boolean isFirstRun) {
        mAppSharePreferences.putBoolean("is_first_run", isFirstRun);
    }

    public boolean getFirstRun() {
        return mAppSharePreferences.getBoolean("is_first_run", true);
    }
}

