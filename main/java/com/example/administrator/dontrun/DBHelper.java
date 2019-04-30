package com.example.administrator.dontrun;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Administrator on 2015-12-24.
 */
public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
            super(context, "recorddb", null, 1);
        Log.d("test", "DBHelper(Context context)");
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("test", "DBHelper(onCreate ");
        String sql = "CREATE TABLE rec (" + "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " + "rdate VERCHAR(255), " + "rtime VERCHAR(255), " + "rdecibel INTEGER);";
        Log.d("test", "create table " + sql);
        db.execSQL(sql) ;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS rec");// ���̺�����
        onCreate(db);// ������
    }
}
