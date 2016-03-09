package com.kw_support.view;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.kw_support.R;
import com.kw_support.base.BaseActivity;
import com.kw_support.manager.ThreadManager;
import com.kw_support.utils.CommonUtil;

/**
 * Created by Administrator on 2015/8/12.
 */
public abstract class LoadingPage extends FrameLayout {
    private static final int STATE_UNLOADED = 0;
    private static final int STATE_LOADING = 1;
    private static final int STATE_ERROR = 3;
    private static final int STATE_EMPTY = 4;
    private static final int STATE_SUCCEED = 5;
    private static final int TEXT_LOADING = 6;

    private View mLoadingView;
    private View mErrorView;
    private View mEmptyView;
    private View mSucceedView;
    private int point = 1;
    private int mState;
    private BaseActivity mActivity;

    public LoadingPage(BaseActivity activity) {
        super(activity);
        this.mActivity = activity;
        init();
    }

    private void init() {
        setBackgroundColor(mActivity.getResources().getColor(R.color.loading_page_bg));
        mState = STATE_UNLOADED;

        mLoadingView = createLoadingView();
        if (mLoadingView != null) {
            addView(mLoadingView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }

        mErrorView = createLoadingView();
        if (mErrorView != null) {
            addView(mErrorView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }

        mEmptyView = createLoadingView();
        if (mEmptyView != null) {
            addView(mEmptyView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }

    }

    private View createLoadingView() {
        return mActivity.getLayoutInflater().inflate(R.layout.layout_loading_page_loading, null);
    }

    private View createErrorView() {
        View view = mActivity.getLayoutInflater().inflate(R.layout.layout_loading_page_error, null);
        view.findViewById(R.id.btn_connect_net_work).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        });
        return view;
    }

    private View createEmptyView() {
        return mActivity.getLayoutInflater().inflate(R.layout.layout_loading_page_empty, null);
    }

    protected boolean needReset() {
        return mState == STATE_EMPTY || mState == STATE_ERROR;
    }

    public void reset() {
        setState(STATE_UNLOADED);
    }

    public synchronized void show() {
        if (needReset()) {
            mState = STATE_UNLOADED;
        }

        if (mState == STATE_UNLOADED) {
            mState = STATE_LOADING;
            LoadingTask task = new LoadingTask();
            ThreadManager.getLongPool().execute(task);
        }
        setPageVisiableSafe();
    }

    protected synchronized void setState(int state) {
        if (state < STATE_UNLOADED || state > STATE_SUCCEED) {
            return;
        }

        mState = state;
        setPageVisiableSafe();
    }

    private void setPageVisiableSafe() {
        long currentThreadId = Thread.currentThread().getId();
        long mainThreadId = mActivity.getMainLooper().getThread().getId();
        if (currentThreadId == mainThreadId) {
            setPageVisiable();
        } else {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setPageVisiable();
                }
            });
        }
    }

    private void setPageVisiable() {
        if (null != mLoadingView) {
            mLoadingView.setVisibility(mState == STATE_UNLOADED
                    || mState == STATE_LOADING ? View.VISIBLE : View.GONE);
        }
        if (null != mErrorView) {
            mErrorView.setVisibility(mState == STATE_ERROR ? View.VISIBLE
                    : View.GONE);
        }
        if (null != mEmptyView) {
            mEmptyView.setVisibility(mState == STATE_EMPTY ? View.VISIBLE
                    : View.GONE);
        }

        if (mState == STATE_SUCCEED) {
            if (mSucceedView == null) {
                mSucceedView = createLoadedView();
                CommonUtil.removeSelfFromParent(mSucceedView);
                addView(mSucceedView, new LayoutParams(
                        LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            }
            mSucceedView.setVisibility(VISIBLE);
        } else {
            mSucceedView.setVisibility(GONE);
        }
    }

    protected abstract View createLoadedView();

    public abstract LoadResult load();

    class LoadingTask implements Runnable {

        @Override
        public void run() {
            LoadResult result = load();

            int state;
            if (result == LoadResult.ERROR) {
                state = STATE_ERROR;
            } else if (result == LoadResult.EMPTY) {
                state = STATE_EMPTY;
            } else {
                state = STATE_SUCCEED;
            }
            setState(state);
        }
    }

    public enum LoadResult {
        ERROR, EMPTY, SUCCEED
    }
}
