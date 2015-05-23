package com.gechen.keepwalking.kw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.gechen.keepwalking.R;
import com.kw_support.base.BaseActivity;
import com.kw_support.utils.UiUtil;


public class KwHomeActivity extends BaseActivity {
    private Button mBounceScrollView;
    private Button mBaiduMap;
    private Button mKwAdapterBtn;
    private Button mPhotoOrImage;

    private long[] mHits = new long[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kw_home);

        setupView();
    }

   private void setupView() {
       mBounceScrollView = (Button) findViewById(R.id.btn_bounce_scroll_view);
       mBaiduMap = (Button) findViewById(R.id.btn_baidu_map);
       mKwAdapterBtn = (Button) findViewById(R.id.btn_list_or_scroll_view);
       mPhotoOrImage = (Button) findViewById(R.id.btn_photo_or_image);

       mBounceScrollView.setOnClickListener(this);
       mBaiduMap.setOnClickListener(this);
       mKwAdapterBtn.setOnClickListener(this);
       mPhotoOrImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_bounce_scroll_view:
                gotoTargetActivity(BounceScrollViewActivity.class);
                break;
            case R.id.btn_baidu_map:
                gotoTargetActivity(KwBaiduMapActivity.class);
                break;
            case R.id.btn_list_or_scroll_view:
                gotoTargetActivity(ListOrScrollViewSimpleActivity.class);
                break;
            case R.id.btn_photo_or_image:
                gotoTargetActivity(PhotoOrImageSimpleActivity.class);
                break;
            default:
                break;
        }
    }

    private void gotoTargetActivity(Class clazz) {
        Intent intent = new Intent(getApplicationContext(), clazz);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        System.arraycopy(mHits, 1, mHits, 0, mHits.length -1);
        mHits[mHits.length - 1] = SystemClock.uptimeMillis();

        if(mHits[0] > SystemClock.uptimeMillis() - 500) {
            finish();
        } else {
            UiUtil.showToast(this, "再按一次退出");
        }
    }

//    @Override
    // 按返回键，回到主界面
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            Intent intent = new Intent(Intent.ACTION_MAIN);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.addCategory(Intent.CATEGORY_HOME);
//            startActivity(intent);
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
}
