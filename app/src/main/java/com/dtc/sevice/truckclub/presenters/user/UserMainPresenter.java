package com.dtc.sevice.truckclub.presenters.user;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

import com.dtc.sevice.truckclub.R;
import com.dtc.sevice.truckclub.model.TblMember;
import com.dtc.sevice.truckclub.model.TblTask;
import com.dtc.sevice.truckclub.service.ApiService;
import com.dtc.sevice.truckclub.until.ApplicationController;
import com.dtc.sevice.truckclub.until.DialogController;
import com.dtc.sevice.truckclub.until.NetworkUtils;
import com.dtc.sevice.truckclub.until.TaskController;
import com.dtc.sevice.truckclub.view.user.activity.UserMainActivity2;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by May on 9/28/2017.
 */

public class UserMainPresenter {
    private ProgressDialog dialog;
    private ApiService mForum;
    private DialogController dialogController;
    private UserMainActivity2 mView;
    private Activity activity;
    private TaskController taskController;
    public UserMainPresenter(UserMainActivity2 view , ApiService forum){
        mForum = forum;
        mView = view;
        dialogController = new DialogController();
        taskController = new TaskController();
    }

    public void loadDriverInScope(){
        try {
            //dialog = ProgressDialog.show(mView, "Wait", "loading...");
            mForum.getApi().getDriverInScope(mView.members.get(0))
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<TblMember>>() {
                        @Override
                        public void onCompleted() {
                            //dialog.dismiss();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("loadDriverInScope Error", e.getMessage());
                            //dialog.dismiss();
                        }

                        @Override
                        public void onNext(List<TblMember> member) {

                            mView.updateMarkerDriverInScope(member);
                            //dialog.dismiss();
                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void sentCreateTask(){
        try {
            if(!NetworkUtils.isConnected(mView)){
                dialogController.dialogNolmal(mView,mView.getString(R.string.txtWarning),mView.getString(R.string.txt_internet_is_not));
            }else {
                dialog = ProgressDialog.show(mView, mView.getString(R.string.txtWait), mView.getString(R.string.txt_loading));
                mForum.getApi()
                        .sentCreateTask(mView.tblTask)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<TblTask>() {
                            @Override
                            public void onCompleted() {
                                dialog.dismiss();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("sentCreateTask Error", e.getMessage());
                                dialog.dismiss();
                            }

                            @Override
                            public void onNext(TblTask tblTask) {
                                mView.setDefault();
                                dialog.dismiss();
                                Log.i("sentCreateTask", "Ok");
                                if(tblTask.getService_type().equalsIgnoreCase("Now")){
                                    List<TblTask> tblTasks = new ArrayList<TblTask>();
                                    tblTasks.add(tblTask);
                                    taskController.createTask(tblTasks);
                                    List<TblTask> t = taskController.getTaskByID(tblTask.getId());
                                    mView.setCountDownTime(tblTask);
                                }

                            }
                        });
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void loadCreateTask(){
        try {
            if(!NetworkUtils.isConnected(mView)){
                dialogController.dialogNolmal(mView,mView.getString(R.string.txtWarning),mView.getString(R.string.txt_internet_is_not));
            }else {
                //dialog = ProgressDialog.show(mView, mView.getString(R.string.txtWait), mView.getString(R.string.txt_loading));
                mForum.getApi()
                        .getTaskByID(mView.tblTask)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<TblTask>() {
                            @Override
                            public void onCompleted() {
                               // dialog.dismiss();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("loadCreateTask Error", e.getMessage());
                               // dialog.dismiss();
                            }

                            @Override
                            public void onNext(TblTask tblTask) {
                                //mView.setDefault();
                                //dialog.dismiss();
                                Log.i("loadCreateTask", "Ok");
                                if(tblTask.getService_type().equalsIgnoreCase("Now")){
                                    mView.setCountDownTime(tblTask);
                                }

                            }
                        });
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateMain(TblTask tblTask,final int status_id){
        try {

                mForum.getApi()
                        .sentUpdateTask(tblTask)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<TblTask>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("updateMain Error", e.getMessage());
                            }

                            @Override
                            public void onNext(TblTask tblTasks) {
                                //mView.updateTask(tblTasks);
                                Log.i("updateMain", "OK");
                                if(status_id == 3){
                                    Activity activity = ApplicationController.getAppActivity();
                                    Intent intent = new Intent(activity,UserMainActivity2.class);
                                    activity.startActivity(intent);
                                    activity.finish();
                                }

                            }
                        });
//            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
