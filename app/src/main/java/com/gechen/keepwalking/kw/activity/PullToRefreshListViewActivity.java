package com.gechen.keepwalking.kw.activity;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.gechen.keepwalking.R;
import com.kw_support.pulltorefresh.PullToRefreshBase;
import com.kw_support.pulltorefresh.PullToRefreshListView;
import com.kw_support.pulltorefresh.extras.SoundPullEventListener;
import com.kw_support.utils.UiUtil;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
@Deprecated
public class PullToRefreshListViewActivity extends ActionBarActivity {
    private PullToRefreshListView mPtrListView;
    private LinkedList<String> mDatas;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_to_refresh_list_view);

        initData();
        initView();
    }

    private void initView() {
        mPtrListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);

        ListView realListView = mPtrListView.getRefreshableView();
        realListView.setAdapter(mAdapter);

        mPtrListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        mPtrListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override

            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                // 设置最后一次更新时间
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                new GetDataTask().execute();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }


        });

        mPtrListView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                UiUtil.showToast(PullToRefreshListViewActivity.this, "到底了...");
            }
        });

        addSoundEventOnRefresh();
    }

    private void addSoundEventOnRefresh() {
        SoundPullEventListener<ListView> soundPullEventListener = new SoundPullEventListener<ListView>(this);
        soundPullEventListener.addSoundEvent(PullToRefreshBase.State.PULL_TO_REFRESH, R.raw.pull_event);
        soundPullEventListener.addSoundEvent(PullToRefreshBase.State.RESET, R.raw.reset_sound);
        soundPullEventListener.addSoundEvent(PullToRefreshBase.State.REFRESHING, R.raw.refreshing_sound);
        mPtrListView.setOnPullEventListener(soundPullEventListener);
    }

    private void initData() {
        mDatas = new LinkedList<String>();
        mDatas.addAll(Arrays.asList(mStrings));

        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mDatas);
    }

    public void manualRefresh(View v) {
        new GetDataTask().execute();
        mPtrListView.setRefreshing(false);
    }

    public void disableScrollingWhileRefreshing(View v) {
        mPtrListView.setScrollingWhileRefreshingEnabled(
                !mPtrListView.isScrollingWhileRefreshingEnabled());
    }

    private class GetDataTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... params) {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
            }
            return mStrings;
        }

        @Override
        protected void onPostExecute(String[] result) {
            mDatas.addFirst("Added after refresh...");
            mAdapter.notifyDataSetChanged();

            mPtrListView.onRefreshComplete();

            super.onPostExecute(result);
        }
    }


    private String[] mStrings = { "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
            "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
            "Allgauer Emmentaler", "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
            "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
            "Allgauer Emmentaler" };
}
