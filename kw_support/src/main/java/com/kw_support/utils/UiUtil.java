package com.kw_support.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.kw_support.R;

/**
 * Created by G-chen on 2015-5-18.
 */
public class UiUtil {

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, int msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static Dialog createLoadingDialog(Context context) {
        String message = context.getResources().getString(R.string.loading);
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        return progressDialog;
    }

    public static void showAlertDialog(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setTitle(R.string.app_name);
        builder.setMessage(message);
        builder.setNeutralButton(android.R.string.ok, null);
        builder.create().show();
    }

    public static void showAlertDialog(Context context, int resource) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setTitle(R.string.app_name);
        builder.setMessage(resource);
        builder.setNeutralButton(android.R.string.ok, null);
        builder.create().show();
    }

    public static void showAlertDialog(Context context, String message, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setTitle(R.string.app_name);
        builder.setMessage(message);
        builder.setNeutralButton(android.R.string.ok, listener);
        builder.create().show();
    }

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
    public static float applyDimension(int unit, float value,
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
