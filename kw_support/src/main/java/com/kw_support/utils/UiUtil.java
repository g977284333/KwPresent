package com.kw_support.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.kw_support.R;

/**
 * Created by G-chen on 2015-5-18.
 */
public class UiUtil {

    public static void showToast(Context context, String msg) {
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
}
