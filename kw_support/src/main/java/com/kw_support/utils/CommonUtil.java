package com.kw_support.utils;

import android.text.TextUtils;

import java.security.MessageDigest;
import java.util.List;

/**
 * Created by G-chen on 2015-5-20.
 */
public class CommonUtil {

    public static <V> boolean isEmpty(List<V> sourceList) {
        return (sourceList == null || sourceList.size() == 0);
    }

    public static boolean isEmpty(String str) {
        if (!TextUtils.isEmpty(str) && !str.equals("null")) {
            return true;
        }
        return false;
    }

    public static String md5(String str) {
        if (TextUtils.isEmpty(str)) {
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
        for (int i = 0; i < digest.length; i++) {
            hex = Integer.toHexString((digest[i] & 0xff));
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
