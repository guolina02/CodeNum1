package com.gln.codenum1.chapter5;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.gln.codenum1.BaseActivity;
import com.gln.codenum1.R;

/**
 * Created by guolina on 2017/6/2.
 */
public class ForceOfflineActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_force_offline);

        findViewById(R.id.btn_force_offline).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.gln.chapter5.FORCE_OFFLINE");
                sendBroadcast(intent);
            }
        });
    }
}
