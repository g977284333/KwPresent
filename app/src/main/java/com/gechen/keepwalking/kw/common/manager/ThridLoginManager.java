package com.gechen.keepwalking.kw.common.manager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.gechen.keepwalking.R;
import com.mob.tools.utils.UIHandler;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * Created by Administrator on 2015/8/9.
 */
public class ThridLoginManager implements Handler.Callback, PlatformActionListener{
    private Context mContext;
    private static final int MSG_USERID_FOUND = 1;
    private static final int MSG_LOGIN = 2;
    private static final int MSG_AUTH_CANCEL = 3;
    private static final int MSG_AUTH_ERROR= 4;
    private static final int MSG_AUTH_COMPLETE = 5;


    public ThridLoginManager(Context context) {
        this.mContext = context;
        ShareSDK.initSDK(context);
    }

    public void authrizeWeiXin() {
        authorize(new Wechat(mContext));
    }

    private void authorize(Platform platform) {
        platform.removeAccount(true);

        if(platform.isValid()) {
            String userID = platform.getDb().getUserId();
            if(!TextUtils.isEmpty(userID)) {
                UIHandler.sendEmptyMessage(MSG_USERID_FOUND, this);
                login(platform.getName(), userID, null);
                return;
            }
        }
        platform.setPlatformActionListener(this);
        platform.SSOSetting(false);
        platform.showUser(null);
    }

    private void login(CharSequence name, String userID, HashMap<String, Object> userInfo) {
        Message msg = Message.obtain();
        msg.what = MSG_LOGIN;
        msg.obj = name;
        UIHandler.sendMessage(msg, this);
    }

    public void onExit() {
        ShareSDK.stopSDK(mContext);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_USERID_FOUND:
                Toast.makeText(mContext, R.string.userid_found, Toast.LENGTH_SHORT).show();
            break;
            case MSG_LOGIN:
                String text = mContext.getString(R.string.logining, msg.obj);
                Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
                System.out.println("---------------");
                break;
            case MSG_AUTH_CANCEL: {
                Toast.makeText(mContext, R.string.auth_cancel, Toast.LENGTH_SHORT).show();
                System.out.println("-------MSG_AUTH_CANCEL--------");
            }
            break;
            case MSG_AUTH_ERROR: {
                Toast.makeText(mContext, R.string.auth_error, Toast.LENGTH_SHORT).show();
                System.out.println("-------MSG_AUTH_ERROR--------");
            }
            break;
            case MSG_AUTH_COMPLETE: {
                Toast.makeText(mContext, R.string.auth_complete, Toast.LENGTH_SHORT).show();
                System.out.println("--------MSG_AUTH_COMPLETE-------");
            }
            break;
        }

        return false;
    }

    @Override
    public void onComplete(Platform platform, int action, HashMap<String, Object> hashMap) {
        if(action == Platform.ACTION_USER_INFOR) {
            UIHandler.sendEmptyMessage(MSG_AUTH_COMPLETE, this);
            login(platform.getName(), platform.getDb().getUserId(), hashMap);
        }
        System.out.println(hashMap);
        System.out.println("------User Name ---------" + platform.getDb().getUserName());
        System.out.println("------User ID ---------" + platform.getDb().getUserId());
        System.out.println("------User icon ---------" + platform.getDb().getUserIcon());
    }

    @Override
    public void onError(Platform platform, int action, Throwable throwable) {
        if(action == Platform.ACTION_USER_INFOR) {
            UIHandler.sendEmptyMessage(MSG_AUTH_ERROR, this);
        }
        throwable.printStackTrace();
    }

    @Override
    public void onCancel(Platform platform, int action) {
        if(action == Platform.ACTION_USER_INFOR) {
            UIHandler.sendEmptyMessage(MSG_AUTH_CANCEL, this);
        }
    }
}
