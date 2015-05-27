package com.kw_support.view;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;

public abstract class ScrollerProxy {

    public static ScrollerProxy getScroller(Context context) {
        return VERSION.SDK_INT < VERSION_CODES.GINGERBREAD ? new PreGingerScroller(context) : new GingerScroller(context);
    }

    public abstract boolean computeScrollOffset();

    public abstract void startScroll(int startX, int startY, int dx, int dy, int duration);

    public abstract void fling(int startX, int startY, int velocityX, int velocityY, int minX, int maxX, int minY, int maxY);

    public abstract void forceFinished(boolean finished);

    public abstract boolean isFinished();

    public abstract int getCurrX();

    public abstract int getCurrY();

}