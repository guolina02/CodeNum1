package com.gln.codenum1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.gln.codenum1.chapter4.NewsActivity;
import com.gln.codenum1.chapter5.ForceOfflineActivity;
import com.gln.codenum1.chapter6.LitePalActivity;
import com.gln.codenum1.chapter7.PermissionActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_chapter4).setOnClickListener(this);
        findViewById(R.id.btn_chapter5).setOnClickListener(this);
        findViewById(R.id.btn_chapter6).setOnClickListener(this);
        findViewById(R.id.btn_chapter7).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.btn_chapter4:
                intent.setClass(this, NewsActivity.class);
                break;
            case R.id.btn_chapter5:
                intent.setClass(this, ForceOfflineActivity.class);
                break;
            case R.id.btn_chapter6:
                intent.setClass(this, LitePalActivity.class);
                break;
            case R.id.btn_chapter7:
                intent.setClass(this, PermissionActivity.class);
                break;
        }
        startActivity(intent);
    }
}
