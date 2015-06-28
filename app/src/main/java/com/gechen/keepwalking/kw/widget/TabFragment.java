package com.gechen.keepwalking.kw.widget;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by G-chen on 2015-6-28.
 */
public class TabFragment extends Fragment {
    private String mTitle = "Default";

    public static String ARGUMENTS = "arguments";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(getArguments() != null) {
            mTitle = getArguments().getString(ARGUMENTS);
        }

        TextView tv = new TextView(getActivity());
        tv.setTextSize(30);
        tv.setBackgroundColor(Color.parseColor("#ffffff"));
        tv.setText(mTitle);
        tv.setGravity(Gravity.CENTER);
        return tv;
    }
}
