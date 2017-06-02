package com.gln.codenum1.chapter6.model;

import org.litepal.crud.DataSupport;

import java.util.Date;

/**
 * Created by guolina on 2017/6/2.
 */
public class Comment extends DataSupport {

    private int id;
    private String content;
    private Date publishDate;
    private News news;

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[")
                .append("id=" + id)
                .append(", content=" + content)
                .append(", publishDate=" + publishDate);
        if (news != null) {
            builder.append(", news=" + news);
        }
        builder.append("]");
        return builder.toString();
    }
}
