package com.gechen.keepwalking.kw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.view.WindowManager;

import com.gechen.keepwalking.R;
import com.gechen.keepwalking.kw.KwApplication;
import com.gechen.keepwalking.kw.constants.AppConfig;
import com.kw_support.base.BaseActivity;

/**
 * Created by G-chen on 2015-7-16.
 */
public class SplashActivity extends FragmentActivity {
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        init();
    }

    private void init() {

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                startNextActivity();
                finish();
            }
        };

        mHandler.sendEmptyMessageDelayed(0, AppConfig.SPLASH_DURATION);
    }

    private void startNextActivity() {
        Intent intent = null;
        if (KwApplication.getInstance().getConfigManager().getFirstRun()) {
            intent = new Intent(SplashActivity.this, WelcomeActivity.class);
        } else {
            intent = new Intent(SplashActivity.this, WeiXinMainActivity.class);
        }
        startActivity(intent);
    }
}
