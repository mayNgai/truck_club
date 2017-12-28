package com.dtc.sevice.truckclub.presenters;

import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;

import com.dtc.sevice.truckclub.R;
import com.dtc.sevice.truckclub.model.TblMember;
import com.dtc.sevice.truckclub.service.ApiService;
import com.dtc.sevice.truckclub.until.DialogController;
import com.dtc.sevice.truckclub.until.NetworkUtils;
import com.dtc.sevice.truckclub.until.TaskController;
import com.dtc.sevice.truckclub.view.LoginSecondActivity;
import com.dtc.sevice.truckclub.view.driver.activity.DriverMainActivity2;
import com.dtc.sevice.truckclub.view.driver.activity.DriverRegisterActivity;
import com.dtc.sevice.truckclub.view.user.activity.UserMainActivity2;
import com.dtc.sevice.truckclub.view.user.activity.UserRegisterActivity;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by admin on 9/20/2017 AD.
 */

public class LoginSecondPresenter {
    private ProgressDialog dialog;
    private ApiService mForum;
    private LoginSecondActivity mView;
    private DialogController dialogController;
    private TaskController taskController;

    public LoginSecondPresenter(LoginSecondActivity view, ApiService forum){
        mForum = forum;
        mView = view;
        dialogController = new DialogController();
        taskController = new TaskController();
    }

    public void loadLogin(){
        try {
            if(!NetworkUtils.isConnected(mView)){
                dialogController.dialogNolmal(mView,mView.getString(R.string.txtWarning),mView.getString(R.string.txt_internet_is_not));
            }else {
                dialog = ProgressDialog.show(mView, mView.getString(R.string.txtWait), mView.getString(R.string.txt_loading));
                mForum.getApi()
                        .getLogin(mView.tblMember)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<TblMember>() {
                            @Override
                            public void onCompleted() {
                                dialog.dismiss();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("userLoginCall Error", e.getMessage());
                                dialog.dismiss();
                            }

                            @Override
                            public void onNext(TblMember member) {
                                updateLogin(member);
                                dialog.dismiss();
                            }
                        });
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateLogin(TblMember members){
        try {
            if(members.getSuccess().equalsIgnoreCase("1")){
                taskController.createMember(members);
                if(members.getAuthority().equalsIgnoreCase(mView.getString(R.string.txtUser))) {
                    Intent i = new Intent(mView, UserMainActivity2.class);
                    mView.startActivity(i);
                    mView.finish();
                    Log.i("message", members.getMessage());
                }else if(members.getAuthority().equalsIgnoreCase(mView.getString(R.string.txtDriver))){
                    Intent i = new Intent(mView, DriverMainActivity2.class);
                    mView.startActivity(i);
                    mView.finish();
                    Log.i("message", members.getMessage());
                }

            }else if(members.getSuccess().equalsIgnoreCase("0")){
                if(mView.member_type == 0){
                    dialogController.dialogNolmal(mView,mView.getString(R.string.txtWarning),members.getMessage());
                    Log.i("message", members.getMessage());
                }else if(mView.member_type == 1){
                    if(mView.str_authen.equalsIgnoreCase(mView.getString(R.string.txtUser))){
                        Intent i = new Intent(mView, UserRegisterActivity.class);
                        i.putExtra("member_type", String.valueOf(mView.member_type));
                        i.putExtra("authen",mView.str_authen);
                        i.putExtra("id", mView.id);
                        i.putExtra("first_name",mView.first_name);
                        i.putExtra("last_name", mView.last_name);
                        i.putExtra("email",mView.email);
                        i.putExtra("gender", mView.gender);
                        i.putExtra("birthday",mView.birthday);
                        mView.startActivity(i);
                        mView.finish();
                    }else if(mView.str_authen.equalsIgnoreCase(mView.getString(R.string.txtDriver))){
                        Intent i = new Intent(mView, DriverRegisterActivity.class);
                        i.putExtra("member_type", String.valueOf(mView.member_type));
                        i.putExtra("authen",mView.str_authen);
                        i.putExtra("id", mView.id);
                        i.putExtra("first_name",mView.first_name);
                        i.putExtra("last_name", mView.last_name);
                        i.putExtra("email",mView.email);
                        i.putExtra("gender", mView.gender);
                        i.putExtra("birthday",mView.birthday);
                        mView.startActivity(i);
                        mView.finish();
                    }

                    Log.i("message", members.getMessage());
                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
