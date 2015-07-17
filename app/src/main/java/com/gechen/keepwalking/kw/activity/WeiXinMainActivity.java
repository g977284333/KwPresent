package com.gechen.keepwalking.kw.activity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;

import com.gechen.keepwalking.R;
import com.gechen.keepwalking.kw.frament.TabFragment;
import com.kw_support.base.BaseActivity;
import com.kw_support.view.ChangeColorIconWithText;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by G-chen on 2015-6-28.
 * the sample for target 11+
 */
@SuppressLint("NewApi")
public class WeiXinMainActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    private ViewPager mContent;

    private List<Fragment> mTabs = new ArrayList<Fragment>();

    private String[] mTitles = new String[]{
            "FirstFragment", "SecondFragment", "ThirdFragment", "ForthFragment"
    };

    private List<ChangeColorIconWithText> mIndicators = new ArrayList<ChangeColorIconWithText>();

    private ContentFragmentAdapter mFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wei_xin_main);

        getActionBar().setDisplayShowHomeEnabled(false);
        setOverFlowButtonAlways();

        initView();
        initDatas();
        initEvent();
    }

    private void initEvent() {
        mContent.setOnPageChangeListener(this);
    }

    private void initDatas() {
        for (String title : mTitles) {
            TabFragment tabFragment = new TabFragment();
            Bundle bundle = new Bundle();
            bundle.putString(TabFragment.ARGUMENTS, title);
            tabFragment.setArguments(bundle);
            mTabs.add(tabFragment);
        }

        mFragmentAdapter = new ContentFragmentAdapter(getSupportFragmentManager());
        mContent.setAdapter(mFragmentAdapter);
    }

    private void initView() {
        mContent = (ViewPager) findViewById(R.id.vp_content);

        ChangeColorIconWithText indicatorOne = (ChangeColorIconWithText) findViewById(R.id.cciw_indicator_one);
        ChangeColorIconWithText indicatorTwo = (ChangeColorIconWithText) findViewById(R.id.cciw_indicator_two);
        ChangeColorIconWithText indicatorThree = (ChangeColorIconWithText) findViewById(R.id.cciw_indicator_three);
        ChangeColorIconWithText indicatorFour = (ChangeColorIconWithText) findViewById(R.id.cciw_indicator_four);

        mIndicators.add(indicatorOne);
        mIndicators.add(indicatorTwo);
        mIndicators.add(indicatorThree);
        mIndicators.add(indicatorFour);

        indicatorOne.setOnClickListener(this);
        indicatorTwo.setOnClickListener(this);
        indicatorThree.setOnClickListener(this);
        indicatorFour.setOnClickListener(this);

        indicatorOne.setIconAlpha(1.0f);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_wei_xin_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void setOverFlowButtonAlways() {
        ViewConfiguration config = ViewConfiguration.get(this);

        try {
            Field menuKey = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            menuKey.setAccessible(true);
            menuKey.setBoolean(config, false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (positionOffset > 0) {
            ChangeColorIconWithText left = mIndicators.get(position);
            ChangeColorIconWithText right = mIndicators.get(position + 1);

            left.setIconAlpha(1 - positionOffset);
            right.setIconAlpha(positionOffset);
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class ContentFragmentAdapter extends FragmentPagerAdapter {

        public ContentFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mTabs == null ? null : mTabs.get(position);
        }

        @Override
        public int getCount() {
            return mTabs.size();
        }

    }

    @Override
    public void onClick(View view) {
        indicatorOnClick(view);
    }

    private void indicatorOnClick(View view) {
        resetOthersIndicator();

        switch (view.getId()) {
            case R.id.cciw_indicator_one:
                mIndicators.get(0).setIconAlpha(1.0f);
                mContent.setCurrentItem(0, false);
                break;
            case R.id.cciw_indicator_two:
                mIndicators.get(1).setIconAlpha(1.0f);
                mContent.setCurrentItem(1, false);
                break;
            case R.id.cciw_indicator_three:
                mIndicators.get(2).setIconAlpha(1.0f);
                mContent.setCurrentItem(2, false);
                break;
            case R.id.cciw_indicator_four:
                mIndicators.get(3).setIconAlpha(1.0f);
                mContent.setCurrentItem(3, false);
                break;
        }
    }

    private void resetOthersIndicator() {
        for (int i = 0; i < mIndicators.size(); i++) {
            mIndicators.get(i).setIconAlpha(0);
        }
    }
}
