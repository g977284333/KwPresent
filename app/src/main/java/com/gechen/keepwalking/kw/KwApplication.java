package com.gechen.keepwalking.kw
        ;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by G-chen on 2015-3-15.
 */
public class KwApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(this);
    }

    
}
