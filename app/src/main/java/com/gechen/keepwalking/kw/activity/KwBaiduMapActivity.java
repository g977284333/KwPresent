package com.gechen.keepwalking.kw.activity;

import android.os.Bundle;

import com.baidu.mapapi.map.MapView;
import com.gechen.keepwalking.R;
import com.gechen.keepwalking.kw.common.manager.LocationManager;
import com.kw_support.base.BaseActivity;

/**
 * demo for baidu map
 * Created by G-chen on 2015-5-11.
 */
@Deprecated
public class KwBaiduMapActivity extends BaseActivity {
    private MapView mMapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitiy_baidumap);

        initView();
        initLocation();
    }

    private void initLocation() {
        LocationManager.getInstance().onInit();
        LocationManager.getInstance().startLocation();
    }

    private void initView() {
        mMapView = (MapView) findViewById(R.id.mv_baidumap);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onStop() {
        LocationManager.getInstance().stopLocation();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }
}
