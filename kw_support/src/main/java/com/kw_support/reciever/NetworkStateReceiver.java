package com.kw_support.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.kw_support.utils.NetWorkUtil;


/**
 * @version 1.0
 * @author: gchen
 * @discription: Network Monitoring
 * @date：2014-11-12 12:10:46
 */
public class NetworkStateReceiver extends BroadcastReceiver {
    public static final String TAG = NetworkStateReceiver.class.getSimpleName();

    public static boolean networkAvailable = false;

    private static NetWorkUtil.NetType mNetType;
    private static NetWorkStateListener mNetworkStateListener;
    private static NetworkStateReceiver mNetworkStateReceiver;


    @Override
    public void onReceive(Context context, Intent intent) {
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
    }

    public static boolean registerListener(NetWorkStateListener listener, Context context) {
        if (null == context) {
            return false;
        }

        mNetworkStateListener = listener;
        mNetworkStateReceiver = new NetworkStateReceiver();

        IntentFilter mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        mIntentFilter.addAction("android.intent.action.TIME_TICK");
        context.registerReceiver(mNetworkStateReceiver, mIntentFilter);
        return true;
    }

    public static void unRegisterListener(Context context) {
        if (null != context && null != mNetworkStateReceiver) {
            context.unregisterReceiver(mNetworkStateReceiver);
        }
    }

    public interface NetWorkStateListener {

        void onNetWorkConnected(NetWorkUtil.NetType netType);

        void onNetWorkDisconnected();
    }
}
