package com.dtc.sevice.truckclub.presenters.user;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;

import com.dtc.sevice.truckclub.R;
import com.dtc.sevice.truckclub.model.TblTask;
import com.dtc.sevice.truckclub.service.ApiService;
import com.dtc.sevice.truckclub.until.ApplicationController;
import com.dtc.sevice.truckclub.until.DialogController;
import com.dtc.sevice.truckclub.until.NetworkUtils;
import com.dtc.sevice.truckclub.view.user.activity.UserMainActivity2;
import com.dtc.sevice.truckclub.view.user.activity.UserTaskActivity;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by May on 10/25/2017.
 */

public class UserTaskPresenter {
    private ProgressDialog dialog;
    private ApiService mForum;
    private UserTaskActivity mView;
    private DialogController dialogController;
    private Activity activity;

    public UserTaskPresenter(UserTaskActivity view,ApiService forum){
        mForum = forum;
        mView = view;
        dialogController = new DialogController();
    }

    public void sendFinish(){
        try {
            if(!NetworkUtils.isConnected(mView)){
                dialogController.dialogNolmal(mView,mView.getString(R.string.txtWarning),mView.getString(R.string.txt_internet_is_not));
            }else {
                dialog = ProgressDialog.show(mView, "Wait", "loading...");
                mForum.getApi()
                        .sentUpdateTask(mView.tblTask)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<TblTask>() {
                            @Override
                            public void onCompleted() {
                                dialog.dismiss();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("sendFinish Error", e.getMessage());
                                dialog.dismiss();
                            }

                            @Override
                            public void onNext(TblTask tblTasks) {
                                //mView.updateTask(tblTasks);
                                Log.i("sendFinish", "OK");
                                dialog.dismiss();
                                activity = ApplicationController.getAppActivity();
                                Intent intent = new Intent(activity, UserMainActivity2.class);
                                activity.startActivity(intent);
                                activity.finish();

                            }
                        });
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
