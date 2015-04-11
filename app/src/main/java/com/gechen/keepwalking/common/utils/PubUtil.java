package com.gechen.keepwalking.common.utils;

/**
 * Created by G-chen on 2015-3-24.
 */
public class PubUtil {

    /**
     * 比较两个对象
     */
    public static boolean isEquals(Object actual, Object expected) {
        return actual == expected || (null == actual ? null == expected : actual.equals(expected));
    }
}
