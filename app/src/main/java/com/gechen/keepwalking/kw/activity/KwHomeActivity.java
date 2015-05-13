package com.gechen.keepwalking.kw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gechen.keepwalking.R;
import com.gechen.keepwalking.kw.activity.baidumap.KwBaiduMapActivity;
import com.gechen.keepwalking.common.base.BaseActivity;


public class KwHomeActivity extends BaseActivity {
    private Button mBounceScrollView;
    private Button mBaiduMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kw_home);

        setupView();
    }

   private void setupView() {
       mBounceScrollView = (Button) findViewById(R.id.btn_bounce_scroll_view);
       mBaiduMap = (Button) findViewById(R.id.btn_baidu_map);

       String str = String.format("application/json; charset=%s", "utf-8");
       mBounceScrollView.setText(str);
       mBounceScrollView.setOnClickListener(this);
       mBaiduMap.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.btn_bounce_scroll_view:
                intent = new Intent(getApplicationContext(), BounceScrollViewActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_baidu_map:
                intent = new Intent(getApplicationContext(), KwBaiduMapActivity.class);
                startActivity(intent);
            default:
                break;
        }
    }

}
