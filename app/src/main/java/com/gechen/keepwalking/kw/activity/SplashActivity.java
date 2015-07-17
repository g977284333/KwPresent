package com.gechen.keepwalking.kw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.gechen.keepwalking.R;
import com.gechen.keepwalking.kw.KwApplication;
import com.gechen.keepwalking.kw.constants.AppConfig;
import com.kw_support.base.BaseActivity;

/**
 * Created by G-chen on 2015-7-16.
 */
public class SplashActivity extends BaseActivity {
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        if(KwApplication.getInstance().getConfigManager().getFirstRun()) {
            intent = new Intent(SplashActivity.this, WelcomeActivity.class);
        }
        intent = new Intent(SplashActivity.this, WeiXinMainActivity.class);
        startActivity(intent);
    }

}
