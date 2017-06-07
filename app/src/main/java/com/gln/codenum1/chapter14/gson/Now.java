package com.gln.codenum1.chapter14.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by guolina on 2017/6/6.
 */
public class Now {

    @SerializedName("tmp")
    public String tmp;

    @SerializedName("cond")
    public Cond cond;

    public class Cond {
        @SerializedName("txt")
        public String txt;
    }
}
