package com.gln.codenum1.chapter14.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by guolina on 2017/6/6.
 */
public class Forecast {

    @SerializedName("date")
    public String date;

    @SerializedName("tmp")
    public Tmp tmp;

    @SerializedName("cond")
    public Cond cond;

    public class Tmp {
        @SerializedName("max")
        public String max;

        @SerializedName("min")
        public String min;
    }

    public class Cond {
        @SerializedName("txt_d")
        public String txt_d;
    }
}
