package com.kw_support.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.kw_support.R;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by gechen on 2015/5/23.
 */
public class ImageUtil {

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
}
