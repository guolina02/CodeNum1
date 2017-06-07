package com.gln.codenum1.chapter14;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.gln.codenum1.chapter14.gson.Weather;
import com.gln.codenum1.chapter14.utils.DataHandler;
import com.gln.codenum1.chapter14.utils.HttpUtils;
import com.gln.codenum1.chapter14.utils.SharedPreferenceUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by guolina on 2017/6/7.
 */
public class AutoUpdateService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        updateWeather();
        updateImage();

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour = 8 * 60 * 60 * 1000;
        Intent i = new Intent(this, AutoUpdateService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, i, 0);
        alarmManager.cancel(pendingIntent);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + anHour, pendingIntent);

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void updateWeather() {
        String weatherString = SharedPreferenceUtils.getWeatherString();
        if (!TextUtils.isEmpty(weatherString)) {
            Weather weather = DataHandler.handleWeatherData(weatherString);
            String weatherId = weather.basic.weatherId;
            String url = "http://guolin.tech/api/weather?cityid=" + weatherId + "&key=794f50dd95264c6897b6223097ad2f7e";
            HttpUtils.sendOkHttpRequest(url, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseString = response.body().string();
                    Weather weather1 = DataHandler.handleWeatherData(responseString);
                    if (weather1 != null && "ok".equals(weather1.status)) {
                        SharedPreferenceUtils.putWeatherString(responseString);
                    }
                }
            });
        }
    }

    private void updateImage() {
        HttpUtils.sendOkHttpRequest("http://guolin.tech/api/bing_pic", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String url = response.body().string();
                SharedPreferenceUtils.putImageUrl(url);
            }
        });
    }
}
