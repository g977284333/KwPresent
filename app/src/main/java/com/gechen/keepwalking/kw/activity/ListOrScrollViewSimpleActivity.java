package com.gechen.keepwalking.kw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.gechen.keepwalking.R;
import com.kw_support.base.BaseActivity;

/**
 * Created by gechen on 2015/5/23.
 */
public class ListOrScrollViewSimpleActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_or_scroll_view);
    }

    public void skipToPullToRefreshListView(View view) {
        gotoTargetActivity(PullToRefreshListViewActivity.class);
    }

    public void skipToCommonAdapter(View view) {
        gotoTargetActivity(KwAdapterActivity.class);
    }

    public void skipToPullToHideScrollView(View view) {
        gotoTargetActivity(PullToHideScrollViewActivity.class);
    }

    private void gotoTargetActivity(Class clazz) {
        Intent intent = new Intent(getApplicationContext(), clazz);
        startActivity(intent);
    }

}
