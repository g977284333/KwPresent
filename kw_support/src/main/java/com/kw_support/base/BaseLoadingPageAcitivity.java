package com.kw_support.base;

import android.os.Bundle;
import android.view.View;

import com.kw_support.view.LoadingPage;

/**
 * Created by Administrator on 2015/8/13.
 */
public class BaseLoadingPageAcitivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
    }

    protected void initView() {
        LoadingPage page = new LoadingPage(this) {
            @Override
            protected View createLoadedView() {
                return createLoadedView();
            }

            @Override
            public LoadResult load() {
                return load();
            }
        };
    }

    protected LoadingPage.LoadResult load() {
        return null;
    }

    protected View createLoadedView() {
        return null;
    }


}
