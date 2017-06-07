package com.gln.codenum1.chapter14.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by guolina on 2017/6/6.
 */
public class Basic {

    @SerializedName("city")
    public String cityName;

    @SerializedName("id")
    public String weatherId;

    @SerializedName("update")
    public Update update;

    public class Update {
        @SerializedName("loc")
        public String updateTime;
    }
}
