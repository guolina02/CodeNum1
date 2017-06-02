package com.gln.codenum1.chapter4;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.gln.codenum1.BaseActivity;
import com.gln.codenum1.R;

/**
 * Created by guolina on 2017/6/1.
 *
 * 适配Activity
 * 给手机和平板适配不同的Fragment内容
 */
public class NewsContentActivity extends BaseActivity {

    private NewsContentFragment mNewsContentFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_content);

        mNewsContentFragment = (NewsContentFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_news_content);

        Intent intent = getIntent();
        if (intent != null) {
            String title = intent.getStringExtra("title");
            String content = intent.getStringExtra("content");
            mNewsContentFragment.refresh(title, content);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
