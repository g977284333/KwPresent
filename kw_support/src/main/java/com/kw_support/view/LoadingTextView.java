package com.kw_support.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by g_chen on 15/9/2.
 */
public class LoadingTextView extends TextView {
    private Handler mHandler;
    private String mText;
    private int index;

    public LoadingTextView(Context context) {
        super(context);
        init();
    }

    public LoadingTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadingTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                handleMsg(msg);
            }
        };
    }

    private void handleMsg(Message msg) {
        switch (msg.what) {
            case 0:
                setText(mText.substring(0, index));

                if (++index > mText.length()) {
                    index = 0;
                }

                mHandler.sendEmptyMessageDelayed(0, 500);
                break;
            case -1:
                break;
        }
    }


    public void start() {
        mText = getText().toString();
        if (TextUtils.isEmpty(mText)) {
            return;
        }

        mHandler.sendEmptyMessage(0);
    }

    public void stop() {
        mHandler.sendEmptyMessage(-1);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        start();
    }
}
