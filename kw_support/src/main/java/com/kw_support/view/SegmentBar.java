package com.kw_support.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kw_support.R;
import com.kw_support.view.controller.ScrollerProxy;

public class SegmentBar extends LinearLayout {

    private final static int INVALID_POSITION = -1;

    private OnTabSelectionChanged mSelectionChangedListener;
    private OnTabSelectionSame mSelectionSameListener;

    private int mSelectedTab = INVALID_POSITION;
    private Rect mSelectedRect;
    private Rect mUnselectRect;
    private Rect mTargetRect;
    private Rect mTempRect;
    private ScrollerProxy mScroller;
    private Drawable mSelector;
    private Drawable mUnselector;
    private Drawable mDivider;
    private int mDividerWidth;
    private int mDividerHeight;
    private int mDividerPadding;

    public SegmentBar(Context context) {
        this(context, null);
    }

    public SegmentBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        mSelectedRect = new Rect();
        mUnselectRect = new Rect();
        mTargetRect = new Rect();
        mTempRect = new Rect();
        mScroller = ScrollerProxy.getScroller(context);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SegmentBar, 0, 0);
        mSelector = typedArray.getDrawable(R.styleable.SegmentBar_selector);
        mUnselector = typedArray.getDrawable(R.styleable.SegmentBar_unselector);
        mDivider = typedArray.getDrawable(R.styleable.SegmentBar_dividerDrawable);
        int dividerWidth = mDivider == null ? 0 : mDivider.getIntrinsicWidth();
        int dividerHeight = mDivider == null ? 0 : mDivider.getIntrinsicHeight();
        mDividerWidth = typedArray.getDimensionPixelSize(R.styleable.SegmentBar_dividerWidth, dividerWidth);
        mDividerHeight = typedArray.getDimensionPixelSize(R.styleable.SegmentBar_dividerHeight, dividerHeight);
        mDividerPadding = typedArray.getDimensionPixelSize(R.styleable.SegmentBar_dividerPaddings, 0);
        typedArray.recycle();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        drawUnselector(canvas);
        drawSelector(canvas);
        super.dispatchDraw(canvas);
        drawDivider(canvas);
    }

    private void drawSelector(Canvas canvas) {
        if (mSelector != null) {
            mSelector.setBounds(mSelectedRect);
            mSelector.draw(canvas);
        }
    }

    private void drawUnselector(Canvas canvas) {
        if (mUnselector != null) {
            boolean isVertical = getOrientation() == VERTICAL;

            int left = isVertical ? 0 : mSelectedRect.right;
            int right = isVertical ? getWidth() : mSelectedRect.left;
            int top = isVertical ? mSelectedRect.bottom : 0;
            int bottom = isVertical ? mSelectedRect.top : getHeight();

            mUnselectRect.set(0, 0, right, bottom);
            mUnselector.setBounds(mUnselectRect);
            mUnselector.draw(canvas);

            mUnselectRect.set(left, top, getWidth(), getHeight());
            mUnselector.setBounds(mUnselectRect);
            mUnselector.draw(canvas);
        }
    }

    private void drawDivider(Canvas canvas) {
        if (mDivider == null || (mDividerWidth <= 0 && mDividerHeight <= 0))
            return;

        Rect bounds = mTempRect;
        if (getOrientation() == VERTICAL) {
            bounds.left = getPaddingLeft() + mDividerPadding;
            bounds.right = getWidth() - getPaddingRight() - mDividerPadding;
        } else {
            bounds.top = getPaddingTop() + mDividerPadding;
            bounds.bottom = getHeight() - getPaddingBottom() - mDividerPadding;
        }

        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);

            if (child == null || child.getVisibility() == GONE)
                continue;

            boolean hasVisibleViewAfter = false;
            for (int j = i + 1; j < count; j++) {
                if (getChildAt(j).getVisibility() != GONE) {
                    hasVisibleViewAfter = true;
                    break;
                }
            }

            if (!hasVisibleViewAfter)
                continue;

            LayoutParams params = (LayoutParams) child.getLayoutParams();

            if (getOrientation() == VERTICAL) {
                bounds.top = child.getBottom() + params.bottomMargin;
                bounds.bottom = bounds.top + mDividerHeight;
            } else {
                bounds.left = child.getRight() + params.rightMargin;
                bounds.right = bounds.left + mDividerWidth;
            }

            mDivider.setBounds(bounds);
            mDivider.draw(canvas);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (mSelectedTab >= 0 && mSelectedTab < getTabCount())
            getChildTabViewAt(mSelectedTab).getHitRect(mSelectedRect);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            int width = mSelectedRect.width();
            int height = mSelectedRect.height();
            mSelectedRect.left = mScroller.getCurrX();
            mSelectedRect.right = mSelectedRect.left + width;
            mSelectedRect.top = mScroller.getCurrY();
            mSelectedRect.bottom = mSelectedRect.top + height;
            postInvalidate();
        }
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        super.addView(child, params);

        child.setOnClickListener(new TabClickListener(getTabCount() - 1));

        if (mSelectedTab == INVALID_POSITION)
            setCurrentTab(0);
    }

    @Override
    public void removeAllViews() {
        super.removeAllViews();
        mSelectedTab = INVALID_POSITION;
    }

    public View getChildTabViewAt(int index) {
        return getChildAt(index);
    }

    public int getTabCount() {
        return getChildCount();
    }

    public int getCurrentTab() {
        return mSelectedTab;
    }

    public void setCurrentTab(int index) {
        if (index < 0 || index >= getTabCount())
            return;

        if (index == mSelectedTab) {
            if (mSelectionSameListener != null)
                mSelectionSameListener.onTabSelectionSame(mSelectedTab);
            return;
        }

        if (mSelectedTab != INVALID_POSITION)
            getChildTabViewAt(mSelectedTab).setSelected(false);

        mSelectedTab = index;
        getChildTabViewAt(mSelectedTab).setSelected(true);

        if (mSelectionChangedListener != null)
            mSelectionChangedListener.onTabSelectionChanged(mSelectedTab);

        getChildTabViewAt(mSelectedTab).getHitRect(mTargetRect);

        if (mSelectedRect.isEmpty())
            mSelectedRect.set(mTargetRect);

        if (!mSelectedRect.equals(mTargetRect) && (mSelector != null || mUnselector != null)) {
            mSelectedRect.right = mSelectedRect.left + mTargetRect.width();
            mSelectedRect.bottom = mSelectedRect.top + mTargetRect.height();
            int deltaX = mTargetRect.left - mSelectedRect.left;
            int deltaY = mTargetRect.top - mSelectedRect.top;
            mScroller.startScroll(mSelectedRect.left, mSelectedRect.top, deltaX, deltaY, 250);
            invalidate();
        }
    }

    public void setTabSelectionListener(OnTabSelectionChanged listener) {
        mSelectionChangedListener = listener;
    }

    public void setTabSelectionSameListener(OnTabSelectionSame listener) {
        mSelectionSameListener = listener;
    }

    private class TabClickListener implements OnClickListener {

        private final int mTabIndex;

        private TabClickListener(int tabIndex) {
            mTabIndex = tabIndex;
        }

        public void onClick(View v) {
            setCurrentTab(mTabIndex);
        }
    }

    public interface OnTabSelectionChanged {

        public void onTabSelectionChanged(int index);

    }

    public interface OnTabSelectionSame {

        public void onTabSelectionSame(int index);

    }

}