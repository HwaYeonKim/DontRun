package com.example.administrator.dontrun;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2015-12-22.
 */
public class Splash extends ActionBarActivity {


    private RequestThread mRequestThread = null;
    SimpleDateFormat timeformat, dateformat;
    String dateDB, dateAPI,time, alramflag;
    Date today;
    String[] months = {"Jan.", "Feb.", "Mar.", "Api.", "May.", "Jun.", "Jul.", "Aug.", "Sep.", "Oct.", "Nov.", "Dec." };
    NowRequAsyncTask now_request;
    NowDecibel decibleObj;
    DBHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

   //     helper = new DBHelper(getApplicationContext());

        decibleObj = new NowDecibel();

        Handler handler = new Handler();
        handler.postDelayed(new splashhandler(), 2000);

        timeformat = new SimpleDateFormat("HH:mm", Locale.KOREA);
        dateformat = new SimpleDateFormat("yyyy-MM-dd");

        now_request = new NowRequAsyncTask();

        mRequestThread = new RequestThread();
        //// TODO: 2015-12-24 Thread start
        mRequestThread.start();
  //      Toast.makeText(getApplicationContext(), "request", Toast.LENGTH_SHORT).show();

    }

    private class splashhandler implements Runnable{

        @Override
        public void run() {
            startActivity(new Intent(getApplication(), NowActivity.class));
            Splash.this.finish();
        }
    }

    // Thread 클래스
    class RequestThread extends Thread implements Runnable {

        @Override
        public void run() {
            super.run();

            while (true) {
                today = new Date();
                dateDB = months[today.getMonth()]+today.getDate();

                Calendar cal = Calendar.getInstance();

                cal.setTime(today);
                cal.add(Calendar.MINUTE, -1);
                dateAPI = dateformat.format(cal.getTime());
                time = timeformat.format(cal.getTime());
                Log.d("response", "dbdate : " + dateDB + " apidate : " + dateAPI + " time : " + time);
                SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();

                editor.putString("dateDB", dateDB);
                editor.putString("time", time);

                editor.commit();



                now_request.executeRequest(time, dateAPI);
                try {
                    Thread.sleep(60000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
