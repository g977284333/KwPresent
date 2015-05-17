package com.gechen.keepwalking.common.utils;

import android.text.TextUtils;

import java.security.MessageDigest;

/**
 * @author: 葛晨  
 * @类   说   明:	md5加密、Base64编码
 * @version 1.0
 * @创建时间：2014-11-13 下午11:15:44
 * 
 */
public class SecretUtil {

	public static String md5(String str) {
		if(TextUtils.isEmpty(str)) {
			return null;
		}
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(str.getBytes());
			return encodeHex(messageDigest.digest());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static String encodeHex(byte[] digest) {
		StringBuilder hexString = new StringBuilder();
		String hex = null;
		for(int i = 0; i < digest.length; i++) {
			hex = Integer.toHexString((digest[i] & 0xff));
			if(hex.length() == 1) {
				hex = '0' + hex;
			}
			hexString.append(hex);
		}
		return hexString.toString();
	}
}
