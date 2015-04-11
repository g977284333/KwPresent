package com.gechen.keepwalking.kw.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by G-chen on 2015-3-16.
 */
public abstract class KwBaseAdapter<T> extends BaseAdapter{
    protected List<T> data;
    protected LayoutInflater inflater;
    protected Context context;

    public KwBaseAdapter(Context context) {
        initWithContext(context);
    }

    public KwBaseAdapter(Context context, List<T> data) {
        initWithContext(context);
        this.data = data == null ? new ArrayList<T>() : new ArrayList<T>(data);
    }

    private void initWithContext(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    protected void setDatas(List<T> data) {
        this.data = data;
    }

    protected void add(T obj) {
        data.add(obj);
        notifyDataSetChanged();
    }

    protected void addAll(List<T> data) {
        data.addAll(data);
        notifyDataSetChanged();
    }

    protected void remove(int position) {
        data.remove(position);
        notifyDataSetChanged();
    }

    protected void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        return null;
    }
}
