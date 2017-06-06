package com.gln.codenum1.chapter7;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.gln.codenum1.chapter6.MyDbHelper;

/**
 * Created by guolina on 2017/6/2.
 */
public class MyProvider extends ContentProvider {

    public static final int NEWS_DIR = 0;
    public static final int NEWS_ITEM = 1;
    public static final int CATEGORY_DIR = 2;
    public static final int CATEGORY_ITEM = 3;

    private static final String AUTHORITY = "com.gln.codenum1.chapter7";

    private static UriMatcher uriMatcher;

    private MyDbHelper myDbHelper;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "news", NEWS_DIR);
        uriMatcher.addURI(AUTHORITY, "news/#", NEWS_ITEM);
        uriMatcher.addURI(AUTHORITY, "category", CATEGORY_DIR);
        uriMatcher.addURI(AUTHORITY, "category/#", CATEGORY_ITEM);
    }

    @Override
    public boolean onCreate() {
        myDbHelper = MyDbHelper.getInstance();
        myDbHelper.createDb();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor = null;
        switch (uriMatcher.match(uri)) {
            case NEWS_DIR:
                cursor = myDbHelper.query("news", projection, selection, selectionArgs, sortOrder);
                break;
            case NEWS_ITEM:
                String id = uri.getPathSegments().get(1);
                cursor = myDbHelper.query("news", projection, "id = %s", new String[]{id}, sortOrder);
                break;
            case CATEGORY_DIR:
                cursor = myDbHelper.query("category", projection, selection, selectionArgs, sortOrder);
                break;
            case CATEGORY_ITEM:
                String cateId = uri.getPathSegments().get(1);
                cursor = myDbHelper.query("category", projection, "id = ?", new String[]{cateId}, sortOrder);
                break;
        }
        return cursor;
    }

    /**
     * @param uri
     * @return
     * 1. must be start with 'vnd'
     * 2. if URI ends with dir, add 'android.cursor.dir/', else if ends of id, add 'android.cursor.item/'
     * 3. add vnd.<authority>.<path>
     */
    @Nullable
    @Override
    public String getType(Uri uri) {
        String type = null;
        switch (uriMatcher.match(uri)) {
            case NEWS_DIR:
                type = "vnd.android.cursor.dir/vnd.com.gln.codenum1.chapter7.table1";
                break;
            case NEWS_ITEM:
                type = "vnd.android.cursor.item/vnd.com.gln.codenum1.chapter7.table1";
                break;
            case CATEGORY_DIR:
                type = "vnd.android.cursor.dir/vnd.com.gln.codenum1.chapter7.table2";
                break;
            case CATEGORY_ITEM:
                type = "vnd.android.cursor.item/vnd.com.gln.codenum1.chapter7.table2";
                break;
        }
        return type;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        myDbHelper.insert();
        return uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        myDbHelper.delete();
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        myDbHelper.update();
        return 0;
    }
}
