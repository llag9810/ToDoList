package com.zhu.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DbHelper extends SQLiteOpenHelper {
    private static DbHelper dbHelper = null;

    public static final String dbName = "data.db";

    public DbHelper(Context context) {
        super(context, dbName, null, 1);
    }

    public static SQLiteDatabase getInstance(Context context) {
        if (dbHelper == null) {
            dbHelper = new DbHelper(context);
        }
        return dbHelper.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String createSql = ("CREATE TABLE IF NOT EXISTS eventData" +
                    "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "title TEXT, content TEXT, " +
                    "createDate TEXT, deadline TEXT, priority INTEGER, isFinished INTEGER)");
            db.execSQL(createSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addData(String title, String content, String createDate, String deadline,
                               int priority, int isFinished) {
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("content", content);
        values.put("createDate", createDate);
        values.put("deadline", deadline);
        values.put("priority", priority);
        values.put("isFinished", isFinished);
        DbHelper.getInstance(null).replace("eventData", "title", values);
    }

    public void updateData(int id, String title, String content, String createDate, String deadline,
                           int priority, int isFinished) {
        ContentValues values = new ContentValues();
        values.put("_id", id);
        values.put("title", title);
        values.put("content", content);
        values.put("createDate", createDate);
        values.put("deadline", deadline);
        values.put("priority", priority);
        values.put("isFinished", isFinished);
        DbHelper.getInstance(null).replace("eventData", "title", values);
    }

/*    public void getAllData(Context context, ArrayList<HashMap<String, Object>> list, String table) {
        Cursor cursor = DbHelper.getInstance(context).rawQuery("select * from " + table, null);
        while(cursor.moveToNext()) {
            HashMap<String, Object> map = new HashMap<>();
            for(int i = 0; i < cursor.getColumnCount(); i++) {
                map.put("_id", cursor.getString(0));
                map.put("title", cursor.getString(1));
                map.put("content", cursor.getString(2));
                map.put("createDate", cursor.getString(3));
                map.put("deadline", cursor.getString(4));
                map.put("priority", cursor.getString(5));
                map.put("isFinished", cursor.getString(6));
            }
            list.add(map);
        }
        cursor.close();
    }*/

}