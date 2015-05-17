package com.gechen.keepwalking.common.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.gechen.keepwalking.common.utils.LogUtil;

/**
 * Created by G-chen on 2015-3-15.
 */
public class BaseActivity extends FragmentActivity implements View.OnClickListener{
    protected final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.debugI(TAG, "className<< " + TAG + " --- started");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.debugI(TAG, "className<< " + TAG + " --- finished");
    }

    @Override
    public void onClick(View view) {

    }

}
