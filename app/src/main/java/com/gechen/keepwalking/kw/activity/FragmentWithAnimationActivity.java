package com.gechen.keepwalking.kw.activity;

import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.gechen.keepwalking.R;
import com.gechen.keepwalking.kw.frament.TabAppFragment;
import com.gechen.keepwalking.kw.frament.TabFragment;
import com.kw_support.base.BaseActivity;
import com.kw_support.view.ChangeColorIconWithText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by G-chen on 2015-7-4.
 * the example for api target 11
 */
@Deprecated
public class FragmentWithAnimationActivity extends BaseActivity {

    private TabAppFragment mFirstFragment;
    private TabAppFragment mSecondFragment;
    private TabAppFragment mThirdFragment;
    private TabAppFragment mFourthFragment;


    private String[] mTitles = new String[]{
            "FirstFragment", "SecondFragment", "ThirdFragment", "ForthFragment"
    };

    private List<ChangeColorIconWithText> mIndicators = new ArrayList<ChangeColorIconWithText>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_with_anim);

        // 沉寖式状态栏
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        initView();
    }

    private void initView() {
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

        select(0);
    }

    @Override
    public void onClick(View view) {
        resetOthersIndicator();

        switch (view.getId()) {
            case R.id.cciw_indicator_one:
                mIndicators.get(0).setIconAlpha(1.0f);
                select(0);
                break;
            case R.id.cciw_indicator_two:
                mIndicators.get(1).setIconAlpha(1.0f);
                select(1);
                break;
            case R.id.cciw_indicator_three:
                mIndicators.get(2).setIconAlpha(1.0f);
                select(2);
                break;
            case R.id.cciw_indicator_four:
                mIndicators.get(3).setIconAlpha(1.0f);
                select(3);
                break;
        }
    }

    private void select(int i) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        addAnim(transaction);
        hideFragment(transaction);

        switch (i) {
            case 0:
                if(mFirstFragment == null) {
                    mFirstFragment = new TabAppFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(TabFragment.ARGUMENTS, mTitles[i]);
                    mFirstFragment.setArguments(bundle);
                    transaction.add(R.id.fl_content, mFirstFragment);
                } else {
                    transaction.show(mFirstFragment);
                }
                break;
            case 1:
                if(mSecondFragment == null) {
                    mSecondFragment = new TabAppFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(TabFragment.ARGUMENTS, mTitles[i]);
                    mSecondFragment.setArguments(bundle);
                    transaction.add(R.id.fl_content, mSecondFragment);
                } else {
                    transaction.show(mSecondFragment);
                }
                break;
            case 2:
                if(mThirdFragment == null) {
                    mThirdFragment = new TabAppFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(TabFragment.ARGUMENTS, mTitles[i]);
                    mThirdFragment.setArguments(bundle);
                    transaction.add(R.id.fl_content, mThirdFragment);
                } else {
                    transaction.show(mThirdFragment);
                }
                break;
            case 3:
                if(mFourthFragment == null) {
                    mFourthFragment = new TabAppFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString(TabFragment.ARGUMENTS, mTitles[i]);
                    mFourthFragment.setArguments(bundle);
                    transaction.add(R.id.fl_content, mFourthFragment);
                } else {
                    transaction.show(mFourthFragment);
                }
                break;
        }
        transaction.commit();
    }

    private void addAnim(FragmentTransaction transaction) {
        transaction.setCustomAnimations(
         R.anim.fragment_slide_left_enter,
         R.anim.fragment_slide_left_exit,
         R.anim.fragment_slide_right_enter,
         R.anim.fragment_slide_right_exit);
    }

    private void hideFragment(FragmentTransaction transaction) {
        if(mFirstFragment != null) {
            transaction.hide(mFirstFragment);
        }
        if(mSecondFragment != null) {
            transaction.hide(mSecondFragment);
        }
        if(mThirdFragment != null) {
            transaction.hide(mThirdFragment);
        }
        if(mFourthFragment != null) {
            transaction.hide(mFourthFragment);
        }
    }

    private void resetOthersIndicator() {
        for (int i = 0; i < mIndicators.size(); i++) {
            mIndicators.get(i).setIconAlpha(0);
        }
    }

}