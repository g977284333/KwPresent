package com.gechen.keepwalking.kw.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.gechen.keepwalking.R;
import com.gechen.keepwalking.kw.adapter.ImagePagerAdapter;
import com.kw_support.base.BaseActivity;
import com.kw_support.view.AutoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gechen on 2015/5/25.
 */
@Deprecated
public class AutoScrollViewPagerActivity extends BaseActivity{
    private AutoScrollViewPager mViewpager;

    private List<Integer> mBanners;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_scroll_view_pager);

        initData();
        initView();
    }

    private void initData() {
        mBanners = new ArrayList<Integer>();
        mBanners.add(R.drawable.banner1);
        mBanners.add(R.drawable.banner2);
        mBanners.add(R.drawable.banner3);
        mBanners.add(R.drawable.banner4);
    }

    private void initView() {
        mViewpager = (AutoScrollViewPager) findViewById(R.id.auto_scroll_view_pager);
        mViewpager.setAdapter(new ImagePagerAdapter(this, mBanners).setInfiniteLoop(true));
        mViewpager.setOnPageChangeListener(new MyOnPageChageListener());

        mViewpager.setInterval(3000);
        mViewpager.startAutoScroll();
        mViewpager.setCurrentItem(getCurrentItem());
    }

    public int getCurrentItem() {
        return Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % mBanners.size();
    }

    public class MyOnPageChageListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mViewpager.stopAutoScroll();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewpager.startAutoScroll();
    }
}
