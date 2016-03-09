package com.kw_support.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;

/**
 * @author gechen
 * 监控屏幕开关
 */
public class ScreenObserver {
    private Context mContext;
    private ScreenReceiver mScreenReceiver;
    private ScreenStateListener mScreenStateListener;

    public ScreenObserver(Context context) {
        this.mContext = context;
        mScreenReceiver = new ScreenReceiver();
    }

    private class ScreenReceiver extends BroadcastReceiver {
        private String action;

        @Override
        public void onReceive(Context context, Intent intent) {
            action = intent.getAction();
            if (Intent.ACTION_SCREEN_ON.equals(action)) {
                mScreenStateListener.OnScreenOn();
            } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                mScreenStateListener.OnScreenOff();
            } else if (Intent.ACTION_USER_PRESENT.equals(action)) { // unlock
                mScreenStateListener.OnUserPresent();
            }
        }

    }

    public void start(ScreenStateListener screenStateListener) {
        mScreenStateListener = screenStateListener;
        registerReceiver();
        firstGetScreenState();
    }

    /**
     * Only in broadcasting state changes to be listening to the broadcast
     * so it is necessary to call this method when start observer
     */
    private void firstGetScreenState() {
        PowerManager pm = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
        if (pm.isScreenOn()) {
            if (null != mScreenStateListener) {
                mScreenStateListener.OnScreenOn();
            }
        } else {
            if (null != mScreenStateListener) {
                mScreenStateListener.OnScreenOff();
            }
        }
    }

    private void registerReceiver() {
        mScreenReceiver = new ScreenReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        mContext.registerReceiver(mScreenReceiver, filter);
    }

    public void unRegisterReceiver() {
        mContext.unregisterReceiver(mScreenReceiver);
    }

    public interface ScreenStateListener {
        void OnScreenOn();

        void OnScreenOff();

        void OnUserPresent();
    }
}
