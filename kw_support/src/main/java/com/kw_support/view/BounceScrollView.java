package com.kw_support.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

/**
 * Created by G-chen on 2015-3-15.
 *
 * @disctiption: ScrollView反弹效果的实现
 */
public class BounceScrollView extends ScrollView {

    private View inner;                                 // 孩子view

    private Rect normal = new Rect();                   // 矩形，用于判断是否需要动画

    private float y;                                    // 点击是的坐标

    private boolean isCount = false;                  // 是否开始计算移动的距离

    private int size = 3;                              // 可以拉伸的长度为屏幕宽度的比值

    private float mLastMotionX;
    private float mLastMotionY;

    public BounceScrollView(Context context) {
        super(context);
    }

    public BounceScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public BounceScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        if (getChildCount() > 0) {
            inner = getChildAt(0);                      // 获取第一个子View
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();

        final float motionX = ev.getX();
        final float motionY = ev.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastMotionX = motionX;
                mLastMotionY = motionY;

                break;
            case MotionEvent.ACTION_MOVE:
                int xDiff = (int) Math.abs(motionX - mLastMotionX);
                int yDiff = (int) Math.abs(motionY - mLastMotionY);

                if(yDiff * 0.3f > xDiff) {
                    return true;
                }
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (null != inner) {
            commOnTouchEvent(ev);
        }
        return super.onTouchEvent(ev);
    }

    private void commOnTouchEvent(MotionEvent ev) {
        int action = ev.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                if (isNeedAnimation()) {
                    animation();
                    isCount = false;
                }
                break;
            /***
             * 排除出第一次移动计算，因为第一次无法得知y坐标， 在MotionEvent.ACTION_DOWN中获取不到，
             * 因为此时是MyScrollView的touch事件传递到到了LIstView的孩子item上面.所以从第二次计算开始.
             * 然而我们也要进行初始化，就是第一次移动的时候让滑动距离归0. 之后记录准确了就正常执行.
             */
            case MotionEvent.ACTION_MOVE:
                final float dY = y;
                float curY = ev.getY();
                int deltaY = (int) (dY - curY);

                if (!isCount) {
                    deltaY = 0;                      // 归0，第一次不计算
                }

                y = curY;
                // 当滚动到最上面或者最下面时就不再滚动，这时候移动布局
                if (isNeedMove()) {
                    // 初始化头部矩形
                    if (normal.isEmpty()) {
                        // 保持正常的布局位置
                        normal.set(inner.getLeft(), inner.getTop(),
                                inner.getRight(), inner.getBottom());
                    }
                    //　移动布局
                    inner.layout(inner.getLeft(), inner.getTop() - deltaY / size,
                            inner.getRight(), inner.getBottom() - deltaY / size);
                }

                isCount = true;
                break;
            default:
                break;
        }
    }

    private void animation() {
        // 开启动画
        TranslateAnimation tsAnim = new TranslateAnimation(0, 0, inner.getTop(), normal.top);
        tsAnim.setDuration(200);
        inner.startAnimation(tsAnim);

        // 设置回到正常的布局为位置
        inner.layout(normal.left, normal.top, normal.right, normal.bottom);
        normal.setEmpty();
    }

    public boolean isNeedAnimation() {
        return !normal.isEmpty();
    }

    /**
     * 是否要移动布局
     * inner.getMeasuredHeight()是获取控件的总高度
     * getHeight是屏幕的高度
     */
    public boolean isNeedMove() {
        int offset = inner.getMeasuredHeight() - getHeight();
        int scrollY = getScrollY();

        // 0是顶部，后面的是底部
        if (scrollY == 0 || scrollY == offset) {
            return true;
        }
        return false;
    }
}
