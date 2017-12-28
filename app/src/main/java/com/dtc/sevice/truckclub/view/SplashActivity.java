package com.dtc.sevice.truckclub.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.dtc.sevice.truckclub.R;
import com.dtc.sevice.truckclub.helper.DatabaseHelper;
import com.dtc.sevice.truckclub.helper.GlobalVar;
import com.dtc.sevice.truckclub.model.TblMember;
import com.dtc.sevice.truckclub.until.ApplicationController;
import com.dtc.sevice.truckclub.until.TaskController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 9/20/2017 AD.
 */

public class SplashActivity extends AppCompatActivity {
    private Handler handler;
    private TextView textView;
    private long startTime, currentTime, finishedTime = 0L;
    private int duration = 10000 / 4;
    private int endTime = 0;
    private Activity _activity;
    private DatabaseHelper databaseHelper = null;
    private TaskController taskController;
    private List<TblMember> members;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        textView = (TextView) findViewById(R.id.textView);
        textView.setText(R.string.app_name);
        _activity = SplashActivity.this;
        taskController = new TaskController();
        ApplicationController.setAppActivity(_activity);
        databaseHelper = DatabaseHelper.getHelper(_activity);
        handler = new Handler();
        startTime = Long.valueOf(System.currentTimeMillis());
        currentTime = startTime;

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                currentTime = Long.valueOf(System.currentTimeMillis());
                finishedTime = Long.valueOf(currentTime)
                        - Long.valueOf(startTime);

                if (finishedTime >= duration + 30) {
                    members = new ArrayList<TblMember>();
                    members = taskController.getMember();
                    if(members.size()>0){
                        Intent i = new Intent(SplashActivity.this, LoginSecondActivity.class);
                        i.putExtra("authen" , members.get(0).getAuthority());
                        i.putExtra("member_type" , members.get(0).getMember_type());
                        i.putExtra("face_book_id" , members.get(0).getFace_book_id());
                        i.putExtra("user_name" , members.get(0).getUser_name());
                        i.putExtra("password" , members.get(0).getPassword());
                        startActivity(i);
                        finish();
                    }else {
                        Intent intent = new Intent(SplashActivity.this, LoginFirstActivity.class);
                        startActivity(intent);
                        finish();
                    }

                } else {
                    endTime = (int) (finishedTime / 250);
                    Spannable spannableString = new SpannableString(textView
                            .getText());
                    spannableString.setSpan(new ForegroundColorSpan(
                                    Color.BLACK), 0, endTime,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    textView.setText(spannableString);
                    handler.postDelayed(this, 10);
                }
            }
        }, 10);
    }
}
