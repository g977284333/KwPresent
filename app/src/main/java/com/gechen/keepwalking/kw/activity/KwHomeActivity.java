package com.gechen.keepwalking.kw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gechen.keepwalking.R;
import com.kw_support.base.BaseActivity;


public class KwHomeActivity extends BaseActivity {
    private Button mBounceScrollView;
    private Button mBaiduMap;
    private Button mKwAdapterBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kw_home);

        setupView();
    }

   private void setupView() {
       mBounceScrollView = (Button) findViewById(R.id.btn_bounce_scroll_view);
       mBaiduMap = (Button) findViewById(R.id.btn_baidu_map);
       mKwAdapterBtn = (Button) findViewById(R.id.btn_kw_adapter);

       mBounceScrollView.setOnClickListener(this);
       mBaiduMap.setOnClickListener(this);
       mKwAdapterBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_bounce_scroll_view:
                gotoTargetActivity(BounceScrollViewActivity.class);
                break;
            case R.id.btn_baidu_map:
                gotoTargetActivity(KwBaiduMapActivity.class);
            case R.id.btn_kw_adapter:
                gotoTargetActivity(KwAdapterActivity.class);
            default:
                break;
        }
    }

    private void gotoTargetActivity(Class clazz) {
        Intent intent = new Intent(getApplicationContext(), clazz);
        startActivity(intent);
    }
}
