package com.gln.codenum1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.gln.codenum1.chapter5.LoginActivity;

/**
 * Created by guolina on 2017/6/2.
 */
public class BaseActivity extends AppCompatActivity {

    private ForceOfflineReceiver mReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(((MyApplication)getApplication()).isLandscape() ? ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE : ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ActivityController.onActivityCreate(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.gln.chapter5.FORCE_OFFLINE");
        mReceiver = new ForceOfflineReceiver();
        registerReceiver(mReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
            mReceiver = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityController.onActivityDestroy(this);
    }

    class ForceOfflineReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, Intent intent) {
            AlertDialog dialog = new AlertDialog.Builder(context)
                    .setTitle("Warning")
                    .setMessage("You are forced to offline, please try to login again.")
                    .setCancelable(false)
                    .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityController.finishAll();
                            Intent intent = new Intent(context, LoginActivity.class);
                            context.startActivity(intent);
                            dialog.dismiss();
                        }
                    })
                    .create();
            dialog.show();
        }
    }
}
