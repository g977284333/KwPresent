package com.gechen.keepwalking.kw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.gechen.keepwalking.R;
import com.kw_support.base.BaseActivity;

/**
 * Created by gechen on 2015/5/23.
 */
@Deprecated
public class PhotoOrImageSimpleActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_or_image);
    }

    public void skipToMultiImageSelector(View view) {
        gotoTargetActivity(MainMultiImageSelectorActivity.class);
    }

    public void skipToPhotoViewActivity(View view) {
        gotoTargetActivity(PhotoViewActivity.class);
    }

    private void gotoTargetActivity(Class clazz) {
        Intent intent = new Intent(getApplicationContext(), clazz);
        startActivity(intent);
    }


}
