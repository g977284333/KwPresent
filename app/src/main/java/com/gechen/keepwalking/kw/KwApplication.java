package com.gechen.keepwalking.kw
        ;

import com.kw_support.manager.HttpManager;
import com.kw_support.thirdlib.http.Request;
import com.kw_support.thirdlib.http.RequestQueue;
import com.kw_support.thirdlib.http.toolbox.Volley;
import com.baidu.mapapi.SDKInitializer;
import com.gechen.keepwalking.kw.common.manager.ConfigManager;
import com.kw_support.base.BaseApplication;
import com.kw_support.thirdlib.http.stack.OkHttpStack;
import com.kw_support.utils.CrashHandler;
import com.kw_support.utils.StrictModeWrapper;
import com.squareup.okhttp.OkHttpClient;

/**
 * Created by G-chen on 2015-3-15.
 */
public class KwApplication extends BaseApplication {
    public static KwApplication mInstance = null;
    private ConfigManager mConfigManager;
    private RequestQueue mRequestQueue;

    public KwApplication() {
        mInstance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
//        SDKInitializer.initialize(this);
//        CrashHandler.getInstance().init(this);
        StrictModeWrapper.init(this);
        HttpManager.getInstance().init(this);

        mConfigManager = new ConfigManager();
        mConfigManager.onInit();
    }

    public static KwApplication getInstance() {
        return mInstance;
    }

    public ConfigManager getConfigManager() {
        return mConfigManager;
    }

    public RequestQueue getVolleyRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(this, new OkHttpStack(new OkHttpClient()));
        }
        return mRequestQueue;
    }

    public static void addRequest(Request<?> request) {
        getInstance().getVolleyRequestQueue().add(request);
    }

    public static void addRequest(Request<?> request, String tag) {
        request.setTag(tag);
        addRequest(request);
    }

    public static void cancelAllRequests(String tag) {
        getInstance().getVolleyRequestQueue().cancelAll(tag);
    }
}
