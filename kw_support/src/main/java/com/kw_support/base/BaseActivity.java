package com.kw_support.base;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.kw_support.R;
import com.kw_support.manager.ActivityManager;
import com.kw_support.manager.SystemBarTintManager;
import com.kw_support.utils.Logger;


/**
 * Created by G-chen on 2015-3-15.
 */
public class BaseActivity extends FragmentActivity implements View.OnClickListener {
    protected final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ActivityManager.getInstance().pushActivity(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Logger.i(TAG, "className<< " + TAG + " --- started");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.i(TAG, "className<< " + TAG + " --- finished");
        ActivityManager.getInstance().popActivity(this);
    }

    @Override
    public void onClick(View view) {

    }

}
