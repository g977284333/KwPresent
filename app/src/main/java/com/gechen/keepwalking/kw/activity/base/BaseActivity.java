package com.gechen.keepwalking.kw.activity.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.gechen.keepwalking.common.utils.Logger;

/**
 * Created by G-chen on 2015-3-15.
 */
public class BaseActivity extends Activity  implements View.OnClickListener{
    protected final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Logger.debugI(TAG, "className<< " + TAG + " --- started");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.debugI(TAG, "className<< " + TAG + " --- finished");
    }

    @Override
    public void onClick(View view) {

    }

}
