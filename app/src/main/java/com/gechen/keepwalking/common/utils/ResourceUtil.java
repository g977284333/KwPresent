package com.gechen.keepwalking.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Context;
import android.text.TextUtils;

/**
 * @author: 葛晨  
 * @类   说   明:	读取assets、raw文件夹中文件
 * @version 1.0
 * @创建时间：2014-11-5 下午6:06:43
 * 
 */
public class ResourceUtil {
	
	/**
	 * 读取Assets包下的文件
	 */
	public static String getFileFromAssets(Context context, String fileName) {
		if(null == context || TextUtils.isEmpty(fileName)) {
			return null;
		}
		
		StringBuilder sb = new StringBuilder();
		try {
			InputStreamReader in = new InputStreamReader(context.getAssets().open(fileName));
			BufferedReader br = new BufferedReader(in);
			String line;
			while(null != (line = br.readLine())) {
				sb.append(line);
			}
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 读取raw包下的文件
	 */
	public static String getFileFromRaw(Context context, int resId) {
		if(null == context) {
			return null;
		}
		
		StringBuilder sb = new StringBuilder();
		try {
			InputStreamReader in = new InputStreamReader(context.getResources().openRawResource(resId));
			BufferedReader br = new BufferedReader(in);
			String line;
			while(null != (line = br.readLine())) {
				sb.append(line);
			}
			return sb.toString();
		}  catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
}
