package com.gechen.keepwalking.kw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.gechen.keepwalking.R;
import com.kw_support.base.BaseActivity;
import com.kw_support.utils.AppUtil;
import com.kw_support.utils.SystemFunUtil;
import com.kw_support.utils.UiUtil;

public class CustomWidgetActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_widget);

    }

    public void skipToSegmentBarActivity(View view) {
        gotoTargetActivity(SegmentBarActivity.class);
    }

    private void gotoTargetActivity(Class clazz) {
        Intent intent = new Intent(getApplicationContext(), clazz);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
