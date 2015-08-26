/*
 * Copyright (c) 2015 [1076559197@qq.com | tchen0707@gmail.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kw_support.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.kw_support.R;
import com.kw_support.utils.CommonUtil;

public class BrowserLayout extends LinearLayout {

    private Context mContext = null;

    private WebView mWebView = null;

    private WebSettings mWebSetting = null;

    private ProgressBar mProgressBar = null;

    private View mBrowserControllerView = null;

    private ImageButton mGoBackBtn = null;
    private ImageButton mGoForwardBtn = null;
    private ImageButton mGoBrowserBtn = null;
    private ImageButton mRefreshBtn = null;

    private int mBarHeight = 5;

    private String mLoadUrl;

    public BrowserLayout(Context context) {
        super(context);
        init(context);
    }

    public BrowserLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        setOrientation(VERTICAL);

        mProgressBar = (ProgressBar) LayoutInflater.from(context).inflate(R.layout.progress_horizontal, null);
        mProgressBar.setMax(100);
        mProgressBar.setProgress(0);
        addView(mProgressBar, LayoutParams.MATCH_PARENT, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, mBarHeight, getResources().getDisplayMetrics()));

        mWebView = new WebView(context);
        mWebSetting = mWebView.getSettings();
        mWebSetting.setJavaScriptEnabled(true);
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mWebSetting.setDefaultTextEncodingName("UTF-8");
        mWebSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebSetting.setBuiltInZoomControls(false);
        mWebSetting.setSupportMultipleWindows(true);
        mWebSetting.setUseWideViewPort(true);
        mWebSetting.setLoadWithOverviewMode(true);
        mWebSetting.setSupportZoom(false);
        mWebSetting.setPluginState(WebSettings.PluginState.ON);
        mWebSetting.setDomStorageEnabled(true);
        mWebSetting.setLoadsImagesAutomatically(true);

        if (Build.VERSION.SDK_INT > 19) {
            mWebSetting.setLoadsImagesAutomatically(true);
        } else {
            mWebSetting.setLoadsImagesAutomatically(false);
        }

        LayoutParams lps = new LayoutParams(LayoutParams.MATCH_PARENT, 0, 1);
        addView(mWebView, lps);

        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(newProgress);
                }
            }
        });

        mWebView.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mLoadUrl = url;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
                showErrorPage();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    view.loadUrl("http://" + url);
                } else {
                    view.loadUrl(url);
                }
                return true;
            }
        });

        mBrowserControllerView = LayoutInflater.from(context).inflate(R.layout.browser_controller, null);
        mGoBackBtn = (ImageButton) mBrowserControllerView.findViewById(R.id.browser_controller_back);
        mGoForwardBtn = (ImageButton) mBrowserControllerView.findViewById(R.id.browser_controller_forward);
        mGoBrowserBtn = (ImageButton) mBrowserControllerView.findViewById(R.id.browser_controller_go);
        mRefreshBtn = (ImageButton) mBrowserControllerView.findViewById(R.id.browser_controller_refresh);

        mGoBackBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (canGoBack()) {
                    goBack();
                }
            }
        });

        mGoForwardBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (canGoForward()) {
                    goForward();
                }
            }
        });

        mRefreshBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                loadUrl(mLoadUrl);
            }
        });

        mGoBrowserBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!CommonUtil.isEmpty(mLoadUrl)) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(mLoadUrl));
                    mContext.startActivity(intent);
                }
            }
        });

        addView(mBrowserControllerView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    }

    public void loadUrl(String url) {
        mWebView.loadUrl(url);
    }

    public boolean canGoBack() {
        return null != mWebView ? mWebView.canGoBack() : false;
    }

    public boolean canGoForward() {
        return null != mWebView ? mWebView.canGoForward() : false;
    }

    public void goBack() {
        if (null != mWebView) {
            mWebView.goBack();
        }
    }

    public void goForward() {
        if (null != mWebView) {
            mWebView.goForward();
        }
    }

    public WebView getWebView() {
        return mWebView != null ? mWebView : null;
    }

    public void hideBrowserController() {
        mBrowserControllerView.setVisibility(View.GONE);
    }

    public void showBrowserController() {
        mBrowserControllerView.setVisibility(View.VISIBLE);
    }

    private void showErrorPage() {
    }

    @SuppressLint("JavascriptInterface")
    public void setSupportLoadJS(Object obj, String interfaceName) {
        mWebView.addJavascriptInterface(obj, interfaceName);
        mWebSetting.setJavaScriptCanOpenWindowsAutomatically(true);
    }
}
