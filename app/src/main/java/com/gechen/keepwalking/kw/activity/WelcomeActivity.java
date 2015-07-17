package com.gechen.keepwalking.kw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.gechen.keepwalking.R;
import com.gechen.keepwalking.kw.KwApplication;
import com.kw_support.base.BaseActivity;
import com.kw_support.manager.SystemBarTintManager;
import com.kw_support.view.CirclePageIndicator;

/**
 * Created by G-chen on 2015-7-15.
 */
public class WelcomeActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    private ViewPager mGuideContent;

    private ImageView mBtnClose;
    private ImageView mBtnConfirm;

    private int[] mGuides = {R.drawable.guide_one, R.drawable.guide_two, R.drawable.guide_three,
            R.drawable.guide_four, R.drawable.guide_five};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        initView();
    }

    private void initView() {
        mBtnClose = (ImageView) findViewById(R.id.btn_guide_close);
        mBtnConfirm = (ImageView) findViewById(R.id.btn_guide_confirm);

        mBtnClose.setOnClickListener(this);
        mBtnConfirm.setOnClickListener(this);

        mGuideContent = (ViewPager) findViewById(R.id.vp_guide);
        mGuideContent.setAdapter(new GuideAdapter());

        CirclePageIndicator pageIndicator = (CirclePageIndicator) findViewById(R.id.page_indicator);
        pageIndicator.setStrokeWidth(0.0f);
        pageIndicator.setFillColor(getResources().getColor(R.color.blue));
        pageIndicator.setPageColor(getResources().getColor(R.color.light_gray));
        pageIndicator.setOnPageChangeListener(this);
        pageIndicator.setViewPager(mGuideContent);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        mBtnConfirm.setVisibility(position == mGuides.length - 1 ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.btn_guide_close:
                intent = new Intent(WelcomeActivity.this, WeiXinMainActivity.class);
                break;
            case R.id.btn_guide_confirm:
                intent = new Intent(WelcomeActivity.this, WeiXinMainActivity.class);
                break;
        }
        if(intent != null) {
            startActivity(intent);
        }
        finish();
    }

    private class GuideAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mGuides.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == (View) object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageview = new ImageView(WelcomeActivity.this);
            imageview.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageview.setImageResource(mGuides[position]);
            ((ViewPager) container).addView(imageview, 0);
            return imageview;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        KwApplication.getInstance().getConfigManager().setFirstRun(false);
    }
}
