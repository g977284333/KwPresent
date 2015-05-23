package com.gechen.keepwalking.kw.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.gechen.keepwalking.R;
import com.gechen.keepwalking.kw.adapter.CommonAdapter;
import com.kw_support.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by G-chen on 2015-5-19.
 */
public class PullToHideScrollViewActivity extends BaseActivity {
    private ListView mListView;
    private List<String> mDatas;
    private CommonAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_to_hide_scroll_view);

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
        mListView.setAdapter(mAdapter);
    }
}
