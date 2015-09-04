package com.kw_support.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kw_support.R;

/**
 * Created by Administrator on 2015/9/3.
 */
public class LoadingDialog {
    private ImageView mHint;

    private Context mContext;

    private Dialog mDialog;

    public LoadingDialog(Context context) {
        mContext = context;
    }

    public LoadingDialog builder() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_loading_dialog, null);
        mHint = (ImageView) view.findViewById(R.id.iv_laoding_dialog_txt);

        mDialog = new Dialog(mContext, R.style.alert_dialog_style);
        mDialog.setContentView(view);
        mDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mDialog.setCanceledOnTouchOutside(false);
        return this;
    }

    public LoadingDialog setLoadingHintMode() {
        mHint.setImageResource(R.drawable.icon_loading_dialog_loading_txt);
        return this;
    }

    public LoadingDialog setWaittingHintMode() {
        mHint.setImageResource(R.drawable.icon_loading_dialog_waitting_txt);
        return this;
    }

    public LoadingDialog setCommittingHintMode() {
        mHint.setImageResource(R.drawable.icon_loading_dialog_commit_txt);
        return this;
    }

    public void show() {
        mDialog.show();
    }
}
