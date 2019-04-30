package com.example.administrator.dontrun;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015-12-24.
 */
public class DBHandler {
    private Context ctx;
    private DBHelper helper;
    private SQLiteDatabase db;

    public DBHandler(Context ctx){
        this.ctx = ctx;
        helper = new DBHelper(ctx);
        db = helper.getWritableDatabase();
        Log.d("test", "DBHandler(Context ctx)");
    }

    public static DBHandler open(Context ctx) throws SQLException {
        DBHandler handler = new DBHandler(ctx);
        return handler;
    }

    public void close() {
        helper.close();
    }

    public void insert(String date, String time, int decibel) {
        Log.d("test", "insert");
        String sql = "insert into rec (rdate,rtime,rdecibel) VALUES ('" + date
                + "','" + time + "'," + decibel + ");";
        Log.d("test","insert : " + sql);
        db.execSQL(sql);
    }

    // SELECT WHERE DATE
    public ArrayList<RecordData> selectDate(String today) {
        db = helper.getReadableDatabase();
        Cursor cursor;
        //// TODO: 2015-12-24  table명 바꾸기
        String str = "SELECT * FROM rec WHERE rdate = '"+ today+"'";
   //     Log.d("AAA", str);
        cursor = db.rawQuery(str, null); //������!

        ArrayList<RecordData> data = new ArrayList<RecordData>();
        int id;
        String date;
        String time;
        int decibel;

        while (cursor.moveToNext()) {
            id = cursor.getInt(0);
            date = cursor.getString(1);
            time = cursor.getString(2);
            decibel = cursor.getInt(3);
            data.add(new RecordData(date, time, decibel));
        }
        return data;
    }
}
