package com.gechen.keepwalking.common.utils;

import com.gechen.keepwalking.kw.KwApplication;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Rect;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * @author: 葛晨  
 * @类   说   明:	
 * @version 1.0
 * @创建时间：2014-11-10 下午10:52:39
 * 
 */
public class ViewUtil {

	/**
	 * 
	 * @Title: getTitleBar 
	 * @说       明: 获取标题栏的高度
	 * @参       数: @param activity
	 * @参       数: @return   
	 * @return int    返回类型 
	 * @throws
	 */
	public static int getTitleBarHeight(Activity activity) {
		Rect outRect = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
		// 状态栏的高度
		int statusBarHeight = outRect.top;
		
		// 获得activity的View不包含标题栏的部分
		int contentTop = activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
		return  contentTop - statusBarHeight;
	}
	
	/**
	 * 
	 * @Title: setDialogLocation 
	 * @说       明: 设置Dialog的位置
	 * @参       数: @param dialog
	 * @参       数: @param locX
	 * @参       数: @param locY   
	 * @return void    返回类型 
	 * @throws
	 */
	public static void setDialogLocation(Dialog dialog, int locX, int locY) {
		Window mWindow = dialog.getWindow();
		WindowManager.LayoutParams lp = mWindow.getAttributes();
		lp.x = locX;
		lp.y = locY;
		dialog.onWindowAttributesChanged(lp);
	}
	
	/**
	 * @Title: showToast 
	 * @说       明: 默认的Toast
	 * @参       数: @param msg   
	 * @return void    返回类型 
	 * @throws
	 */
	public static void showToast(String msg) {
		Toast.makeText(KwApplication.mContext, msg, Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * @Title: goToActivity 
	 * @说       明: Activity跳转
	 * @参       数: @param act
	 * @参       数: @param cls   
	 * @return void    返回类型 
	 * @throws
	 */
	public static void goToActivity(Activity act, Class<?> cls) {
		Intent intent = new Intent();
		intent.setClass(act, cls);
		act.startActivity(intent);
	}
}
