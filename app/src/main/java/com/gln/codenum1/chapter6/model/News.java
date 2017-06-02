package com.gln.codenum1.chapter6.model;

import org.litepal.crud.DataSupport;

import java.util.Date;
import java.util.List;

/**
 * Created by guolina on 2017/6/2.
 */
public class News extends DataSupport {

    private int id;
    private String title;
    private String content;
    private Date publishDate;
    private int commentCount;

    private Introduction introduction;
    private List<Comment> commentList;
    private List<Category> categoryList;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public Introduction getIntroduction() {
        return introduction;
    }

    public void setIntroduction(Introduction introduction) {
        this.introduction = introduction;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[")
                .append("id=" + id)
                .append(", title=" + title)
                .append(", content=" + content)
                .append(", publishDate=" + publishDate)
                .append(", commentCount=" + commentCount);
        if (introduction != null) {
            builder.append(", introduction=" + introduction);
        }

        if (commentList != null && commentList.size() > 0) {
            builder.append(", commentList={");
            for (Comment news: commentList) {
                builder.append(news);
            }
            builder.append("}");
        }

        if (categoryList != null && categoryList.size() > 0) {
            builder.append(", categoryList={");
            for (Category news: categoryList) {
                builder.append(news);
            }
            builder.append("}");
        }
        builder.append("]");
        return builder.toString();
    }
}
