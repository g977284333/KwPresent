package com.kw_support.manager;

import android.app.Activity;

import java.util.Stack;

public class ActivityManager {

    private static ActivityManager instance;
    private Stack<Activity> activityStack;//activity栈

    private ActivityManager() {
    }
    //单例模式
    public static ActivityManager getInstance() {
        if (instance == null) {
            instance = new ActivityManager();
        }
        return instance;
    }
    //把一个activity压入栈中
    public void pushActivity(Activity actvity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(actvity);
    }
    //获取栈顶的activity，先进后出原则
    public Activity getLastActivity() {
        return activityStack.lastElement();
    }
    //移除一个activity
    public void popActivity(Activity activity) {
        if (activityStack != null && activityStack.size() > 0) {
            if (activity != null) {
                activity.finish();
                activityStack.remove(activity);
                activity = null;
            }

        }
    }
    //退出所有activity
    public void finishAllActivity() {
        if (activityStack != null) {
            while (activityStack.size() > 0) {
                Activity activity = getLastActivity();
                if (activity == null) break;
                popActivity(activity);
            }
        }
    }}

