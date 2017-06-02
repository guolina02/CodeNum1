package com.gln.codenum1.chapter6;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.StringBuilderPrinter;

import com.gln.codenum1.chapter6.model.Category;
import com.gln.codenum1.chapter6.model.Comment;
import com.gln.codenum1.chapter6.model.Introduction;
import com.gln.codenum1.chapter6.model.News;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by guolina on 2017/6/2.
 */
public class MyDbHelper {

    private static final String TAG = MyDbHelper.class.getSimpleName();

    private static MyDbHelper sInstance;

    private SQLiteDatabase db;

    private MyDbHelper() {

    }

    public static MyDbHelper getInstance() {
        if (sInstance == null) {
            synchronized (MyDbHelper.class) {
                if (sInstance == null) {
                    sInstance = new MyDbHelper();
                }
            }
        }

        return sInstance;
    }

    public void createDb() {
        db = Connector.getDatabase();
    }

    public void insert() {
        News news1 = new News();
        news1.setTitle("NewsTitle1");
        news1.setContent("NewsContent2");
        news1.setPublishDate(new Date());

        News news2 = new News();
        news2.setTitle("NewsTitle2");
        news2.setContent("NewsContent2");
        news2.setPublishDate(new Date());

        Introduction introduction1 = new Introduction();
        introduction1.setGuide("IntroductionGuide1");
        introduction1.setDigest("IntroductionDigest1");

        Introduction introduction2 = new Introduction();
        introduction2.setGuide("IntroductionGuide2");
        introduction2.setDigest("IntroductionDigest2");

        Comment comment1 = new Comment();
        comment1.setContent("comment1");
        comment1.setPublishDate(new Date());

        Comment comment2 = new Comment();
        comment2.setContent("comment2");
        comment2.setPublishDate(new Date());

        Comment comment3 = new Comment();
        comment3.setContent("comment3");
        comment3.setPublishDate(new Date());

        Category category1 = new Category();
        category1.setName("Category1");

        Category category2 = new Category();
        category2.setName("Category2");

        news1.setCategoryList(Arrays.asList(category1, category2));
        news1.setIntroduction(introduction1);
        news1.setCommentList(Arrays.asList(comment1, comment2));
        news1.setCommentCount(2);

        news2.setCategoryList(Arrays.asList(category1));
        news2.setIntroduction(introduction2);
        news2.setCommentList(Arrays.asList(comment3));
        news2.setCommentCount(1);

        comment1.setNews(news1);
        comment2.setNews(news1);
        comment3.setNews(news2);

        category1.setNewsList(Arrays.asList(news1, news2));
        category2.setNewsList(Arrays.asList(news1));

        news1.save();
        news2.save();

        introduction1.save();
        introduction2.save();

        DataSupport.saveAll(Arrays.asList(comment1, comment2, comment3));
        DataSupport.saveAll(Arrays.asList(category1, category2));
    }

    public void delete() {
        DataSupport.delete(News.class, 2);
        DataSupport.deleteAll(Comment.class, "content=?", "content1");
    }

    public void query() {
        Comment comment2 = DataSupport.find(Comment.class, 2);
        Comment commentFirst = DataSupport.findFirst(Comment.class);
        Comment commentLast = DataSupport.findLast(Comment.class);

        Log.d(TAG, "comment id:2 " + comment2);
        Log.d(TAG, "comment first " + commentFirst);
        Log.d(TAG, "comment last " + commentLast);

        List<Introduction> introductionList = DataSupport.findAll(Introduction.class);
        for (Introduction introduction: introductionList) {
            Log.d(TAG, "introduction " + introduction);
        }

        List<News> newsList = DataSupport.where("commentCount > ?", "0").find(News.class);
        for (News news: newsList) {
            Log.d(TAG, "news commentCount > 0 " + news);
        }

        List<News> newsList1 = DataSupport.select("title", "content").where("commentCount > ?", "0").find(News.class);
        for (News news: newsList1) {
            Log.d(TAG, "news select title&content commentCount > 0 " + news);
        }

        List<News> newsList2 = DataSupport.select("title", "content").where("commentCount > ?", "0")
                .order("publishDate desc").limit(10).offset(1).find(News.class);
        for (News news: newsList2) {
            Log.d(TAG, "news select title&content commentCount > 0 order limit offset" + news);
        }

        Cursor cursor = DataSupport.findBySQL("select id, name from category where name = ?", "Category1");
        while (cursor.moveToNext()) {
            Category category = new Category();
            category.setId(cursor.getInt(cursor.getColumnIndex("id")));
            category.setName(cursor.getString(cursor.getColumnIndex("name")));
            Log.d(TAG, "category " + category);
        }
        cursor.close();
    }

    public void update() {
        ContentValues values = new ContentValues();
        values.put("guide", "newGuide");
        DataSupport.update(Introduction.class, values, 1);

        ContentValues values1 = new ContentValues();
        values1.put("name", "newName");
        DataSupport.updateAll(Category.class, values1, "name=?", "Category2");
    }

    public void group() {
        int count = DataSupport.count(News.class);
        int sum = DataSupport.sum(News.class, "commentCount", int.class);
        double average = DataSupport.average(News.class, "commentCount");
        int max = DataSupport.max(News.class, "commentCount", int.class);
        int min = DataSupport.min(News.class, "commentCount", int.class);
        Log.d(TAG, "count=" + count + ", sum=" + sum + ", average=" + average + ", max=" + max + ", min=" + min);
    }

    public Cursor query(String db, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        StringBuilder builder = new StringBuilder();
        builder.append("select ");
        if (projection != null && projection.length > 0) {
            for (int i=0;i<projection.length;i++) {
                if (i > 0) {
                    builder.append(",").append(projection[i]);
                } else {
                    builder.append(projection[i]);
                }
            }
        } else {
            builder.append("*");
        }
        builder.append(" from ").append(db);
        builder.append(" where ");
        String where = String.format(selection, selectionArgs);
        builder.append(where);
        builder.append(" order by ").append(sortOrder);
        Log.d(TAG, where);
        Log.d(TAG, builder.toString());
        return DataSupport.findBySQL(builder.toString());
    }
}
