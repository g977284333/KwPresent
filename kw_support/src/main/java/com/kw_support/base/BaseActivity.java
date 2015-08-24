package com.kw_support.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.kw_support.manager.ActivityManager;
import com.kw_support.utils.Logger;


/**
 * Created by G-chen on 2015-3-15.
 */
public class BaseActivity extends FragmentActivity implements View.OnClickListener {
    protected final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
