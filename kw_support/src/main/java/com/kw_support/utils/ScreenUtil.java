package com.kw_support.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * @version 1.0
 * @author: 葛晨
 * @类说明: px和dp的转换
 * @创建时间：2014-11-5 下午5:44:04
 */
public class ScreenUtil {
    public static final int COMPLEX_UNIT_PX = 0;  // px
    public static final int COMPLEX_UNIT_DIP = 1; // dp
    public static final int COMPLEX_UNIT_SP = 2;  // scaled pixel
    public static final int COMPLEX_UNIT_PT = 3;  // points
    public static final int COMPLEX_UNIT_IN = 4;  // inches
    public static final int COMPLEX_UNIT_MM = 5;  // millimeters

    public static float pxToDp(Context context, float px) {
        if (context == null) {
            return -1;
        }
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static float dpToPx(Context context, float dp) {
        if (context == null) {
            return -1;
        }
        return dp * context.getResources().getDisplayMetrics().density;
    }

    public static int pxToDpInt(Context context, float px) {
        return (int) (pxToDp(context, px) + 0.5f);
    }

    public static int dpToPxInt(Context context, float dp) {
        return (int) (dpToPx(context, dp) + 0.5f);
    }

    /**
     * @return float
     * @说 明: Pixels of conversion
     * @参 数: @param unit
     * @参 数: @param value
     * @参 数: @param metrics
     */
    public static float applayDimension(int unit, float value,
                                        DisplayMetrics metrics) {
        switch (unit) {
            case COMPLEX_UNIT_PX:
                return value;
            case COMPLEX_UNIT_DIP:
                return value * metrics.density + 0.5f;
            case COMPLEX_UNIT_SP:
                return value * metrics.scaledDensity;
            case COMPLEX_UNIT_PT:
                return value * metrics.xdpi * (1.0f / 72);
            case COMPLEX_UNIT_IN:
                return value * metrics.xdpi;
            case COMPLEX_UNIT_MM:
                return value * metrics.xdpi * (1.0f / 25.4f);
        }
        return 0;
    }

}
