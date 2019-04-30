package com.example.administrator.dontrun;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by Administrator on 2015-12-24.
 */
public class UserSetActivity extends Activity {

    EditText height_ed, weight_ed, age_ed;
    ImageView gender_img, start_btn;
    String gender;
    Double height, weight;
    int age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);

        gender = "m";

        height_ed = (EditText)findViewById(R.id.height_ed);
        weight_ed = (EditText)findViewById(R.id.weight_ed);
        age_ed = (EditText) findViewById(R.id.age_ed);
        gender_img = (ImageView) findViewById(R.id.gender_toggle);
        start_btn = (ImageView) findViewById(R.id.start_btn);

        gender_img.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(gender.equalsIgnoreCase("m")){
                    gender = "w";
                    gender_img.setBackgroundResource(R.drawable.women);
                }else{
                    gender = "m";
                    gender_img.setBackgroundResource(R.drawable.men);
                }

                return false;
            }
        });

       start_btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               height = Double.parseDouble(height_ed.getText().toString());
               weight = Double.parseDouble(weight_ed.getText().toString());
               age = Integer.parseInt(age_ed.getText().toString());

                new DataClass().setUserData(weight, height, gender, age);
//               Intent intent = new Intent(UserSetActivity.this, NowActivity.class);
//               startActivity(intent);
               finish();
           }
       });

    }
}
