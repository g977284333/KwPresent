package com.gechen.keepwalking.kw.common.manager;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.gechen.keepwalking.kw.common.constants.BdColorType;
import com.gechen.keepwalking.kw.KwApplication;
import com.kw_support.manager.IManager;

/**
 * Created by G-chen on 2015-5-12.
 */
public class LocationManager implements IManager {
    private static  final int DEFAULT_SPAN = 5000;

    private LocationClient mLocationClient = null;
    private LocationClientOption mOption = null;
    private BDLocation mBDLocation = null;

    private static LocationManager instance = null;

    private LocationManager() {}

    public static LocationManager getInstance() {
        if(instance == null) {
            synchronized (LocationManager.class) {
                if(instance == null) {
                    instance = new LocationManager();
                }
            }
        }
        return instance;
    }

    @Override
    public void onInit() {
        KwApplication application = KwApplication.getInstance();
        mLocationClient = new LocationClient(application);
        mBDLocation = mLocationClient.getLastKnownLocation();

        mLocationClient.setLocOption(getDefaultLocationClientOption());
        mLocationClient.registerLocationListener(mBDLocationListener);
    }

    @Override
    public void onExit() {
        stopLocation();
    }

    public void startLocation() {
        if(mLocationClient != null && !mLocationClient.isStarted()) {
            mLocationClient.start();
        }
    }

    public void stopLocation() {
        if(isReadyForLocation()) {
            mLocationClient.stop();
        }
    }

    private boolean isReadyForLocation() {
        return mLocationClient != null && mLocationClient.isStarted();
    }

    public void requestLocation() {
        if(isReadyForLocation()) {
            mLocationClient.requestLocation();
        }
    }

    public void requestOffLineLocation() {
        if (isReadyForLocation()) {
            mLocationClient.requestOfflineLocation();
        }
    }

    public void setLocationClientOption(LocationClientOption option) {
        this.mOption = option;
    }

    private LocationClientOption getDefaultLocationClientOption() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationMode.Hight_Accuracy);
        option.setCoorType(BdColorType.BD_11_COLOR);
        option.setScanSpan(DEFAULT_SPAN);
        option.setIsNeedAddress(true);
        option.setNeedDeviceDirect(true);
        return option;
    }

    private BDLocationListener mBDLocationListener  = new  BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
           mBDLocation = location;
        }
    };
}
