package com.gechen.keepwalking.kw.common.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.gechen.keepwalking.R;
import com.kw_support.utils.UiUtil;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.tencent.weibo.TencentWeibo;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

public class ShareDialog extends Dialog {

    private static final int MSG_SHARE_NONE = 0;
    private static final int MSG_SHARE_CANCEL = 1;
    private static final int MSG_SHARE_ERROR = 2;
    private static final int MSG_SHARE_COMPLETE = 3;

    private String mTitle;
    private String mText;
    private String mImageUrl;
    private String mWebUrl;

    private Handler mShareHandler;

    public ShareDialog(Context context) {
        super(context);
    }

    public ShareDialog(Context context, int theme) {
        super(context, theme);
    }

    public void setShareContent(String title, String text, String imageUrl, String webUrl) {
        mTitle = title;
        mText = text;
        mImageUrl = imageUrl;
        mWebUrl = webUrl;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_share);

        mShareHandler = new Handler(mShareCallback);

        getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        TextView btnShareWechat = (TextView) findViewById(R.id.btn_share_wechat);
        btnShareWechat.setOnClickListener(mBtnShareWechatOnClickListener);

        TextView btnShareWechatMoments = (TextView) findViewById(R.id.btn_share_wechat_moments);
        btnShareWechatMoments.setOnClickListener(mBtnShareWechatMomentsOnClickListener);

        TextView btnShareTencent = (TextView) findViewById(R.id.btn_share_tencent);
        btnShareTencent.setOnClickListener(mBtnShareTencentOnClickListener);

        TextView btnShareTencentWeibo = (TextView) findViewById(R.id.btn_share_tencent_weibo);
        btnShareTencentWeibo.setOnClickListener(mBtnShareTencentWeiboOnClickListener);

        TextView btnShareQZone = (TextView) findViewById(R.id.btn_share_qzone);
        btnShareQZone.setOnClickListener(mBtnShareQZoneOnClickListener);

        TextView btnShareSinaWeibo = (TextView) findViewById(R.id.btn_share_sina_weibo);
        btnShareSinaWeibo.setOnClickListener(mBtnShareSinaWeiboOnClickListener);

        ImageView btnCancel = (ImageView) findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(mBtnCancelOnClickListener);
    }

    private View.OnClickListener mBtnShareWechatOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            shareByWechat(Wechat.NAME);
        }

    };

    private View.OnClickListener mBtnShareWechatMomentsOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            shareByWechat(WechatMoments.NAME);
        }

    };

    private View.OnClickListener mBtnShareTencentOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            shareByTencent(QQ.NAME);
        }

    };

    private View.OnClickListener mBtnShareTencentWeiboOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            shareByOther(TencentWeibo.NAME);
        }

    };

    private View.OnClickListener mBtnShareQZoneOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            shareByTencent(QZone.NAME);
        }

    };

    private View.OnClickListener mBtnShareSinaWeiboOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            shareByOther(SinaWeibo.NAME);
        }

    };

    private void shareByWechat(String name) {
        Platform platform = ShareSDK.getPlatform(getContext(), name);
        ShareParams shareParams = new ShareParams();
        shareParams.setShareType(Platform.SHARE_WEBPAGE);
        shareParams.setUrl(mWebUrl);
        shareParams.setTitle(mTitle);
        shareParams.setText(mText);
        shareParams.setImageUrl(mImageUrl);
        platform.setPlatformActionListener(mPlatformActionListener);
        platform.share(shareParams);
        dismiss();
    }

    private void shareByTencent(String name) {
        Platform platform = ShareSDK.getPlatform(getContext(), name);
        ShareParams shareParams = new ShareParams();
        shareParams.setTitle(mTitle);
        shareParams.setTitleUrl(mWebUrl);
        shareParams.setText(mText);
        shareParams.setImageUrl(mImageUrl);
        platform.setPlatformActionListener(mPlatformActionListener);
        platform.share(shareParams);
        dismiss();
    }

    private void shareByOther(String name) {
        Platform platform = ShareSDK.getPlatform(getContext(), name);
        ShareParams shareParams = new ShareParams();
        shareParams.setText(mText + mWebUrl);
        shareParams.setImageUrl(mImageUrl);
        platform.setPlatformActionListener(mPlatformActionListener);
        platform.share(shareParams);
        dismiss();
    }

    private View.OnClickListener mBtnCancelOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            dismiss();
        }

    };

    private PlatformActionListener mPlatformActionListener = new PlatformActionListener() {

        @Override
        public void onCancel(Platform platform, int action) {
            mShareHandler.sendEmptyMessage(MSG_SHARE_CANCEL);
        }

        @Override
        public void onComplete(Platform platform, int action, HashMap<String, Object> res) {
            boolean isSinaWeibo = SinaWeibo.NAME.equals(platform.getName());
            mShareHandler.sendEmptyMessage(isSinaWeibo ? MSG_SHARE_NONE : MSG_SHARE_COMPLETE);
        }

        @Override
        public void onError(Platform platform, int action, Throwable throwable) {
            Message.obtain(mShareHandler, MSG_SHARE_ERROR, throwable).sendToTarget();
        }

    };

    private Handler.Callback mShareCallback = new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SHARE_CANCEL:
                    UiUtil.showToast(getContext(), R.string.share_canceled);
                    break;
                case MSG_SHARE_ERROR:
                    String expName = msg.obj.getClass().getSimpleName();
                    boolean isWechatNotExist = "WechatClientNotExistException".equals(expName);
                    UiUtil.showToast(getContext(), isWechatNotExist ? R.string.wechat_client_inavailable : R.string.share_failed);
                    break;
                case MSG_SHARE_COMPLETE:
                    UiUtil.showToast(getContext(), R.string.share_completed);
                    break;
            }

            return false;
        }

    };

}