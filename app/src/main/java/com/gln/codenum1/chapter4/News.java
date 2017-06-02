package com.gln.codenum1.chapter4;


/**
 * Created by guolina on 2017/6/1.
 */
public class News {

    private String title;
    private String content;

    private News(Builder builder) {
        this.title = builder.title;
        this.content = builder.content;
    }

    public String title() {
        return this.title;
    }

    public String content() {
        return this.content;
    }

    public static class Builder {
        private String title;
        private String content;

        public Builder() {

        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public News build() {
            return new News(this);
        }
    }
}
