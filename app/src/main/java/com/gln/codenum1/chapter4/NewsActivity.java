package com.gln.codenum1.chapter4;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.gln.codenum1.BaseActivity;
import com.gln.codenum1.R;
import com.gln.codenum1.chapter13.LogUtils;

/**
 * Created by guolina on 2017/6/1.
 */
public class NewsActivity extends BaseActivity {

    private boolean isTwoPane;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        LogUtils.d("NewsActivity", "onCreate");

        if (findViewById(R.id.fragment_news_content) == null) {
            isTwoPane = false;
        } else {
            isTwoPane = true;
        }
    }

    public boolean isTwoPane() {
        return isTwoPane;
    }
}
