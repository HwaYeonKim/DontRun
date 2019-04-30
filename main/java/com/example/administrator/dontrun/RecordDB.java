package com.example.administrator.dontrun;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015-12-22.
 */
public class RecordDB extends SQLiteOpenHelper {
    private static int DATABASE_VERSION = 1;
    public static String DBNAME = "Record_DB";
    public static String TABLENAME = "record_table";
    public static String DATE = "date";
    public static String TIME = "time";
    public static String DECIBEL = "decibel";

    public RecordDB(Context context) {
        super(context, DBNAME, null, DATABASE_VERSION);
    }

    private static final String CREATE_AUTHOR_TABLE =
            "CREATE TABLE record_table (" +
                    "record_index INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + //id
                    "date VERCHAR(255)," +
                    "time VERCHAR(255)," +
                    "decibel INTEGER);";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_AUTHOR_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS time_table");
        onCreate(db);
    }

    public void addRecord(String date, String time, int decibel){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(DATE, date);
        values.put(TIME, time);
        values.put(DECIBEL, decibel);
        db.insert(TABLENAME, null, values);
        db.close();
    }

    public ArrayList<ListData> selectTodayData(String date){
        ArrayList<ListData> dataObjs = new ArrayList<ListData>();
        String selectQuery= "SELECT * FROM record_table WHERE date ='"+date+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery , null);
        if(cursor.moveToFirst())
        {
            do{
                ListData obj = new ListData();
                obj.date =cursor.getString(0);
                obj.time=cursor.getString(1);
                obj.decibel=cursor.getInt(2);
                dataObjs.add(obj);
            }while(cursor.moveToNext());
        }
        db.close();

        return dataObjs;
    }
}
