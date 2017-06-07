package com.gln.codenum1.chapter14;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gln.codenum1.BaseActivity;
import com.gln.codenum1.R;
import com.gln.codenum1.chapter14.gson.Forecast;
import com.gln.codenum1.chapter14.gson.Weather;
import com.gln.codenum1.chapter14.utils.DataHandler;
import com.gln.codenum1.chapter14.utils.HttpUtils;
import com.gln.codenum1.chapter14.utils.SharedPreferenceUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by guolina on 2017/6/6.
 */
public class WeatherActivity extends BaseActivity {

    private DrawerLayout mDrawer;
    private Toolbar mToolbar;
    private SwipeRefreshLayout mRefreshLayout;

    private TextView mTextCity;
    private TextView mTextUpdateTime;

    private TextView mTextNowDegree;
    private TextView mTextNowInfo;

    private LinearLayout mLayoutForecast;

    private TextView mTextAqi;
    private TextView mTextPM25;

    private TextView mTextSuggComfort;
    private TextView mTextSuggCar;
    private TextView mTextSuggSport;

    private ImageView mImageBg;

    private String mWeatherId;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        mDrawer = (DrawerLayout) findViewById(R.id.weather_drawer);

        mToolbar = (Toolbar) findViewById(R.id.weather_toolbar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.weather_refresh);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                searchWeather(mWeatherId);
            }
        });

        mTextCity= (TextView) findViewById(R.id.text_weather_city);
        mTextUpdateTime = (TextView) findViewById(R.id.text_weather_update_time);

        mTextNowDegree = (TextView) findViewById(R.id.text_weather_now_degree);
        mTextNowInfo = (TextView) findViewById(R.id.text_weather_now_info);

        mLayoutForecast = (LinearLayout) findViewById(R.id.layout_weather_forecast);

        mTextAqi = (TextView) findViewById(R.id.text_weather_aqi);
        mTextPM25 = (TextView) findViewById(R.id.text_weather_pm25);

        mTextSuggComfort = (TextView) findViewById(R.id.text_weather_sugg_comfort);
        mTextSuggCar = (TextView) findViewById(R.id.text_weather_sugg_car);
        mTextSuggSport = (TextView) findViewById(R.id.text_weather_sugg_sport);

        mImageBg = (ImageView) findViewById(R.id.image_bg);

        initData();
        initImage();
    }

    private void initData() {
        String weatherString = SharedPreferenceUtils.getWeatherString();
        if (!TextUtils.isEmpty(weatherString)) {
            Weather weather = DataHandler.handleWeatherData(weatherString);
            mWeatherId = weather.basic.weatherId;
            showWeatherInfo(weather);
        } else {
            if (!TextUtils.isEmpty(mWeatherId)) {
                searchWeather(mWeatherId);
            }
        }
    }

    public void searchWeather(final String weatherId) {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        }

        mWeatherId = weatherId;
        if (!TextUtils.isEmpty(mWeatherId)){
            String url = "http://guolin.tech/api/weather?cityid=" + mWeatherId + "&key=794f50dd95264c6897b6223097ad2f7e";
            HttpUtils.sendOkHttpRequest(url, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mRefreshLayout.setRefreshing(false);
                            Toast.makeText(WeatherActivity.this, "Load weather failed!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String responseText = response.body().string();
                    final Weather weather = DataHandler.handleWeatherData(responseText);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mRefreshLayout.setRefreshing(false);
                            if (weather != null && "ok".equals(weather.status)) {
                                SharedPreferenceUtils.putWeatherString(responseText);
                                showWeatherInfo(weather);
                            } else {
                                Toast.makeText(WeatherActivity.this, "Load weather failed!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
        }
    }

    private void showWeatherInfo(Weather weather) {
        mTextCity.setText(weather.basic.cityName);
        mTextUpdateTime.setText(weather.basic.update.updateTime);

        mTextNowDegree.setText(weather.now.tmp + "C");
        mTextNowInfo.setText(weather.now.cond.txt);

        if (weather.aqi != null) {
            mTextAqi.setText(weather.aqi.city.aqi);
            mTextPM25.setText(weather.aqi.city.pm25);
        }

        mTextSuggComfort.setText("舒适度：" + weather.suggestion.comf.txt);
        mTextSuggCar.setText("洗车指数：" + weather.suggestion.cw.txt);
        mTextSuggSport.setText("运动建议：" + weather.suggestion.sport.txt);

        mLayoutForecast.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);
        if (weather.forecastList != null && weather.forecastList.size() > 0) {
            for (Forecast forecast: weather.forecastList) {
                View itemView = inflater.inflate(R.layout.weather_forecast_item, mLayoutForecast, false);
                TextView textDate = (TextView) itemView.findViewById(R.id.text_weather_forecast_date);
                TextView textInfo = (TextView) itemView.findViewById(R.id.text_weather_forecast_info);
                TextView textMax = (TextView) itemView.findViewById(R.id.text_weather_forecast_max);
                TextView textMin = (TextView) itemView.findViewById(R.id.text_weather_forecast_min);

                textDate.setText(forecast.date);
                textInfo.setText(forecast.cond.txt_d);
                textMax.setText(forecast.tmp.max);
                textMin.setText(forecast.tmp.min);
                mLayoutForecast.addView(itemView);
            }
        }

        Intent intent = new Intent(this, AutoUpdateService.class);
        startService(intent);
    }

    private void initImage() {
        String url = SharedPreferenceUtils.getImageUrl();
        if (!TextUtils.isEmpty(url)) {
            Glide.with(this).load(url).into(mImageBg);
        } else {
            searchImage();
        }
    }

    private void searchImage() {
        HttpUtils.sendOkHttpRequest("http://guolin.tech/api/bing_pic", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String url = response.body().string();
                SharedPreferenceUtils.putImageUrl(url);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(WeatherActivity.this).load(url).into(mImageBg);
                    }
                });
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                break;
        }
        return true;
    }
}
