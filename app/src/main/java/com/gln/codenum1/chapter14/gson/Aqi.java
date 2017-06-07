package com.gln.codenum1.chapter14.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by guolina on 2017/6/6.
 */
public class Aqi {

    @SerializedName("city")
    public AqiCity city;

    public class AqiCity {
        @SerializedName("aqi")
        public String aqi;

        @SerializedName("pm25")
        public String pm25;
    }
}
