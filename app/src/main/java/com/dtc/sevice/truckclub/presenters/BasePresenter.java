package com.dtc.sevice.truckclub.presenters;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dtc.sevice.truckclub.R;
import com.dtc.sevice.truckclub.model.TblMember;
import com.dtc.sevice.truckclub.model.TblTask;
import com.dtc.sevice.truckclub.service.ApiService;
import com.dtc.sevice.truckclub.until.ApplicationController;
import com.dtc.sevice.truckclub.until.DateController;
import com.dtc.sevice.truckclub.until.DialogController;
import com.dtc.sevice.truckclub.until.NetworkUtils;
import com.dtc.sevice.truckclub.until.TaskController;
import com.dtc.sevice.truckclub.view.BaseActivity;
import com.dtc.sevice.truckclub.view.LoginFirstActivity;
import com.dtc.sevice.truckclub.view.driver.activity.DriverBookingActivity;
import com.dtc.sevice.truckclub.view.user.activity.UserMainActivity2;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by may on 10/4/2017.
 */

public class BasePresenter {
    private ApiService mForum;
    private BaseActivity mView;
    private TaskController taskController;
    private DialogController dialogController;
    private DateController dateController;
    private Activity _activity;
    private static int task_id = 0 ;

    public BasePresenter(BaseActivity view,ApiService forum){
        mForum = forum;
        mView = view;
        taskController = new TaskController();
        dialogController = new DialogController();
        dateController = new DateController();
    }

    public void updateStatus(){
        try {
            if(!NetworkUtils.isConnected(mView)){
                dialogController.dialogNolmal(mView,mView.getString(R.string.txtWarning),mView.getString(R.string.txt_internet_is_not));
            }else {
                mForum.getApi()
                        .updateStatusMember(mView.listMembers.get(0))
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<TblMember>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("updateStatus Error", e.getMessage());
                            }

                            @Override
                            public void onNext(TblMember member) {
                                //updateCarGroup(groups);
                            }
                        });
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void logOut(){
        try {
            mForum.getApi()
                    .logOut(mView.listMembers.get(0))
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<TblMember>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("logOut Error", e.getMessage());
                        }

                        @Override
                        public void onNext(TblMember member) {
                            updateLogOut(member);
                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateLogOut(TblMember member){
        try {
            if(member.getSuccess().equalsIgnoreCase("1")){
                List<TblMember> listMembers = new ArrayList<TblMember>();
                member.setGuid(mView.listMembers.get(0).getGuid());
                listMembers.add(member);
                taskController.deleteMember(listMembers);
                taskController.deleteAllTask();
                Intent i = new Intent(mView,LoginFirstActivity.class);
                mView.startActivity(i);
                mView.finish();
            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void loadDataMember(){
        try {
            if(!NetworkUtils.isConnected(mView)){
                dialogController.dialogNolmal(mView,mView.getString(R.string.txtWarning),mView.getString(R.string.txt_internet_is_not));
            }else {
                //dialog = ProgressDialog.show(mView, mView.getString(R.string.txtWait), mView.getString(R.string.txt_loading));
                mForum.getApi()
                        .getDataMember(mView.listMembers.get(0))
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<TblMember>() {
                            @Override
                            public void onCompleted() {
                               // dialog.dismiss();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("userLoginCall Error", e.getMessage());
                                //dialog.dismiss();
                            }

                            @Override
                            public void onNext(TblMember member) {
                                taskController.createMember(member);
                                mView.getMember();
                                //dialog.dismiss();
                            }
                        });
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void loadTask(){
        try {
            task_id = mView.tblTask.getTask_status();
            if(!NetworkUtils.isConnected(mView)){
                dialogController.dialogNolmal(mView,mView.getString(R.string.txtWarning),mView.getString(R.string.txt_internet_is_not));
            }else {
                mForum.getApi()
                        .getTask(mView.tblTask)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<List<TblTask>>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("loagTask Error", e.getMessage());
                            }

                            @Override
                            public void onNext(List<TblTask> tblTasks) {
                                if(task_id>2){
                                    mView.updateTaskBooking(tblTasks);
                                }else {
                                    mView.updateTaskWait(tblTasks);
                                }
                            }
                        });
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void loadTaskByID(){
        try {
            if(!NetworkUtils.isConnected(mView)){
                dialogController.dialogNolmal(mView,mView.getString(R.string.txtWarning),mView.getString(R.string.txt_internet_is_not));
            }else {
                mForum.getApi()
                        .getTaskByID(mView.tblTask)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<TblTask>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("loadTaskByID Error", e.getMessage());
                            }

                            @Override
                            public void onNext(TblTask tblTasks) {
                                setDialogBottom(tblTasks);
                                Log.i("loadTaskByID","Ok");

                            }
                        });
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void callUpdateTask(TblTask tblTask){
        try {
//            if(!NetworkUtils.isConnected(mView)){
//                dialogController.dialogNolmal(mView,mView.getString(R.string.txtWarning),mView.getString(R.string.txt_internet_is_not));
//            }else {
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
                                Log.e("callWaitAccept Error", e.getMessage());
                            }

                            @Override
                            public void onNext(TblTask tblTasks) {
                                _activity = ApplicationController.getAppActivity();
                                Intent i = new Intent(_activity, UserMainActivity2.class);
                                _activity.startActivity(i);
                                _activity.finish();

                            }
                        });
//            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static Dialog mBottomSheetDialog;
    private void setDialogBottom(TblTask tblTasks){
        try {
            LayoutInflater inflater = (LayoutInflater)mView.getSystemService(mView.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate (R.layout.dialog_driver_offer, null);
            ImageView img_cancel = (ImageView)view.findViewById( R.id.img_cancel);
            TextView txt_id = (TextView)view.findViewById( R.id.txt_id);
            TextView txt_start_date = (TextView)view.findViewById( R.id.txt_start_date);
            TextView txt_end_date = (TextView)view.findViewById( R.id.txt_end_date);
            TextView txt_start_time = (TextView)view.findViewById( R.id.txt_start_time);
            TextView txt_end_time = (TextView)view.findViewById( R.id.txt_end_time);
            TextView txt_name = (TextView)view.findViewById( R.id.txt_driver_name);
            TextView txt_surname = (TextView)view.findViewById( R.id.txt_driver_surname);
            TextView txt_start_position = (TextView)view.findViewById( R.id.txt_start_position);
            TextView txt_des_position = (TextView)view.findViewById( R.id.txt_des_position);
            TextView txt_type_car = (TextView)view.findViewById( R.id.txt_type_car);
            final EditText edt_price = (EditText)view.findViewById( R.id.edt_price);
            ImageButton img_ok = (ImageButton)view.findViewById( R.id.img_ok);
            Button btn_travel = (Button)view.findViewById( R.id.btn_travel);
            RatingBar rating = (RatingBar)view.findViewById( R.id.rating);
            edt_price.setEnabled(false);
            mBottomSheetDialog = new Dialog (mView,R.style.MaterialDialogSheet);
            mBottomSheetDialog.setContentView (view);
            mBottomSheetDialog.setCancelable (true);
            mBottomSheetDialog.getWindow ().setLayout (LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            mBottomSheetDialog.getWindow ().setGravity (Gravity.BOTTOM);
            mBottomSheetDialog.show ();
            txt_id.setText(tblTasks.getId());
            txt_start_date.setText(dateController.convertDateFormat2To1(tblTasks.getStart_date()));
            txt_end_date.setText(dateController.convertDateFormat2To1(tblTasks.getEnd_date()));
            txt_start_time.setText(tblTasks.getEnd_date().substring(10,16));
            txt_end_time.setText(tblTasks.getEnd_date().substring(10,16));
            txt_name.setText(tblTasks.getMember().get(0).getFirst_name());
            txt_surname.setText(tblTasks.getMember().get(0).getLast_name());
            txt_start_position.setText(tblTasks.getStart_location());
            txt_des_position.setText(tblTasks.getDest_location());
            txt_type_car.setText(tblTasks.getName_group());
            edt_price.setText(tblTasks.getPrice());
            img_ok.setVisibility(View.GONE);
            btn_travel.setVisibility(View.GONE);

            img_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBottomSheetDialog.dismiss();
                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
