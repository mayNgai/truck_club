package com.dtc.sevice.truckclub.broadcasts;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.dtc.sevice.truckclub.model.TblMember;
import com.dtc.sevice.truckclub.model.TblTask;
import com.dtc.sevice.truckclub.until.DateController;
import com.dtc.sevice.truckclub.view.driver.activity.DriverMainActivity2;
import com.dtc.sevice.truckclub.view.user.activity.UserMainActivity2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by May on 10/30/2017.
 */

public class DriverBackgroundService extends Service {
    public static CountDownTimer countDownTimer;
    private static DriverMainActivity2 mView;
    private static TblTask tblTask2;
    private static long time_count;
    private static int time_wait;
    private DateController dateController;

    final String PREF_NAME = "user";
    final String KEY_MEMBER_ID = "member_id";
    final String KEY_TASK_ID = "task_id";
    final String KEY_TIME_END = "time_end";
    final String KEY_TIME_OUT = "time_out";
    final String KEY_DATE_CREATE = "date_create";
    final String KEY_TIME_COUNT = "time_count";
    final String KEY_TIME_WAIT = "time_wait";
    final String KEY_DESTROY = "destroy_app";

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Override
    public void onCreate() {
        super.onCreate();
        mView = new DriverMainActivity2();
        dateController = new DateController();
        tblTask2 = new TblTask();
        sp = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();
        time_wait = sp.getInt(KEY_TIME_WAIT,0);
        Log.i("onCreate",String.valueOf(time_count));
        long current = dateController.dateTimeFormat9Tolong(dateController.getSystemTimeFull(getApplicationContext()));
        time_count = dateController.dateTimeFormat9Tolong(sp.getString(KEY_DATE_CREATE,"") ) + sp.getInt(KEY_TIME_WAIT,0) * 60000;
        if(time_count > current){
            time_count = time_count - current;
            countDownTimer = new CountDownTimer(time_count, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    try {
                        if(mView.txt_wait_time != null){
                            tblTask2 = mView.tblTask2;

                            mView.txt_wait_time.setText(mView.hmsTimeFormatter(millisUntilFinished));
                            mView.progressBarCircle.setProgress(60 - (int) ((time_count - millisUntilFinished)/ (1000 * time_wait)));
                            Log.i("onTick","test");
                        }

                        Log.i("wait_time",mView.hmsTimeFormatter(millisUntilFinished));
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFinish() {
                    Log.i("wait_time","Time out...");
//                    editor.putBoolean(KEY_TIME_OUT,true);
//                    editor.commit();
//                    TblMember tblMember = new TblMember();
//                    tblMember.setMember_id(sp.getInt(KEY_MEMBER_ID,0));
//                    List<TblMember> tblMembers = new ArrayList<TblMember>();
//                    tblMembers.add(tblMember);
//                    TblTask tblTask = new TblTask();
//                    tblTask.setId(sp.getString(KEY_TASK_ID,""));
//                    tblTask.setTask_status(2);
//                    tblTask.setService_type("Now");
//                    tblTask.setMember(tblMembers);
//                    mView.callUpdateTask(tblTask,2);
//                    Intent i = new Intent(UserBackgroundService.this, UserMainActivity2.class);
//                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(i);
                }

            }.start();
            countDownTimer.start();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStart(intent, startId);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("onBind","onBind");
        return null;
    }
}
