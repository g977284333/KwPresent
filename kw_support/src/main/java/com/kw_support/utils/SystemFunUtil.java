package com.kw_support.utils;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.inputmethod.InputMethodManager;

import com.kw_support.R;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by gechen on 2015/5/25.
 */
public class SystemFunUtil {

    public static String getFilePath(Activity activity, Uri imageUri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = activity.getContentResolver().query(imageUri, projection, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
        String imagePath = cursor.getString(columnIndex);
        cursor.close();
        return imagePath;
    }

    public static String openImageCapture(Activity activity, int requestCode) {
        String imagePath = null;
        try {
            File storagePath = SdCardUtil.getExternalStoragePath();
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
            String fileName = dateFormat.format(new Date()) + ".jpg";
            File imageFile = new File(storagePath, fileName);
            imagePath = imageFile.getAbsolutePath();
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
            activity.startActivityForResult(intent, requestCode);
        } catch (IOException e) {
            UiUtil.showAlertDialog((Context)activity, R.string.sdcard_error);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
        return imagePath;
    }

    public static void openPhotoLibrary(Activity activity, int requestCode) {
        try {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            activity.startActivityForResult(intent, requestCode);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void showSoftInput(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(activity.getCurrentFocus(), inputMethodManager.SHOW_FORCED);
    }

    public static void hideSoftInput(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getWindow().peekDecorView().getWindowToken(), 0);
    }


    public static void gotoSettingNetWork(Context context) {
        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
        context.startActivity(intent);
    }

    public static void gotoSettingGPS(Context context) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        context.startActivity(intent);
    }


    public static void openBluetoothWithRequest(Activity activity, int RequestCode) {
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        activity.startActivityForResult(intent, RequestCode);
    }

    /**
     * 为程序创建桌面快捷方式
     * 需要声明权限："com.android.launcher.permission.INSTALL_SHORTCUT"
     */
    private void addShortcut(Activity context, int iconId, Class launcherClazz){
        Intent addShortCut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        //不能重复创建快捷方式
        addShortCut.putExtra("duplicate", false);
        String title = context.getString(R.string.app_name);//名称
        Parcelable icon = Intent.ShortcutIconResource.fromContext(context, iconId);//图标
        //点击快捷方式后操作Intent,快捷方式建立后，再次启动该程序
        Intent intent = new Intent(context,launcherClazz);
        //设置快捷方式的标题
        addShortCut.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
        //设置快捷方式的图标
        addShortCut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
        //设置快捷方式对应的Intent
        addShortCut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
        //发送广播添加快捷方式
        context.sendBroadcast(addShortCut);
    }
}
