package com.kw_support.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by G-chen on 2015-5-20.
 * 带有一点像素的Service
 */
public class ViewService extends Service {
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mBallLayoutParams;
    private View mBallView;                                         // 球状态View

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mBallView = new View(this);
        createView();
    }

    private void createView() {
        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        mBallLayoutParams = new WindowManager.LayoutParams();
        mBallLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        mBallLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mBallLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        mBallLayoutParams.x = 0;
        mBallLayoutParams.y = 0;
        mBallLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mBallLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mBallLayoutParams.format = PixelFormat.RGBA_8888;
        mWindowManager.addView(mBallView, mBallLayoutParams);
    }
}
