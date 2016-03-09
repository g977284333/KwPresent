package com.gechen.keepwalking.kw.activity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.gechen.keepwalking.R;
import com.kw_support.base.BaseActivity;

/**
 * Created by Administrator on 2015/9/4.
 */
public class MyPtrActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myptr);

        ImageView macot = (ImageView) findViewById(R.id.iv_ptr_header_macot);
        AnimationDrawable drawable = (AnimationDrawable) macot.getDrawable();
        drawable.start();


    }
}
