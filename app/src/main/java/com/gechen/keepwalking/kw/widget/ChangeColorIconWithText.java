package com.gechen.keepwalking.kw.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.gechen.keepwalking.R;

/**
 * Created by G-chen on 2015-6-28.
 */
public class ChangeColorIconWithText extends View {

    private int mColor = 0xFF45C01A;
    private int mTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
            12, getResources().getDisplayMetrics());

    private Bitmap mIconBitmap;

    private String mText = "н╒пе";

    private Canvas mCanvas;

    private Bitmap mBitmap;

    private Paint mPaint;
    private Paint mTextPaint;

    private float mAlpha;

    private Rect mIconRect;
    private Rect mTextBound;

    public ChangeColorIconWithText(Context context) {
        this(context, null, 0);
    }

    public ChangeColorIconWithText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChangeColorIconWithText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ChangeColorIconWithText);

        int counts = a.getIndexCount();
        for (int i = 0; i < counts; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.ChangeColorIconWithText_changeable_icon:
                    BitmapDrawable drawable = (BitmapDrawable) a.getDrawable(attr);
                    mIconBitmap = drawable.getBitmap();
                    break;
                case R.styleable.ChangeColorIconWithText_changeable_color:
                    mColor = a.getColor(attr, 0xFF45C01A);
                    break;
                case R.styleable.ChangeColorIconWithText_changeable_text:
                    mText = a.getString(attr);
                    break;
                case R.styleable.ChangeColorIconWithText_changeable_text_size:
                    mTextSize = (int) a.getDimension(attr, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                            12, getResources().getDisplayMetrics()));
                    break;
                default:
                    break;
            }
        }

        a.recycle();

        mTextBound = new Rect();
        mTextPaint = new Paint();
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(0xFF555555);
        mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBound);
    }

    private static final String ORIGIN_STATUS = "origin_status";
    private static final String ALPHA_STATUS = "alpha_status";

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ORIGIN_STATUS, super.onSaveInstanceState());
        bundle.putFloat(ALPHA_STATUS, mAlpha);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            mAlpha = bundle.getFloat(ALPHA_STATUS);
            super.onRestoreInstanceState(bundle.getParcelable(ORIGIN_STATUS));
        } else {
            super.onRestoreInstanceState(state);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int iconWith = Math.min(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(),
                getMeasuredHeight() - getPaddingTop() - getPaddingBottom() - mTextBound.height());
        int left = getMeasuredWidth() / 2 - iconWith / 2;
        int top = (getMeasuredHeight() - mTextBound.height()) / 2 - iconWith / 2;

        mIconRect = new Rect(left, top, left + iconWith, top + iconWith);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mIconBitmap, null, mIconRect, null);

        int alpha = (int) Math.ceil(255 * mAlpha);

        setupTargetBitmap(alpha);

        drawSourceText(canvas, alpha);

        drawTargetText(canvas, alpha);

        canvas.drawBitmap(mBitmap, 0, 0, null);
    }

    private void drawTargetText(Canvas canvas, int alpha) {
        mTextPaint.setColor(mColor);
        mTextPaint.setAlpha(alpha);
        int x = getMeasuredWidth() / 2 - mTextBound.width() / 2;
        int y = mIconRect.bottom + mTextBound.height();
        canvas.drawText(mText, x, y, mTextPaint);
    }

    private void drawSourceText(Canvas canvas, int alpha) {
        mTextPaint.setColor(0xff333333);
        mTextPaint.setAlpha(255 - alpha);
        int x = getMeasuredWidth() / 2 - mTextBound.width() / 2;
        int y = mIconRect.bottom + mTextBound.height();
        canvas.drawText(mText, x, y, mTextPaint);

    }

    private void setupTargetBitmap(int alpha) {
        mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(mColor);
        mPaint.setAlpha(alpha);

        mCanvas.drawRect(mIconRect, mPaint);

        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mPaint.setAlpha(255);
        mCanvas.drawBitmap(mIconBitmap, null, mIconRect, mPaint);
    }

    public void setIconAlpha(float alpha) {
        mAlpha = alpha;
        invalidateView();
    }

    private void invalidateView() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            invalidate();
        } else {
            postInvalidate();
        }
    }
}
