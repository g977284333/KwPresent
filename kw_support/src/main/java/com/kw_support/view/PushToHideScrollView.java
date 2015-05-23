package com.kw_support.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

/**
 *
 */
public class PushToHideScrollView extends FrameLayout {

	private float mLastMotionY;

	private ScrollerProxy mScroller;
	private VelocityTracker mVelocityTracker;

	private int mMinimumVelocity;
	private int mMaximumVelocity;

	public PushToHideScrollView(Context context) {
		this(context, null);
	}

	public PushToHideScrollView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public PushToHideScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initSectionScrollView(context);
	}

	private void initSectionScrollView(Context context) {
		mScroller = ScrollerProxy.getScroller(context);
		ViewConfiguration configuration = ViewConfiguration.get(context);
		mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
		mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		int childTop = 0;

		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			final View child = getChildAt(i);

			if (child.getVisibility() == View.GONE)
				continue;

			final int childWidth = child.getMeasuredWidth();
			final int childHeight = child.getMeasuredHeight();
			child.layout(0, childTop, childWidth, childTop + childHeight);
			childTop += childHeight;
		}
	}

	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			int scrollX = mScroller.getCurrX();
			int scrollY = mScroller.getCurrY();
			scrollTo(scrollX, scrollY);
			postInvalidate();
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		if (mVelocityTracker == null)
			mVelocityTracker = VelocityTracker.obtain();

		mVelocityTracker.addMovement(event);

		final int action = event.getAction();
		final float motionY = event.getY();

		final int scrollX = getScrollX();
		final int scrollY = getScrollY();

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			if (!mScroller.isFinished())
				mScroller.forceFinished(true);

			mLastMotionY = motionY;

			break;
		case MotionEvent.ACTION_MOVE:
			final int deltaY = (int) (mLastMotionY - motionY);
			mLastMotionY = motionY;

			if (deltaY < 0) {
				scrollBy(0, Math.max(deltaY, -scrollY));
			} else if (deltaY > 0) {
				int lastChildIndex = getChildCount() - 1;
				View child = getChildAt(lastChildIndex);
				int childBottom = child == null ? 0 : child.getBottom();
				int availableToScroll = Math.max(childBottom - getHeight() - scrollY, 0);
				scrollBy(0, Math.min(availableToScroll, deltaY));
			}

			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			final VelocityTracker velocityTracker = mVelocityTracker;
			velocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
			int initialVelocity = (int) velocityTracker.getYVelocity();

			if ((Math.abs(initialVelocity) > mMinimumVelocity)) {
				int lastChildIndex = getChildCount() - 1;
				View child = getChildAt(lastChildIndex);
				int childBottom = child == null ? 0 : child.getBottom();
				mScroller.fling(scrollX, scrollY, 0, -initialVelocity, 0, 0, 0, Math.max(childBottom - getHeight(), 0));
				invalidate();
			}

			if (mVelocityTracker != null) {
				mVelocityTracker.recycle();
				mVelocityTracker = null;
			}

			break;
		}

		return super.dispatchTouchEvent(event);
	}

}