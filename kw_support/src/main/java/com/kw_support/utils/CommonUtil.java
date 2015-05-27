package com.kw_support.utils;

import android.text.TextUtils;

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


}
