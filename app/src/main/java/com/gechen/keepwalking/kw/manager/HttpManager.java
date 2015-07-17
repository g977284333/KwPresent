package com.gechen.keepwalking.kw.manager;

import android.app.DownloadManager;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by G-chen on 2015-7-5.
 */
public class HttpManager {

    private static final int CONNECT_TIME_OUT = 30;

    private final OkHttpClient mHttPClient;

    public HttpManager () {
        mHttPClient = new OkHttpClient();
        mHttPClient.setConnectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS);
    }

    // 开启异步线程访问网络
    public void requestServer(Request request, Callback callback) {
        mHttPClient.newCall(request).enqueue(callback);
    }

    // 开启异步线程访问网络-不处理结果
    public void requestServer(Request request) {
        mHttPClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {

            }
        });
    }

    // 异步get
    public void requestServer() {
        
    }
}
