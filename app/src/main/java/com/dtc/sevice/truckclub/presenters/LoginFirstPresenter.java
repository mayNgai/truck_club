package com.dtc.sevice.truckclub.presenters;

import android.util.Log;

import com.dtc.sevice.truckclub.model.TblCarGroup;
import com.dtc.sevice.truckclub.model.TblMember;
import com.dtc.sevice.truckclub.service.ApiService;
import com.dtc.sevice.truckclub.until.TaskController;
import com.dtc.sevice.truckclub.view.LoginFirstActivity;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by May on 10/4/2017.
 */

public class LoginFirstPresenter {
    private TaskController taskController;
    private ApiService mForum;
    private LoginFirstActivity mView;
    public LoginFirstPresenter(LoginFirstActivity view,ApiService forum){
        mForum = forum;
        mView = view;
        taskController = new TaskController();
    }

    public void loadCarGroup(){
        try {
            mForum.getApi()
                    .getCarGroup()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<TblCarGroup>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("loadCarGroup Error", e.getMessage());
                        }

                        @Override
                        public void onNext(List<TblCarGroup> groups) {
                            updateCarGroup(groups);
                            Log.i("loadCarGroup","OK");
                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateCarGroup(List<TblCarGroup> groups){
        try {
            taskController.createCarGroup(groups);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
