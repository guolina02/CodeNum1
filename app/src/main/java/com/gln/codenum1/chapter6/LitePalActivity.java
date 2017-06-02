package com.gln.codenum1.chapter6;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.gln.codenum1.BaseActivity;
import com.gln.codenum1.R;

/**
 * Created by guolina on 2017/6/2.
 */
public class LitePalActivity extends BaseActivity implements View.OnClickListener{

    private static final String TAG = LitePalActivity.class.getSimpleName();

    private MyDbHelper myDbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lite_pal);

        findViewById(R.id.btn_lite_create).setOnClickListener(this);
        findViewById(R.id.btn_lite_insert).setOnClickListener(this);
        findViewById(R.id.btn_lite_update).setOnClickListener(this);
        findViewById(R.id.btn_lite_query).setOnClickListener(this);
        findViewById(R.id.btn_lite_delete).setOnClickListener(this);
        findViewById(R.id.btn_lite_group).setOnClickListener(this);

        myDbHelper = MyDbHelper.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_lite_create:
                myDbHelper.createDb();
                break;
            case R.id.btn_lite_insert:
                myDbHelper.insert();
                break;
            case R.id.btn_lite_delete:
                myDbHelper.delete();
                break;
            case R.id.btn_lite_query:
                myDbHelper.query();
                break;
            case R.id.btn_lite_update:
                myDbHelper.update();
                break;
            case R.id.btn_lite_group:
                myDbHelper.group();
                break;
        }
    }
}
