package com.gechen.keepwalking.kw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.gechen.keepwalking.R;
import com.kw_support.base.BaseActivity;
import com.kw_support.utils.UiUtil;
import com.kw_support.view.OverLayLayout;

@Deprecated
public class KwHomeActivity extends BaseActivity {

    private Button mBounceScrollView;
    private Button mBaiduMap;
    private Button mKwAdapterBtn;
    private Button mPhotoOrImage;
    private Button mCustomWidget;
    private Button mWeiXin;
    private Button mOpenFragment;

    private OverLayLayout mOverlayLayout;

    private ImageView mBanner;

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
        mCustomWidget = (Button) findViewById(R.id.btn_custom_widget);
        mWeiXin = (Button) findViewById(R.id.btn_wei_xin_activity);
        mOpenFragment = (Button) findViewById(R.id.btn_fragment_with_anim_activity);

        mOverlayLayout = (OverLayLayout) findViewById(R.id.overlay);
        mBanner = (ImageView) findViewById(R.id.iv_banner);

        mBounceScrollView.setOnClickListener(this);
        mBaiduMap.setOnClickListener(this);
        mKwAdapterBtn.setOnClickListener(this);
        mPhotoOrImage.setOnClickListener(this);
        mCustomWidget.setOnClickListener(this);
        mWeiXin.setOnClickListener(this);
        mOpenFragment.setOnClickListener(this);
        mOverlayLayout.setOnOverlayStateChangedListener(mOnOverlayStateChangeListener);

        mOverlayLayout.setVisibility(View.VISIBLE);
        mOverlayLayout.autoHideOverlay(3000);
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
            case R.id.btn_custom_widget:
                gotoTargetActivity(CustomWidgetActivity.class);
                break;
            case R.id.btn_wei_xin_activity:
                gotoTargetActivity(WeiXinMainActivity.class);
                break;
            case R.id.btn_fragment_with_anim_activity:
                gotoTargetActivity(FragmentWithAnimationActivity.class);
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
        System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
        mHits[mHits.length - 1] = SystemClock.uptimeMillis();

        if (mHits[0] > SystemClock.uptimeMillis() - 2000) {
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

    private OverLayLayout.OnOverlayStateChangedListener mOnOverlayStateChangeListener = new OverLayLayout.OnOverlayStateChangedListener() {

        @Override
        public void onOverlayStateChanged(boolean isShow) {
            mOverlayLayout.setVisibility(isShow ? View.VISIBLE : View.GONE);

        }
    };
}
