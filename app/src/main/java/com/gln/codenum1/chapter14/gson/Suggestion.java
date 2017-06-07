package com.gln.codenum1.chapter14.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by guolina on 2017/6/6.
 */
public class Suggestion {

    @SerializedName("comf")
    public Detail comf;

    @SerializedName("cw")
    public Detail cw;

    @SerializedName("sport")
    public Detail sport;

    public class Detail {
        @SerializedName("txt")
        public String txt;
    }
}
