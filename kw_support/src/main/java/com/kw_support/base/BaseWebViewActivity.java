package com.kw_support.base;

import android.os.Bundle;

import com.kw_support.R;
import com.kw_support.view.BrowserLayout;

/**
 * Created by Administrator on 2015/8/14.
 */
public class BaseWebViewActivity extends BaseActivity {

    private BrowserLayout mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        mWebView = (BrowserLayout) findViewById(R.id.bl_web_view);

        mWebView.loadUrl("http://www.baidu.com");
    }
}
