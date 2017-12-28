package com.dtc.sevice.truckclub.view.driver.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.DigitsKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.dtc.sevice.truckclub.R;
import com.dtc.sevice.truckclub.helper.GlobalVar;
import com.dtc.sevice.truckclub.model.TblMember;
import com.dtc.sevice.truckclub.service.ApiService;
import com.dtc.sevice.truckclub.until.DateController;
import com.dtc.sevice.truckclub.until.DialogController;
import com.dtc.sevice.truckclub.until.TaskController;
import com.dtc.sevice.truckclub.view.LoginSecondActivity;
import com.dtc.sevice.truckclub.view.driver.activity.DriverRegisterActivity;
import com.facebook.AccessToken;
import com.squareup.picasso.Picasso;

/**
 * Created by May on 9/21/2017.
 */

public class RegisterDriverFragmentFirst extends Fragment implements View.OnClickListener{
    public static EditText edt_user_name,edt_first_name , edt_last_name ,edt_email,edt_tel,edt_password,edt_confirm_pass;
    public static TextView txt_date_of_birth;
    private Spinner spn_sex;
    private LinearLayout layPassword;
    private Toolbar toolbar;
    private View rootView;
    private String[] arrSex;
    private DateController dateController;
    private DialogController dialogController;
    private TaskController taskController;
    public boolean flagSex = false;
    public static TblMember tblMember;
    private static DriverRegisterActivity mView;
    private static int member_type = 0;
    private static String face_book_id="";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_register_driver_first, container, false);
        init();
        setDataText();
        setSpinnerSex();
        return rootView;
    }

    private void init(){
        try {
            dateController = new DateController();
            dialogController = new DialogController();
            taskController = new TaskController();
            mView = new DriverRegisterActivity();
            edt_user_name = (EditText)rootView.findViewById(R.id.edt_user_name);
            edt_first_name =(EditText)rootView.findViewById(R.id.edt_first_name);
            edt_last_name =(EditText)rootView.findViewById(R.id.edt_last_name);
            edt_email =(EditText)rootView.findViewById(R.id.edt_email);
            edt_tel =(EditText)rootView.findViewById(R.id.edt_tel);
            edt_password =(EditText)rootView.findViewById(R.id.edt_password);
            edt_confirm_pass =(EditText)rootView.findViewById(R.id.edt_confirm_pass);
            txt_date_of_birth = (TextView)rootView.findViewById(R.id.txt_date_of_birth);
            spn_sex = (Spinner)rootView.findViewById(R.id.spn_sex);
            layPassword = (LinearLayout)rootView.findViewById(R.id.layPassword);
            toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
            toolbar.setTitle(R.string.txtRegister);
            toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);
            txt_date_of_birth.setOnClickListener(this);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //getActivity().onBackPressed();
                    AccessToken.setCurrentAccessToken(null);
                    Intent i = new Intent(getActivity(), LoginSecondActivity.class);
                    i.putExtra("authen","Driver");
                    getActivity().startActivity(i);
                    getActivity().finish();
                }
            });

            //edt_user_name.setKeyListener(DigitsKeyListener.getInstance("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setDataText(){
        try {
            if(mView.member != null){
                tblMember = new TblMember();
                tblMember = mView.member;
                if(tblMember.getMember_type()==1){
                    member_type = 1;
                    face_book_id = tblMember.getFace_book_id();
                    edt_first_name.setText(tblMember.getFirst_name());
                    edt_last_name.setText(tblMember.getLast_name());
                    edt_email.setText(tblMember.getEmail());
                    edt_tel.setText(tblMember.getTel());
                    txt_date_of_birth.setText(((tblMember.getBirth_date() == null) || (tblMember.getBirth_date().length()==0) ? "" : dateController.convertDateFormat7To1(tblMember.getBirth_date())));
                    layPassword.setVisibility(View.GONE);
                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void setSpinnerSex(){
        try {
            arrSex = getResources().getStringArray(R.array.sex);
            ArrayAdapter<String> sexArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, arrSex);
            spn_sex.setAdapter(sexArrayAdapter);
            spn_sex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if(i==0){
                        flagSex = false;
                    }else if(i==1){
                        flagSex = true;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setDateBirthDay() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {

                String aa = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                txt_date_of_birth.setText(dateController.convertDateFormat2To1(aa));
                txt_date_of_birth.setTextColor(Color.BLACK);
            }
        }, GlobalVar.mYear, GlobalVar.mMonth, GlobalVar.mDay);
        datePickerDialog.show();
    }

    public boolean setCompletePageFirst(Activity activity){
        boolean success = false;
        try {
            boolean cancel = false;
            View focusView = null;
            if(member_type == 0){
                if(edt_user_name.getText().toString().trim().length()==0){
                    edt_user_name.setError(activity.getResources().getString(R.string.error_invalid_useer_name));
                    focusView = edt_user_name;
                    cancel = true;
                }else if(edt_first_name.getText().toString().trim().length()==0){
                    edt_first_name.setError(activity.getResources().getString(R.string.error_invalid_first_name));
                    focusView = edt_first_name;
                    cancel = true;
                }else if(edt_last_name.getText().toString().trim().length()==0){
                    edt_last_name.setError(activity.getResources().getString(R.string.error_invalid_last_name));
                    focusView = edt_last_name;
                    cancel = true;
                }else if(!GlobalVar.isEmailValid(edt_email.getText().toString().trim())){
                    edt_email.setError(activity.getResources().getString(R.string.error_invalid_email));
                    focusView = edt_email;
                    cancel = true;
                }else if(!GlobalVar.isPhoneNumberValid(edt_tel.getText().toString().trim())){
                    edt_tel.setError(activity.getResources().getString(R.string.error_invalid_tel));
                    focusView = edt_tel;
                    cancel = true;
                }else if(!GlobalVar.isPassWordValid(edt_password.getText().toString().trim())){
                    edt_password.setError(activity.getResources().getString(R.string.error_invalid_pass));
                    focusView = edt_password;
                    cancel = true;
                }else if(!GlobalVar.isConfirmPassWordValid(edt_password.getText().toString().trim(),edt_confirm_pass.getText().toString().trim())){
                    edt_confirm_pass.setError(activity.getResources().getString(R.string.error_invalid_pass));
                    focusView = edt_confirm_pass;
                    cancel = true;
                }
            }else if(member_type == 1){
                if(edt_user_name.getText().toString().trim().length()==0){
                    edt_user_name.setError(activity.getResources().getString(R.string.error_invalid_useer_name));
                    focusView = edt_user_name;
                    cancel = true;
                }else if(edt_first_name.getText().toString().trim().length()==0){
                    edt_first_name.setError(activity.getResources().getString(R.string.error_invalid_first_name));
                    focusView = edt_first_name;
                    cancel = true;
                }else if(edt_last_name.getText().toString().trim().length()==0){
                    edt_last_name.setError(activity.getResources().getString(R.string.error_invalid_last_name));
                    focusView = edt_last_name;
                    cancel = true;
                }else if(!GlobalVar.isEmailValid(edt_email.getText().toString().trim())){
                    edt_email.setError(activity.getResources().getString(R.string.error_invalid_email));
                    focusView = edt_email;
                    cancel = true;
                }else if(!GlobalVar.isPhoneNumberValid(edt_tel.getText().toString().trim())){
                    edt_tel.setError(activity.getResources().getString(R.string.error_invalid_tel));
                    focusView = edt_tel;
                    cancel = true;
                }
            }


            if(cancel) {
                focusView.requestFocus();
            }else {
                dateController = new DateController();
                success = true;
                tblMember = new TblMember();
                tblMember.setUser_name(edt_user_name.getText().toString());
                tblMember.setFirst_name(edt_first_name.getText().toString().replace("'"," "));
                tblMember.setLast_name(edt_last_name.getText().toString().replace("'"," "));
                tblMember.setEmail(edt_email.getText().toString());
                tblMember.setTel(edt_tel.getText().toString());
                tblMember.setPassword(edt_password.getText().toString());
                tblMember.setMember_type(member_type);
                tblMember.setAuthority("Driver");
                tblMember.setBirth_date(txt_date_of_birth.getText().toString().trim().length()==0 ? "" : dateController.convertDateFormat1To2(txt_date_of_birth.getText().toString().trim()));
                tblMember.setDevice_id(GlobalVar.getDeviceID(activity));
                tblMember.setFace_book_id(face_book_id);
                tblMember.setLanguage("en");
                tblMember.setTime_wait(0);
                if(!flagSex){
                    tblMember.setSex("W");
                }else {
                    tblMember.setSex("M");
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return success;
    }
    private boolean isDateBirthDay(Activity activity , String date){
        boolean success = true;
        if(date.length()==0) {
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
            alertDialogBuilder.setTitle(activity.getResources().getString(R.string.txtWarning));
            alertDialogBuilder.setMessage(activity.getResources().getString(R.string.error_invalid_birth_date));
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            txt_date_of_birth.setTextColor(Color.RED);
                            mView.setPre(2);
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

            success = false;
        }
        return success;
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_date_of_birth:
                setDateBirthDay();
                break;
        }

    }

}
