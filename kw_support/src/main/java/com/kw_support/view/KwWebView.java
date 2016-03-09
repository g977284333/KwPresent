package com.kw_support.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * Created by Administrator on 2015/8/14.
 */
public class KwWebView extends WebView {

    private float mCurContentHeight;
    private float mThrehold;

    public KwWebView(Context context) {
        super(context);
    }

    public KwWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KwWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * 当我们做类似上拉加载下一页这样的功能的时候，页面初始的时候需要知道当前WebView是否存在纵向滚动条，
     * 如果有则不加载下一页，如果没有则加载下一页直到其出现纵向滚动条。
     */
    public boolean exitsVerticalScrollbar() {
        return computeVerticalScrollRange() > computeVerticalScrollExtent();
    }

    @Override
    /**
     * 当我们做类似上拉加载下一页这样的功能的时候，页面初始的时候需要知道当前WebView是否存在纵向滚动条，
     * 如果有则不加载下一页，如果没有则加载下一页直到其出现纵向滚动条。
     */
    protected void onScrollChanged(int newX, int newY, int oldX, int oldY) {
        super.onScrollChanged(newX, newY, oldX, oldY);

        if (newY != oldY) {
            float contentHeight = getContentHeight() * getScale();

            if (mCurContentHeight != contentHeight && newY > 0 && contentHeight <= newY + getHeight() + mThrehold) {
                mCurContentHeight = contentHeight;
            }
        }
    }
}
