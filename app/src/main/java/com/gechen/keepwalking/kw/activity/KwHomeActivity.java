package com.gechen.keepwalking.kw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gechen.keepwalking.R;
import com.gechen.keepwalking.kw.activity.base.BaseActivity;


public class KwHomeActivity extends BaseActivity {
    private Button btnBounceScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kw_home);

        setupView();
    }

   private void setupView() {
        btnBounceScrollView = (Button) findViewById(R.id.btn_bounce_scroll_view);

        String str = String.format("application/json; charset=%s", "utf-8");
        btnBounceScrollView.setText(str);
       btnBounceScrollView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_bounce_scroll_view:
                Intent intent = new Intent(getApplicationContext(), BounceScrollViewActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

}
