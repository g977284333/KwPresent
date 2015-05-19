package com.kw_support.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by G-chen on 2015-5-18.
 */
public class UiUtil {

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
