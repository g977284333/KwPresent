package com.gechen.keepwalking.common.manager;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.gechen.keepwalking.common.utils.LogUtil;
import com.gechen.keepwalking.common.constants.BdColorType;

/**
 * 百度定位管理
 * Created by G-chen on 2015-5-12.
 */
public class LocationManager implements IManager{
    private static  final int DEFAULT_SPAN = 5000;

    private LocationClient mLocationClient = null;
    private LocationClientOption mOption = null;
    private BDLocationListener mLocationListener = new MyLocationListener();

    private static LocationManager INSTANCE = null;

    private LocationManager() {}

    public static LocationManager getInstance() {
        if(INSTANCE == null) {
            synchronized (LocationManager.class) {
                if(INSTANCE == null) {
                    INSTANCE = new LocationManager();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void onInit(Context context) {
        if(mLocationClient == null) {
            mLocationClient = new LocationClient(context);
        }

        mLocationClient.registerLocationListener(mLocationListener);
    }

    @Override
    public void onExit() {
        stop();
    }

    public void start() {
        if(mLocationClient != null && !mLocationClient.isStarted()) {
            mLocationClient.start();
        }
    }

    public void stop() {
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
        option.setCoorType(BdColorType.GC_COLOR);
        option.setScanSpan(DEFAULT_SPAN);
        option.setIsNeedAddress(true);
        option.setNeedDeviceDirect(true);
        return option;
    }

    private class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation){
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\ndirection : ");
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append(location.getDirection());
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
            }
            LogUtil.debugD("LocationManager", sb.toString());
        }
    }
}
