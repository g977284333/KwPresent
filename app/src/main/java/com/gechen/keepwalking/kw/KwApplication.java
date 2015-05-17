package com.gechen.keepwalking.kw
        ;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;
import com.gechen.keepwalking.common.utils.CrashHandler;
import com.gechen.keepwalking.common.utils.StrictModeWrapper;

/**
 * Created by G-chen on 2015-3-15.
 */
public class KwApplication extends Application{
    public static KwApplication mInstance = null;

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
    }

    public static KwApplication getInstance() {
        return mInstance;
    }

}
