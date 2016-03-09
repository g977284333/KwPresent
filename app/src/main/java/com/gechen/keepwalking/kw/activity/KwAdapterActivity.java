package com.gechen.keepwalking.kw.activity;

import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.ListView;

import com.gechen.keepwalking.R;
import com.gechen.keepwalking.kw.adapter.CommonAdapter;
import com.kw_support.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by G-chen on 2015-5-19.
 */
@Deprecated
public class KwAdapterActivity extends BaseActivity {
    private ListView mListView;
    private List<String> mDatas;
    private CommonAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kw_adapter);

        initData();
        initView();
    }

    private void initData() {
        mDatas = new ArrayList<String>();
        for(int i = 'A'; i < 'z'; i++) {
            mDatas.add("RecyclerView: " + (char)i);
        }
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.lv_kw_adapter);
        mAdapter = new CommonAdapter(this, mDatas, R.layout.kw_adapter_item);
//        mListView.setLayoutAnimation(getListAnim());
        mListView.setAdapter(mAdapter);
    }

//    private LayoutAnimationController getListAnim() {
//        AnimationSet set = new AnimationSet(true);
//        Animation animation = new AlphaAnimation(0.0f, 1.0f);
//        animation.setDuration(300);
//        set.addAnimation(animation);
//        animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
//                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
//                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
//        animation.setDuration(500);
//        set.addAnimation(animation);
//        LayoutAnimationController controller = new LayoutAnimationController(
//                set, 0.5f);
//        return controller;
//    }
}
