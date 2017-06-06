package com.gln.codenum1;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.baidu.mapapi.SDKInitializer;

import org.litepal.LitePalApplication;

/**
 * Created by guolina on 2017/6/2.
 */
public class MyApplication extends LitePalApplication {

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
