package com.gln.codenum1.chapter14.utils;

import com.gln.codenum1.chapter13.LogUtils;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by guolina on 2017/6/6.
 */
public class HttpUtils {

    public static void sendOkHttpRequest(String address, Callback callback) {
        LogUtils.d("HttpUtils", "url: " + address);
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
