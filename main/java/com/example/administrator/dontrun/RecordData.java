package com.example.administrator.dontrun;

/**
 * Created by Administrator on 2015-12-24.
 */
public class RecordData {
    int id;
    String date;
    String time;
    int decibel;

    public RecordData(){ };
    public RecordData(String date, String time, int decibel){
        super();
        this.date = date;
        this.time = time;
        this.decibel = decibel;
    }

    public String getDate(){
        return date;
    }
    public String getTime(){
        return time;
    }
    public int getDecibel(){
        return decibel;
    }
}
