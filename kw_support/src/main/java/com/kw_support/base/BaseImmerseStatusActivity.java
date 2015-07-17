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
public class BaseImmerseStatusActivity extends FragmentActivity implements View.OnClickListener {
    protected final String TAG = getClass().getSimpleName();
    protected SystemBarTintManager mTintManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getInstance().pushActivity(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }

        mTintManager = new SystemBarTintManager(this);

        mTintManager.setStatusBarTintEnabled(true);
        mTintManager.setStatusBarTintResource(R.color.actionbar_bg);
        mTintManager.setStatusBarDarkMode(false, this);
    }

    @TargetApi(19)
    protected void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
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
