package com.gechen.keepwalking.kw.activity.baidumap;

import android.os.Bundle;

import com.baidu.mapapi.map.MapView;
import com.gechen.keepwalking.R;
import com.gechen.keepwalking.common.manager.LocationManager;
import com.gechen.keepwalking.kw.activity.base.BaseActivity;

/**
 * demo for baidu map
 * Created by G-chen on 2015-5-11.
 */
public class KwBaiduMapActivity extends BaseActivity {
    private MapView mMapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kwbaidumap_activitiy);

        initView();
        initLocation();
    }

    private void initLocation() {
        LocationManager.getInstance().onInit(getApplication());
        LocationManager.getInstance().start();
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
        LocationManager.getInstance().stop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }
}
