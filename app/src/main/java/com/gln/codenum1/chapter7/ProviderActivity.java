package com.gln.codenum1.chapter7;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.gln.codenum1.BaseActivity;
import com.gln.codenum1.R;

/**
 * Created by guolina on 2017/6/2.
 */
public class ProviderActivity extends BaseActivity implements View.OnClickListener{

    private static final String TAG = ProviderActivity.class.getSimpleName();

    private ContentResolver contentResolver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);

        findViewById(R.id.btn_provider_delete).setOnClickListener(this);
        findViewById(R.id.btn_provider_insert).setOnClickListener(this);
        findViewById(R.id.btn_provider_query).setOnClickListener(this);
        findViewById(R.id.btn_provider_update).setOnClickListener(this);

        contentResolver = getContentResolver();
    }

    @Override
    public void onClick(View v) {
        Uri uri;
        switch (v.getId()) {
            case R.id.btn_provider_delete:
                uri = Uri.parse("content://com.gln.codenum1.chapter7/news");
                contentResolver.delete(uri, null, null);
                break;
            case R.id.btn_provider_insert:
                uri = Uri.parse("content://com.gln.codenum1.chapter7/news/1");
                contentResolver.insert(uri, null);
                break;
            case R.id.btn_provider_query:
                uri = Uri.parse("content://com.gln.codenum1.chapter7/category/1");
                Cursor cursor = contentResolver.query(uri, new String[]{"id", "name"}, null, null, "id desc");
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        Log.d(TAG, "category [id: " + cursor.getInt(cursor.getColumnIndex("id")) + ", name: " + cursor.getString(cursor.getColumnIndex("name")));
                    }
                }
                break;
            case R.id.btn_provider_update:
                uri = Uri.parse("content://com.gln.codenum1.chapter7/category");
                contentResolver.update(uri, null, null, null);
                break;
        }
    }
}
