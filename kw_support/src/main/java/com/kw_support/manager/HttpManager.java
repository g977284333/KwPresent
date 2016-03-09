package com.kw_support.manager;

import android.content.Context;

import com.kw_support.thirdlib.http.RequestQueue;
import com.kw_support.thirdlib.http.stack.OkHttpStack;
import com.kw_support.thirdlib.http.toolbox.Volley;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

/**
 * Created by Administrator on 2015/8/6.
 */
public class HttpManager implements IManager {

    private static HttpManager INSTANCE = null;
    private RequestQueue requestQueue = null;

    private HttpManager() {
    }

    public static HttpManager getInstance() {
        if (INSTANCE == null) {
            synchronized (HttpManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpManager();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void onInit() {

    }

    @Override
    public void onExit() {

    }

    public void init(Context context) {
        requestQueue = Volley.newRequestQueue(context, new OkHttpStack(new OkHttpClient()));
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue != null) {
            return requestQueue;
        } else {
            throw new IllegalArgumentException("RequestQueue is not initialized.");
        }
    }
}
