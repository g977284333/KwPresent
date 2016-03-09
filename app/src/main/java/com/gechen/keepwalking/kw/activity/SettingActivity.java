package com.gechen.keepwalking.kw.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.gechen.keepwalking.R;
import com.gechen.keepwalking.kw.KwApplication;
import com.gechen.keepwalking.kw.common.manager.ConfigManager;
import com.gechen.keepwalking.kw.common.widget.ShareDialog;
import com.kw_support.base.BaseActivity;
import com.kw_support.utils.FileUtil;
import com.kw_support.utils.SdCardUtil;
import com.kw_support.view.KwAlertDialog;
import com.kw_support.view.LoadingDialog;

/**
 * Created by Administrator on 2015/8/22.
 */
public class SettingActivity extends BaseActivity {

    private CheckBox mNotifyMsg;
    private CheckBox mNotifySound;
    private CheckBox mNotifyVibrate;
    private CheckBox mLoadOrgPic;
    private CheckBox mPictureNone;
    private CheckBox mDisableCache;
    private CheckBox mCrashLogUpload;

    private TextView mSwipeBackEdge;
    private TextView mUploadSetting;
    private TextView mPictureMode;
    private TextView mClearCache;

    private String[] mUploadModes;
    private String[] mPictureModes;
    private String[] mSwipeBackEdges;

    private ConfigManager mConfigManger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();
        initDatas();
    }

    private void initView() {
        findViewById(R.id.btn_setting_swipe_back_edge).setOnClickListener(this);
        findViewById(R.id.btn_setting_notify_msg).setOnClickListener(this);
        findViewById(R.id.btn_setting_notify_sound).setOnClickListener(this);
        findViewById(R.id.btn_setting_notify_vibrate).setOnClickListener(this);
        findViewById(R.id.btn_setting_upload_setting).setOnClickListener(this);
        findViewById(R.id.btn_setting_picture_mode).setOnClickListener(this);
        findViewById(R.id.btn_setting_load_org_pic).setOnClickListener(this);
        findViewById(R.id.btn_setting_picture_none).setOnClickListener(this);
        findViewById(R.id.btn_setting_disable_cache).setOnClickListener(this);
        findViewById(R.id.btn_setting_crash_log_upload).setOnClickListener(this);
        findViewById(R.id.btn_setting_clear_cache).setOnClickListener(this);

        mSwipeBackEdge = (TextView) findViewById(R.id.tv_setting_swipe_back_edge);
        mUploadSetting = (TextView) findViewById(R.id.tv_setting_upload_setting);
        mPictureMode = (TextView) findViewById(R.id.tv_setting_picture_mode);
        mClearCache = (TextView) findViewById(R.id.tv_setting_clear_cache);

        mNotifyMsg = (CheckBox) findViewById(R.id.cb_setting_notify_msg);
        mNotifySound = (CheckBox) findViewById(R.id.cb_setting_notify_sound);
        mNotifyVibrate = (CheckBox) findViewById(R.id.cb_setting_notify_vibrate);
        mLoadOrgPic = (CheckBox) findViewById(R.id.cb_setting_notify_load_org_pic);
        mPictureNone = (CheckBox) findViewById(R.id.cb_setting_picture_none);
        mDisableCache = (CheckBox) findViewById(R.id.cb_setting_disable_cache);
        mCrashLogUpload = (CheckBox) findViewById(R.id.cb_setting_crash_log_upload);
    }

    private void initDatas() {
        mUploadModes = getResources().getStringArray(R.array.setting_upload_pic_mode);
        mPictureModes = getResources().getStringArray(R.array.setting_pic_mode);
        mSwipeBackEdges = getResources().getStringArray(R.array.setting_swipe_back_edge);

        mConfigManger = KwApplication.getInstance().getConfigManager();

        mNotifyMsg.setChecked(mConfigManger.isNotifyMsg());
        mNotifySound.setChecked(mConfigManger.isNotifySound());
        mNotifyVibrate.setChecked(mConfigManger.isNotifyVibrate());
        mPictureNone.setChecked(mConfigManger.isPicNone());
        mDisableCache.setChecked(mConfigManger.isDisableCache());
        mCrashLogUpload.setChecked(mConfigManger.isCrashLogUpload());
        mLoadOrgPic.setChecked(mConfigManger.isLoadOrigPic());

        mPictureMode.setText(mPictureModes[mConfigManger.getPictureMode()]);
        mSwipeBackEdge.setText(mSwipeBackEdges[mConfigManger.getSwipebackEdgeMode()]);
        mUploadSetting.setText(mUploadModes[mConfigManger.getUploadSetting()]);

        String cacheSize = SdCardUtil.formatMemorySize(FileUtil.getFolderSize(FileUtil.getAppCachePath()));
        mClearCache.setText(cacheSize);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.btn_setting_swipe_back_edge:
                showSwipeBackEdgeDialog();
                break;
            case R.id.btn_setting_notify_msg:
                mNotifyMsg.toggle();
                mConfigManger.setNofifyMsg(mNotifyMsg.isChecked());
                break;
            case R.id.btn_setting_notify_sound:
                mNotifySound.toggle();
                mConfigManger.setNotifySound(mNotifySound.isChecked());
                break;
            case R.id.btn_setting_notify_vibrate:
                mNotifyVibrate.toggle();
                mConfigManger.setNotifyVibrate(mNotifyVibrate.isChecked());
                break;
            case R.id.btn_setting_upload_setting:
                showUploadPictureModeDialog();
                break;
            case R.id.btn_setting_picture_mode:
                showPictureModeDialog();
                break;
            case R.id.btn_setting_load_org_pic:
                mLoadOrgPic.toggle();
                mConfigManger.setLoadOrigPic(mLoadOrgPic.isChecked());
                break;
            case R.id.btn_setting_picture_none:
                mPictureNone.toggle();
                mConfigManger.setPicNone(mPictureNone.isChecked());
                break;
            case R.id.btn_setting_disable_cache:
                mDisableCache.toggle();
                mConfigManger.setDisableCache(mDisableCache.isChecked());
                break;
            case R.id.btn_setting_crash_log_upload:
                mCrashLogUpload.toggle();
                mConfigManger.setCrashLogUpload(mCrashLogUpload.isChecked());
                break;
            case R.id.btn_setting_clear_cache:
                FileUtil.clearDirectory(FileUtil.getAppCachePath());
                break;
            default:
                break;
        }
    }

    public void showUploadPictureModeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("图片上传模式");
        builder.setSingleChoiceItems(mUploadModes, mConfigManger.getUploadSetting(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mUploadSetting.setText(mUploadModes[which]);
                mConfigManger.setUploadSetting(which);
                dialog.dismiss();
            }
        });
        builder.setNeutralButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public void showPictureModeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("图片加载模式");
        builder.setSingleChoiceItems(mPictureModes, mConfigManger.getPictureMode(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPictureMode.setText(mPictureModes[which]);
                mConfigManger.setPictureMode(which);
                dialog.dismiss();
            }
        });
        builder.setNeutralButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public void showSwipeBackEdgeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("手势返回");
        builder.setSingleChoiceItems(mSwipeBackEdges, mConfigManger.getSwipebackEdgeMode(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mSwipeBackEdge.setText(mSwipeBackEdges[which]);
                mConfigManger.setSwipebackEdgeMode(which);
                dialog.dismiss();
            }
        });
        builder.setNeutralButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
