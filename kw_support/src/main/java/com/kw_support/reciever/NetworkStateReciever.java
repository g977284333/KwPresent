package com.kw_support.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.kw_support.utils.NetWorkUtil;


/**
 * @version 1.0
 * @author: 葛晨
 * @类说明: 网络监听
 * @创建时间：2014-11-12 上午12:10:46
 */
public class NetworkStateReciever extends BroadcastReceiver {
    public static final String TAG = "NetworkStateReciever";

    public static boolean networkAvailable = false;

    private static NetWorkUtil.NetType mNetType;
    private static NetWorkStateListener mNetworkStateListener;
    private static NetworkStateReciever mNetworkStateReciever;


    @Override
    public void onReceive(Context context, Intent intent) {
        // 注册网络状态
        if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
            if (NetWorkUtil.isNetWorkAvailable(context)) {
                mNetType = NetWorkUtil.getNetType(context);
                if (null != mNetworkStateListener) {
                    mNetworkStateListener.onNetWorkConnected(mNetType);
                }
                networkAvailable = true;
            } else {
                if (null != mNetworkStateListener) {
                    mNetworkStateListener.onNetWorkDisconnected();
                }
                networkAvailable = false;
            }
        }
        // 每隔一段时间进行一次监听操作
        if (intent.getAction().equals("android.intent.action.TIME_TICK")) {

//			//保证服务不死
//			boolean isRun = GeService.isServiceRunning(context);
//			if(!isRun){
//				LogUtil.debugI(TAG, "开启服务-------------定时检查开启服务是否运行");
//				context.startService(new Intent(context,GeService.class));
//			}
        }
    }

    /**
     * @return void 返回类型
     * @throws
     * @Title: registListener
     * @说 明: 注册网络监听广播 context 建议为getApplicationContext(),注意context内存泄露
     * @参 数: @param listener
     */
    public static boolean registerListener(NetWorkStateListener listener, Context context) {
        if (null == context) {
            return false;
        }

        mNetworkStateListener = listener;
        mNetworkStateReciever = new NetworkStateReciever();

        IntentFilter mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        mIntentFilter.addAction("android.intent.action.TIME_TICK");
        context.registerReceiver(mNetworkStateReciever, mIntentFilter);
        return true;
    }

    /**
     * @return void    返回类型
     * @throws
     * @Title: unRegisterListener
     * @说 明: 取消注册网络监听广播
     * @参 数: @param context
     */
    public static void unRegisterListener(Context context) {
        if (null != context && null != mNetworkStateReciever) {
            context.unregisterReceiver(mNetworkStateReciever);
        }
    }

    public interface NetWorkStateListener {

        void onNetWorkConnected(NetWorkUtil.NetType netType);

        void onNetWorkDisconnected();
    }
}
