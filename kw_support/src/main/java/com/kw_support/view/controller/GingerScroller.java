package com.kw_support.view.controller;

import android.annotation.TargetApi;
import android.content.Context;
import android.widget.OverScroller;

@TargetApi(9)
public class GingerScroller extends ScrollerProxy {

    protected OverScroller mScroller;

    public GingerScroller(Context context) {
        mScroller = new OverScroller(context);
    }

    @Override
    public boolean computeScrollOffset() {
        return mScroller.computeScrollOffset();
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        mScroller.startScroll(startX, startY, dx, dy, duration);
    }

    @Override
    public void fling(int startX, int startY, int velocityX, int velocityY, int minX, int maxX, int minY, int maxY) {
        mScroller.fling(startX, startY, velocityX, velocityY, minX, maxX, minY, maxY, 0, 0);
    }

    @Override
    public void forceFinished(boolean finished) {
        mScroller.forceFinished(finished);
    }

    @Override
    public boolean isFinished() {
        return mScroller.isFinished();
    }

    @Override
    public int getCurrX() {
        return mScroller.getCurrX();
    }

    @Override
    public int getCurrY() {
        return mScroller.getCurrY();
    }

}