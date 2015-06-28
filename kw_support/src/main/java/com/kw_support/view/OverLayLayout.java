package com.kw_support.view;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

import com.kw_support.view.controller.ScrollerProxy;


/**
 * Created by gechen on 2015/6/25.
 */
public class OverLayLayout extends FrameLayout {

    private static final int INVALID_SCREEN = -1;
    private static final int SHOW_OVERLAY = 0;
    private static final int HIDE_OVERLAY = 1;
    private static final int SNAP_VELOCITY = 1000;

    private boolean mIsBeingDragged = false;

    private int mTouchSlop;

    private float mLastMotionX;
    private float mLastMotionY;

    private int mCurrentScreen = 0;
    private int mNextScreen = INVALID_SCREEN;

    private ScrollerProxy mScroller;
    private VelocityTracker mVelocityTracker;

    private OnOverlayStateChangedListener mOverlayStateChangedListener;

    private Handler mCountDownHandler;
    private CountDownTask mCountDownTask;

    public OverLayLayout(Context context) {
        this(context, null, 0);
    }

    public OverLayLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OverLayLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initOverLayLayout(context);
    }

    private void initOverLayLayout(Context context) {
        mScroller = ScrollerProxy.getScroller(context);
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = configuration.getScaledPagingTouchSlop();
        mCountDownHandler = new Handler();
        mCountDownTask = new CountDownTask();
    }

    public void setOnOverlayStateChangedListener(OnOverlayStateChangedListener listener) {
        mOverlayStateChangedListener = listener;
    }

    public void autoHideOverlay(long delayMillis) {
        mCountDownHandler.postDelayed(mCountDownTask, delayMillis);
    }

    private void showOverlay() {
        snapToScreen(SHOW_OVERLAY);
    }

    private void hideOverlay() {
        snapToScreen(HIDE_OVERLAY);
    }

    private void snapToScreen(int whichScreen) {
        int scrollY = getScrollY();
        int deltaY = whichScreen * getHeight() - scrollY;

        mNextScreen = whichScreen;
        mScroller.startScroll(0, scrollY, 0, deltaY, 250);
        invalidate();
    }

    private void snapToDestination() {
        int height = getHeight();
        int scrollY = getScrollY();
        int whichScreen = (scrollY + (height / 2)) / height;
        snapToScreen(whichScreen);
    }

    @Override
    public void computeScroll() {
        if(mScroller.computeScrollOffset()) {
            int scrollX = mScroller.getCurrX();
            int scrollY = mScroller.getCurrY();
            scrollTo(scrollX, scrollY);
            postInvalidate();
        } else if(mNextScreen != INVALID_SCREEN) {
            mCurrentScreen = mNextScreen;
            if(mOverlayStateChangedListener != null) {
                mOverlayStateChangedListener.onOverlayStateChanged(mCurrentScreen == SHOW_OVERLAY);
            }
            mNextScreen = INVALID_SCREEN;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();

        if(action == MotionEvent.ACTION_MOVE && mIsBeingDragged)
            return true;

        final float motionX = ev.getX();
        final float motionY = ev.getY();

        switch(action) {
            case MotionEvent.ACTION_MOVE:
                int xDiff = (int) Math.abs(motionX - mLastMotionX);
                int yDiff = (int) Math.abs(motionY - mLastMotionY);

                if(yDiff > mTouchSlop && yDiff * 0.5f > xDiff) {
                    mIsBeingDragged = true;
                }

                break;
            case MotionEvent.ACTION_DOWN:
                mLastMotionX = motionX;
                mLastMotionY = motionY;

                mCountDownHandler.removeCallbacks(mCountDownTask);
                mIsBeingDragged = !mScroller.isFinished();

                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mIsBeingDragged = false;

                break;
        }
        return mIsBeingDragged;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        obtainVelocityTracker(event);

        final int action = event.getAction();
        float motionY = event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if(!mScroller.isFinished()) {
                    mScroller.forceFinished(true);
                }

                mLastMotionY = motionY;
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaY = (int) (mLastMotionY  - motionY);
                mLastMotionY = motionY;

                int scrollY = getScrollY();

                if(deltaY < 0) {
                    scrollBy(0, Math.max(deltaY, -scrollY));
                } else if(deltaY > 0) {
                    scrollBy(0, Math.min(deltaY, getHeight() - scrollY));
                }

                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                final VelocityTracker velocityTracker = mVelocityTracker;
                velocityTracker.computeCurrentVelocity(1000);
                int velocityY = (int) velocityTracker.getYVelocity();

                if(velocityY > SNAP_VELOCITY) {
                    showOverlay();
                } else if(velocityY < -SNAP_VELOCITY) {
                    hideOverlay();
                } else {
                    snapToDestination();
                }

                recycleVelocityTracker();
                break;
        }

        return true;
    }




    private void obtainVelocityTracker(MotionEvent event) {
        if(mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }

        mVelocityTracker.addMovement(event);
    }

    private void recycleVelocityTracker() {
        if(mVelocityTracker != null) {
            mVelocityTracker.recycle();;
            mVelocityTracker = null;
        }
    }



    private class CountDownTask implements Runnable {

        @Override
        public void run() {
            hideOverlay();
        }
    }

    public interface OnOverlayStateChangedListener {
        void onOverlayStateChanged(boolean isShow);
    }
}
