package com.gln.codenum1.chapter12;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.gln.codenum1.BaseActivity;
import com.gln.codenum1.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guolina on 2017/6/5.
 */
public class DrawerLayoutActivity extends BaseActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private FloatingActionButton mFloatingButton;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefresh;

    private FruitAdapter mAdapter;
    private List<Fruit> mFruitList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setCheckedItem(R.id.nav_call);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

        mFloatingButton = (FloatingActionButton) findViewById(R.id.floating_button);
        mFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Data deleted", Snackbar.LENGTH_SHORT)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(DrawerLayoutActivity.this, "Data restored", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        });

        initData();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager lm = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(lm);
        mAdapter = new FruitAdapter(mFruitList);
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        mSwipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFruits();
            }
        });
    }

    private void initData() {
        mFruitList = new ArrayList<>();
        mFruitList.add(new Fruit("Apple", "https://github.com/guolindev/booksource/blob/master/chapter12/MaterialTest/app/src/main/res/drawable-xxhdpi/apple.jpg"));
        mFruitList.add(new Fruit("Banana", "https://github.com/guolindev/booksource/blob/master/chapter12/MaterialTest/app/src/main/res/drawable-xxhdpi/banana.jpg"));
        mFruitList.add(new Fruit("Orange", "https://github.com/guolindev/booksource/blob/master/chapter12/MaterialTest/app/src/main/res/drawable-xxhdpi/orange.jpg"));
        mFruitList.add(new Fruit("Pear", "https://github.com/guolindev/booksource/blob/master/chapter12/MaterialTest/app/src/main/res/drawable-xxhdpi/pear.jpg"));
        mFruitList.add(new Fruit("Watermelon", "https://github.com/guolindev/booksource/blob/master/chapter12/MaterialTest/app/src/main/res/drawable-xxhdpi/watermelon.jpg"));
        mFruitList.add(new Fruit("Grape", "https://github.com/guolindev/booksource/blob/master/chapter12/MaterialTest/app/src/main/res/drawable-xxhdpi/grape.jpg"));
        mFruitList.add(new Fruit("Pineapple", "https://github.com/guolindev/booksource/blob/master/chapter12/MaterialTest/app/src/main/res/drawable-xxhdpi/pineapple.jpg"));
        mFruitList.add(new Fruit("Strawberry", "https://github.com/guolindev/booksource/blob/master/chapter12/MaterialTest/app/src/main/res/drawable-xxhdpi/strawberry.jpg"));
        mFruitList.add(new Fruit("Cherry", "https://github.com/guolindev/booksource/blob/master/chapter12/MaterialTest/app/src/main/res/drawable-xxhdpi/cherry.jpg"));
        mFruitList.add(new Fruit("Mango", "https://github.com/guolindev/booksource/blob/master/chapter12/MaterialTest/app/src/main/res/drawable-xxhdpi/mango.jpg"));
    }

    private void refreshFruits() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initData();
                        mAdapter.notifyDataSetChanged();
                        mSwipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String text = null;
        switch (item.getItemId()) {
            case R.id.toolbar_backup:
                text = "Backup";
                break;
            case R.id.toolbar_delete:
                text = "Delete";
                break;
            case R.id.toolbar_settings:
                text = "Settings";
                break;
            case android.R.id.home:
                text = "menu";
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        Toast.makeText(this, text == null ? "null" : text, Toast.LENGTH_SHORT).show();
        return true;
    }
}
