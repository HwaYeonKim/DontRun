package com.example.administrator.dontrun;

import java.util.Date;

/**
 * Created by Administrator on 2015-12-21.
 */
public class DataClass{
    private String accessToken =  "Bearer eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE0NTMwMzkzNDQsInNjb3BlcyI6Indwcm8gd2xvYyB3bnV0IHdzbGUgd3NldCB3aHIgd3dlaSB3YWN0IHdzb2MiLCJzdWIiOiIzVENGN0giLCJhdWQiOiIyMjlYNFEiLCJpc3MiOiJGaXRiaXQiLCJ0eXAiOiJhY2Nlc3NfdG9rZW4iLCJpYXQiOjE0NTA0NDczNDR9.3PBQ-AGIwxUJpjXmv5KS6nPoL46e-S3rx1_T1-xBDVI";
//    public String url_cal = "https://api.fitbit.com/1/user/3TCF7H/activities/calories/date/today/1d/1min/time/22:23/22:23.json";
//    public String url_step = "https://api.fitbit.com/1/user/3TCF7H/activities/steps/date/today/1d/1min/time/22:23/22:23.json";
//    public String url_distance = "https://api.fitbit.com/1/user/3TCF7H/activities/distance/date/today/1d/1min/time/22:23/22:23.json";
    private int nowDecibel, age;
    private double weight, height;// = 55.0;
    private String date, gender;

    public DataClass(){
        this.weight = 55.0;
    }

    public String getAccessToken(){
        return accessToken;
    }

    public void setNowDecibel(int decibel){
        nowDecibel = decibel;
    }

    public void setWeight(double weight){
        this.weight = weight;
    }

    public double getWeight(){
        return weight;
    }

    public String getDate(){
        Date today = new Date();
        String[] months = {"Jan.", "Feb.", "Mar.", "Api.", "May.", "Jun.", "Jul.", "Aug.", "Sep.", "Oct.", "Nov.", "Dec." };
        date = months[today.getMonth()]+today.getDay();
        return date;
    }

    public void setUserData(double w, double h, String g, int age){
        this.weight = w;
        this.height = h;
        this.gender = g;
        this.age = age;
    }


}
