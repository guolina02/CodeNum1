package com.gln.codenum1.chapter12;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gln.codenum1.BaseActivity;
import com.gln.codenum1.R;

/**
 * Created by guolina on 2017/6/5.
 */
public class FruitDetailActivity extends BaseActivity {

    public static final String FRUIT_NAME = "fruit_name";
    public static final String FRUIT_IMAGE = "fruit_image";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit_detail);

        Intent intent = getIntent();
        String name = intent.getStringExtra(FRUIT_NAME);
        String image = intent.getStringExtra(FRUIT_IMAGE);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_fruit_detail);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        ImageView imageView = (ImageView) findViewById(R.id.image_fruit_detail);
        TextView textView = (TextView) findViewById(R.id.text_fruit_detail);

//        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        collapsingToolbarLayout.setTitle(name);
        Glide.with(this).load(image).into(imageView);
        textView.setText(generateFruitName(name));
    }

    private String generateFruitName(String name) {
        StringBuilder builder = new StringBuilder();
        for (int i=0;i<500;i++) {
            builder.append(name);
        }
        return builder.toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
