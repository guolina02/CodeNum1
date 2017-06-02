package com.gln.codenum1.chapter6.model;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by guolina on 2017/6/2.
 */
public class Category extends DataSupport {

    private int id;
    private String name;
    private List<News> newsList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[")
                .append("id=" + id)
                .append(", name=" + name);
        if (newsList != null && newsList.size() > 0) {
            builder.append(", news={");
            for (News news: newsList) {
                builder.append(news);
            }
            builder.append("}");
        }
        builder.append("]");
        return builder.toString();
    }
}
