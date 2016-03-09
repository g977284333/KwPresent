package com.gechen.keepwalking.kw.frament;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by G-chen on 2015-6-28.
 */
public class TabFragment extends Fragment {
    private static final String TAG = TabFragment.class.getSimpleName();

    private String mTitle = "Default";

    public static String ARGUMENTS = "arguments";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(getArguments() != null) {
            mTitle = getArguments().getString(ARGUMENTS);
        }

        TextView tv = new TextView(getActivity());
        tv.setTextSize(30);
        tv.setTextColor(Color.parseColor("#000000"));
        tv.setBackgroundColor(Color.parseColor("#ffffff"));
        tv.setText(mTitle);
        tv.setGravity(Gravity.CENTER);
        Log.d(TAG, "onCreateView" + getArguments());
        return tv;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(TAG, "onAttach" +  getArguments());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate" +  getArguments());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated" + getArguments());
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart" + getArguments());
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume" + getArguments());
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause" + getArguments());
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop" + getArguments());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView" + getArguments());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy" + getArguments());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach" + getArguments());
    }
}
