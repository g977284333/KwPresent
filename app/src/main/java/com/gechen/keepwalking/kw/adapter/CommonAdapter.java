package com.gechen.keepwalking.kw.adapter;

import android.content.Context;

import com.gechen.keepwalking.R;
import com.kw_support.base.KwBaseAdapter;
import com.kw_support.utils.ViewHolder;

import java.util.List;

/**
 * 测试KwBaseAdapter
 * Created by G-chen on 2015-5-19.
 */
public class CommonAdapter extends KwBaseAdapter<String> {

    public CommonAdapter(Context context, List<String> datas, int itemLayoutId) {
        super(context, datas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder viewHolder, String item) {
        viewHolder.setImageResource(R.id.iv_kw_adapter_icon, R.drawable.ic_launcher);
        viewHolder.setText(R.id.tv_kw_adapter_title, item);
    }

}

