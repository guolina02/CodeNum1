package com.gln.codenum1;

import android.app.Activity;
import android.util.Log;

import com.gln.codenum1.chapter13.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guolina on 2017/6/2.
 */
public class ActivityController {

    private static final String TAG = ActivityController.class.getSimpleName();

    public static List<Activity> activityList = new ArrayList<>();

    public static void onActivityCreate(Activity activity) {
        activityList.add(activity);
        LogUtils.d(TAG, "onActivityCreate: " + activity.getClass().getSimpleName());
    }

    public static void onActivityDestroy(Activity activity) {
        activityList.remove(activity);
        LogUtils.d(TAG, "onActivityDestroy: " + activity.getClass().getSimpleName());
    }

    public static void finishAll() {
        for (Activity activity: activityList) {
            if (!activity.isFinishing()) {
                activity.finish();
                LogUtils.d(TAG, "finish: " + activity.getClass().getSimpleName());
            }
        }
        activityList.clear();
    }
}
