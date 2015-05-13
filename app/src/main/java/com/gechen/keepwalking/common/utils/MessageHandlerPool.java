package com.gechen.keepwalking.common.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import android.os.Handler;
import android.os.Message;

/**
 * @author: 葛晨  
 * @类   说   明:	消息池
 * @version 1.0
 * @创建时间：2014-11-15 下午10:55:10
 * 
 */
public class MessageHandlerPool {
	private static MessageHandlerPool mHandlerList = null;
	
	private MessageHandlerPool() {
		
	};
	
	public static MessageHandlerPool getInstance() {
		if(null == mHandlerList) {
			synchronized (MessageHandlerPool.class) {
				if(null == mHandlerList) {
					mHandlerList = new MessageHandlerPool();
				}
			}
		}
		return mHandlerList;
	}

	private Map<String, Handler> mMap = new HashMap<String, Handler>();

	/**
	 * @Title: addHandler 
	 * @说       明: 根据类名添加handler到消息池
	 * @参       数: @param className
	 * @参       数: @param handler   
	 * @return void    返回类型 
	 * @throws
	 */
	public static void addHandler(String className, Handler handler) {
		if(null == getInstance().mMap.get(className)) {
			getInstance().mMap.put(className, handler);
		}
	}
	
	/**
	 * @Title: removeHandler 
	 * @说       明: 根键名将对应handler移除消息池
	 * @参       数: @param className   
	 * @return void    返回类型 
	 * @throws
	 */
	public static synchronized void removeHandler(String className) {
		getInstance().mMap.remove(className);
	}
	
	/**
	 * @Title: sendMessage 
	 * @说       明: 根据指定的handler发送消息
	 * @参       数: @param handler
	 * @参       数: @param what
	 * @参       数: @param obj   
	 * @return void    返回类型 
	 * @throws
	 */
	public static void sendMessage(Handler handler, int what, Object obj) {
		sendMessage(handler, what, obj, 0);
	}
	
	/**
	 * @Title: sendMessage 
	 * @说       明: 根据指定的handler发送消息
	 * @参       数: @param handler
	 * @参       数: @param what
	 * @参       数: @param obj
	 * @参       数: @param delay   
	 * @return void    返回类型 
	 * @throws
	 */
	public static void sendMessage(Handler handler, int what, Object obj, long delay) {
		Message msg = handler.obtainMessage();
		msg.what = what;
		msg.obj = msg;
		handler.sendMessageDelayed(msg, delay);
	}
	
	/**
	 * @Title: sendGlobalMessage 
	 * @说       明: 全局发送消息 -- (只要存活的Activity、Fragment有抓取指定的Msg就会执行接收的消息)
	 * @参       数: @param what
	 * @参       数: @param obj   
	 * @return void    返回类型 
	 * @throws
	 */
	public synchronized static void sendGlobalMessage(int what, Object obj) {
		synchronized (getInstance().mMap) {
			for(Entry<String, Handler> entry : getInstance().mMap.entrySet()) {
				Handler handler = entry.getValue();
				sendMessage(handler, what, obj);
			}
		}
	}
	
	/**
	 * @Title: sendGloabalMessage 
	 * @说       明: 发送空消息
	 * @参       数: @param what   
	 * @return void    返回类型 
	 * @throws
	 */
	public static void sendGloabalMessage(int what) {
		sendGlobalMessage(what, null);
	}
	
	/**
	 * @Title: sendMessage 
	 * @说       明: 根据指定的类名发送消息
	 * @参       数: @param className
	 * @参       数: @param what
	 * @参       数: @param obj   
	 * @return void    返回类型 
	 * @throws
	 */
	public static void sendMessage(String className, int what, Object obj) {
		Handler handler = getInstance().mMap.get(className);
		if(null != handler) {
			sendMessage(handler, what, obj);
		}
	}
	
	/**
	 * @Title: sendMessage 
	 * @说       明: 根据指定的类名发送消息
	 * @参       数: @param cls
	 * @参       数: @param what
	 * @参       数: @param obj   
	 * @return void    返回类型 
	 * @throws
	 */
	@SuppressWarnings("rawtypes") 
	public static void sendMessage(Class cls, int what, Object obj) {
		sendMessage(cls.getName(), what, obj);
	}
	
	/**
	 * @Title: sendmessage
	 * @说       明: 根据指定的类型发送空消息
	 * @参       数: @param cls
	 * @参       数: @param what   
	 * @return void    返回类型 
	 * @throws
	 */
	@SuppressWarnings("rawtypes") 
	public static void sendMessage(Class cls, int what) {
		sendMessage(cls.getName(), what, null);
	}
}
