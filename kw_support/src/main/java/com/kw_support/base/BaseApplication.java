package com.kw_support.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by G-chen on 2015-5-18.
 */
public class BaseApplication extends Application {

    protected static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    private static Context getContext() {
        return mContext;
    }
}
