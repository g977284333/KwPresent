package com.gechen.keepwalking.kw.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.gechen.keepwalking.R;
import com.kw_support.base.BaseActivity;
import com.kw_support.view.SegmentBar;

/**
 * Created by gechen on 2015/5/27.
 */
public class SegmentBarActivity extends BaseActivity implements SegmentBar.OnTabSelectionChanged{
    private SegmentBar mSegmentBar;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segment_bar);

        initView();
    }

    private void initView() {
        mSegmentBar = (SegmentBar) findViewById(R.id.segment_bar);
        mTextView = (TextView) findViewById(R.id.tv_segment_bar_tab_hint);

        mSegmentBar.setTabSelectionListener(this);
    }

    @Override
    public void onTabSelectionChanged(int index) {
        mTextView.setText("当前tab为：" + index);
    }
}
