package com.kw_support.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;

/**
 * @类说明:利用监听屏幕开关状态来判断系统的锁屏和解锁
 * @author gechen
 *
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
			if(Intent.ACTION_SCREEN_ON.equals(action)) { // 开屏
				mScreenStateListener.OnScreenOn();
			} else if(Intent.ACTION_SCREEN_OFF.equals(action)) { // 锁屏
				mScreenStateListener.OnScreenOff();
			} else if(Intent.ACTION_USER_PRESENT.equals(action)) { // 解锁
				mScreenStateListener.OnUserPresent();
			}
		}
		
	}
	

	//	开启屏幕观察者
	public void start(ScreenStateListener screenStateListener) {
		mScreenStateListener = screenStateListener;
		registerReceiver();
		firstGetScreenState();
	}
	
	

	//因为广播是状态改变才会被监听到，所以第一次需要处理当前的屏幕状态
	@SuppressWarnings("deprecation")
	private void firstGetScreenState() {
		PowerManager pm = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
		if(pm.isScreenOn()) {
			if(null != mScreenStateListener) {
				mScreenStateListener.OnScreenOn();
			}
		} else {
			if(null != mScreenStateListener) {
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


	// 接口说明:屏幕状态改变监听接口
	public interface ScreenStateListener {
		void OnScreenOn();
		
		void OnScreenOff();
		
		void OnUserPresent();
	}
}
