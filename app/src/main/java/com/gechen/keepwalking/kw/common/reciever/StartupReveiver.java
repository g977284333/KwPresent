package com.gechen.keepwalking.kw.common.reciever;

import com.gechen.keepwalking.kw.activity.KwHomeActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @author: 葛晨  
 * @类   说   明:	开机监听事件，开机启动
 * @version 1.0
 * @创建时间：2014-11-10 下午11:11:55
 * 
 */
public class StartupReveiver extends BroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {
		if(intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
			Intent startupIntent = new Intent(context, KwHomeActivity.class);
			startupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(startupIntent);
		}
	}

}
