package com.kw_support.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import android.os.Handler;
import android.os.Message;

/**
 * @version 1.0
 * @author: 葛晨
 * @类说明:	消息池
 * @创建时间：2014-11-15 下午10:55:10
 */
public class MessageHandlerPool {
    private static MessageHandlerPool mHandlerList = null;

    private MessageHandlerPool() {

    }

    public static MessageHandlerPool getInstance() {
        if (null == mHandlerList) {
            synchronized (MessageHandlerPool.class) {
                if (null == mHandlerList) {
                    mHandlerList = new MessageHandlerPool();
                }
            }
        }
        return mHandlerList;
    }

    private Map<String, Handler> mMap = new HashMap<String, Handler>();

    public static void addHandler(String className, Handler handler) {
        if (null == getInstance().mMap.get(className)) {
            getInstance().mMap.put(className, handler);
        }
    }

    public static synchronized void removeHandler(String className) {
        getInstance().mMap.remove(className);
    }

    public static void sendMessage(Handler handler, int what, Object obj) {
        sendMessage(handler, what, obj, 0);
    }

    public static void sendMessage(Handler handler, int what, Object obj, long delay) {
        Message msg = handler.obtainMessage();
        msg.what = what;
        msg.obj = msg;
        handler.sendMessageDelayed(msg, delay);
    }

    public synchronized static void sendGlobalMessage(int what, Object obj) {
        synchronized (getInstance().mMap) {
            for (Entry<String, Handler> entry : getInstance().mMap.entrySet()) {
                Handler handler = entry.getValue();
                sendMessage(handler, what, obj);
            }
        }
    }

    public static void sendGloabalMessage(int what) {
        sendGlobalMessage(what, null);
    }

    public static void sendMessage(String className, int what, Object obj) {
        Handler handler = getInstance().mMap.get(className);
        if (null != handler) {
            sendMessage(handler, what, obj);
        }
    }

    public static void sendMessage(Class cls, int what, Object obj) {
        sendMessage(cls.getName(), what, obj);
    }

    public static void sendMessage(Class cls, int what) {
        sendMessage(cls.getName(), what, null);
    }
}
