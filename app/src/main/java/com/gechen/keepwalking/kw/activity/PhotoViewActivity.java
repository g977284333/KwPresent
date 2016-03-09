package com.gechen.keepwalking.kw.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.gechen.keepwalking.R;
import com.kw_support.base.BaseActivity;
import com.kw_support.thirdlib.photoview.PhotoView;
import com.kw_support.thirdlib.photoview.HackyViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gechen on 2015/5/26.
 */
@Deprecated
public class PhotoViewActivity extends BaseActivity {
    private ViewPager mViewPager;
    private List<PhotoView> mViews;

    private int[] mDatas = {R.drawable.wallpaper, R.drawable.wallpaper, R.drawable.wallpaper,
                    R.drawable.wallpaper, R.drawable.wallpaper};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);

        initView();
        initDatas();
    }

    private void initDatas() {
        mViews = new ArrayList<PhotoView>();
        for(int i = 0; i < mDatas.length; i++) {
            PhotoView pv = new PhotoView(getApplicationContext());
            pv.setImageResource(mDatas[i]);
            mViews.add(pv);
        }

        PhotoViewPagerAdapter adapter = new PhotoViewPagerAdapter();
        mViewPager.setAdapter(adapter);
    }

    private void initView() {
       mViewPager = (HackyViewPager) findViewById(R.id.vp_photo_view);
    }

    private class PhotoViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mDatas.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == (View)object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViews.get(position));
            return mViews.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }
    }
}
