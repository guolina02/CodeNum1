package com.gln.codenum1;

import android.app.Application;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by guolina on 2017/6/2.
 */
public class MyApplication extends Application {

    private boolean mLandscape;

    @Override
    public void onCreate() {
        super.onCreate();

        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);

        if (dm.widthPixels > dm.heightPixels) {
            mLandscape = true;
        } else {
            mLandscape = false;
        }
    }

    public boolean isLandscape() {
        return mLandscape;
    }
}
