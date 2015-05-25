package com.gechen.keepwalking.kw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.gechen.keepwalking.R;
import com.kw_support.base.BaseActivity;
import com.kw_support.utils.AppUtil;
import com.kw_support.utils.SystemFunUtil;
import com.kw_support.utils.UiUtil;
import com.kw_support.view.BounceScrollView;

public class BounceScrollViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bounce_scroll_view);

        init();
    }

    private void init() {
        if(AppUtil.isLocationEnable(BounceScrollViewActivity.this)) {
            UiUtil.showToast(BounceScrollViewActivity.this, "GPS可用");
        } else {
            UiUtil.showToast(BounceScrollViewActivity.this, "GPS不可用");
        }

        if(AppUtil.isBluetoothEnable()) {
            UiUtil.showToast(BounceScrollViewActivity.this, "蓝牙可用");
        } else {
            UiUtil.showToast(BounceScrollViewActivity.this, "蓝牙不可用");
        }
    }

    public void openWifiSetting(View view) {
        SystemFunUtil.gotoSettingNetWork(BounceScrollViewActivity.this);
    }

    public void openGPSSetting(View view) {
        SystemFunUtil.gotoSettingGPS(BounceScrollViewActivity.this);
    }

    public void openBluetoothSetting(View view) {
        SystemFunUtil.openBluetoothWithRequest(BounceScrollViewActivity.this, 0x0000001);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0x0000001) {
            UiUtil.showToast(BounceScrollViewActivity.this, "Bluetooth RequestCode: " + requestCode);
        }
    }
}
