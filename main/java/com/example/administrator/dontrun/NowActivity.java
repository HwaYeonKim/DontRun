package com.example.administrator.dontrun;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NowActivity extends Activity {

    Date today;
    String[] months = {"Jan.", "Feb.", "Mar.", "Api.", "May.", "Jun.", "Jul.", "Aug.", "Sep.", "Oct.", "Nov.", "Dec." };
    SharedPreferences pref;
    Context context;
    LinearLayout titlebar, menubtnLay;
    ImageView tab1, tab2, levelface, explain_card, menu_btn, alram_btn;
    TextView nowdecibeltv;
    Button testbtn;
    SeekBar seekbar;
    RecordDB recorddb;
    SimpleDateFormat timeformat, dateformat;
    String dateDB, dateAPI, time, alramflag;
    MenuDialog mdialog;
    DBHandler handler;
    SQLiteDatabase sql;
    private static final int SEND_THREAD_INFOMATION = 0;
    private static final int SEND_THREAD_STOP_MESSAGE = 1;

    public static SendMassgeHandler mMainHandler = null;
    int a = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_now);

        handler = new DBHandler(this);

        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.activity_titlebar);
        menubtnLay = (LinearLayout) findViewById(R.id.menubtn_lay);
        titlebar = (LinearLayout) getWindow().findViewById(R.id.titlebar_layout);
        menu_btn = (ImageView) findViewById(R.id.menu_btn);
        alram_btn = (ImageView) findViewById(R.id.alram_btn);

        mdialog = new MenuDialog(this);
        Window window = mdialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.LEFT | Gravity.TOP;

        alramflag = "on";

        window.setAttributes(wlp);

        menubtnLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("response", "onclick");
                mdialog.show();
            }
        });

        alram_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alramflag.equalsIgnoreCase("on")) {
                    alramflag = "off";
                    alram_btn.setBackgroundResource(R.drawable.alram_off);
                } else {
                    alramflag = "on";
                    alram_btn.setBackgroundResource(R.drawable.titlebar_alarm);
                }
            }
        });

        setTabAction();

        nowdecibeltv = (TextView) findViewById(R.id.now_decibel);
        levelface = (ImageView) findViewById(R.id.center_face_img);
        explain_card = (ImageView) findViewById(R.id.explain_card);
        recorddb = new RecordDB(getApplicationContext());

        timeformat = new SimpleDateFormat("HH:mm", Locale.KOREA);
        dateformat = new SimpleDateFormat("yyyy-MM-dd");

        mMainHandler = new SendMassgeHandler();

        setUI();
    }

    private void setUI() {
        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        nowdecibeltv.setText("" + pref.getInt("decibel", 0));
        switch (pref.getInt("level", 0)) {
            case 0:
            case 1:
                levelface.setBackgroundDrawable(getResources().getDrawable(R.drawable.face_level1));
                //levelface.setBackgroundResource(R.drawable.face_level1);
                explain_card.setBackgroundDrawable(getResources().getDrawable(R.drawable.card_level1));
                break;
            case 2:
            case 3:
                levelface.setBackgroundDrawable(getResources().getDrawable(R.drawable.face_level2));
                explain_card.setBackgroundDrawable(getResources().getDrawable(R.drawable.card_level2));
                break;
            case 4:
            case 5:
                levelface.setBackgroundDrawable(getResources().getDrawable(R.drawable.face_level3));
                explain_card.setBackgroundDrawable(getResources().getDrawable(R.drawable.card_level3));
                break;
            case 6:
            case 7:
                levelface.setBackgroundDrawable(getResources().getDrawable(R.drawable.face_level4));
                explain_card.setBackgroundDrawable(getResources().getDrawable(R.drawable.card_level4));
                break;
            case 8:
            case 9:
            case 10:
                levelface.setBackgroundDrawable(getResources().getDrawable(R.drawable.face_level5));
                explain_card.setBackgroundDrawable(getResources().getDrawable(R.drawable.card_level5));
                break;
        }
    }


    private void setTabAction() {

        tab1 = (ImageView) findViewById(R.id.tab1_btn1);
        tab1.setClickable(false);

        tab2 = (ImageView) findViewById(R.id.tab1_btn2);
        tab2.setClickable(true);
        tab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = null;
                i = new Intent(NowActivity.this, TodayActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });
    }

    // Handler 클래스
    class SendMassgeHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case SEND_THREAD_INFOMATION:
                    Log.d("response", "222Decibel = " + msg.arg1 + " level = " + msg.arg2);
                    SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();

                    editor.putInt("decibel", msg.arg1);
                    editor.putInt("level", msg.arg2);

                    editor.commit();
                    // TODO: 2015-12-24 단계 4,5일때 db에 데이터 저장

                    pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);

       //             handler.insert(pref.getString("dateDB", ""),pref.getString("time", ""),msg.arg1 );
        //          Log.d("response", "insert data");

                    if(msg.arg2 > 5 && msg.arg2 < 11){
                            handler.insert(pref.getString("dateDB", ""),pref.getString("time", ""),msg.arg1 );
                            Log.d("test", pref.getString("dateDB", "") + "/" + pref.getString("time", "") + "/" + msg.arg1  );
                            Log.d("response", "insert data");
                    }
                    // TODO: 2015-12-24 알람 등록, notification 띄우기
                    setUI();
                    break;
                default:
                    break;
            }
        }

    }
}
