package com.dtc.sevice.truckclub.view.user.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dtc.sevice.truckclub.R;
import com.dtc.sevice.truckclub.helper.GlobalVar;
import com.dtc.sevice.truckclub.model.TblMember;
import com.dtc.sevice.truckclub.presenters.user.UserRegisterPresenter;
import com.dtc.sevice.truckclub.service.ApiService;
import com.dtc.sevice.truckclub.until.ApplicationController;
import com.dtc.sevice.truckclub.until.DateController;
import com.dtc.sevice.truckclub.until.DialogController;
import com.dtc.sevice.truckclub.until.Updown_Image;
import com.dtc.sevice.truckclub.view.LoginSecondActivity;
import com.facebook.AccessToken;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by admin on 9/20/2017 AD.
 */

public class UserRegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText UserUsernameEdt,UserPhoneEdt,UserBirthEdt,UserPasswordEdt,UserConfirmPasswordEdt,UserFirstnameEdt,UserLastnameEdt,UserEmailEdt;
    private LinearLayout lay_female,lay_male,linear_user_name,linear_tel,linear_pass,linear_confirm_pass,linear_user_name_head,linear_face_book_head,lay_group_password;
    private ImageView img_female,img_male,img_head;
    private Button UserConfirmRegisterBtn;
    private Toolbar toolbar;
    private Activity _activity;
    private ApiService mApiService;
    private UserRegisterPresenter mRegisterPresenter;
    private boolean flagSex = false;
    public TblMember member;
    private String str_authen,str_device_id,name_pic_path = "",face_book_id="";
    private int member_type=0;
    private DateController dateController;
    private DialogController dialogController;
    private ApplicationController _appController;
    private Updown_Image download_image;
    private int PROFILE_PIC = 1;
    private Uri uriProfile;
    private Bitmap bitmapProfileDefault;
    private boolean defaultPicProfile = true;
    public String first_name = "",last_name = "",email = "",tel = "",sex = "",birthday = "";
    private Intent extra;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        setIntentFromLogin();
        init();
    }

    private void init() {
        _activity = UserRegisterActivity.this;
        dateController = new DateController();
        dialogController = new DialogController();
        _appController = new ApplicationController();
        download_image = new Updown_Image();
        UserUsernameEdt = (EditText)findViewById(R.id.UserUsernameEdt);
        UserFirstnameEdt = (EditText)findViewById(R.id.UserFirstnameEdt);
        UserLastnameEdt = (EditText)findViewById(R.id.UserLastnameEdt);
        UserEmailEdt = (EditText)findViewById(R.id.UserEmailEdt);
        UserPhoneEdt = (EditText)findViewById(R.id.UserPhoneEdt);
        UserBirthEdt = (EditText)findViewById(R.id.UserBirthEdt);
        UserPasswordEdt = (EditText)findViewById(R.id.UserPasswordEdt);
        UserConfirmPasswordEdt = (EditText)findViewById(R.id.UserConfirmPasswordEdt);
        lay_female = (LinearLayout)findViewById(R.id.lay_female);
        lay_male = (LinearLayout)findViewById(R.id.lay_male);
        linear_user_name = (LinearLayout)findViewById(R.id.linear_user_name);
        linear_tel = (LinearLayout)findViewById(R.id.linear_tel);
        linear_pass = (LinearLayout)findViewById(R.id.linear_pass);
        linear_confirm_pass = (LinearLayout)findViewById(R.id.linear_confirm_pass);
        linear_user_name_head = (LinearLayout)findViewById(R.id.linear_user_name_head);
        linear_face_book_head = (LinearLayout)findViewById(R.id.linear_face_book_head);
        lay_group_password = (LinearLayout)findViewById(R.id.lay_group_password);
        img_female = (ImageView)findViewById(R.id.img_female);
        img_male = (ImageView)findViewById(R.id.img_male);
        img_head = (ImageView)findViewById(R.id.img_head);
        UserConfirmRegisterBtn = (Button)findViewById(R.id.UserConfirmRegisterBtn);
        lay_female.setOnClickListener(this);
        lay_male.setOnClickListener(this);
        UserConfirmRegisterBtn.setOnClickListener(this);
        UserBirthEdt.setOnClickListener(this);
        img_head.setOnClickListener(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backButton();
            }
        });
        setOnTount();
        setMemberType();
    }

    private void setIntentFromLogin(){
        try {
            extra = getIntent();
            if (extra != null) {
                if(extra.getStringExtra("authen")!=null)
                    str_authen = extra.getStringExtra("authen");
                if(extra.getStringExtra("member_type")!=null)
                    member_type = Integer.parseInt(extra.getStringExtra("member_type"));
                if(extra.getStringExtra("id")!=null)
                    face_book_id = extra.getStringExtra("id");
                if(extra.getStringExtra("first_name")!=null)
                    first_name = extra.getStringExtra("first_name");
                if(extra.getStringExtra("last_name")!=null)
                    last_name = extra.getStringExtra("last_name");
                if(extra.getStringExtra("email")!=null)
                    email = extra.getStringExtra("email");
                if(extra.getStringExtra("gender")!=null)
                    sex = extra.getStringExtra("gender");
                if(extra.getStringExtra("birthday")!=null)
                    birthday = extra.getStringExtra("birthday");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getDataFromString(){
        UserFirstnameEdt.setText(first_name);
        UserLastnameEdt.setText(last_name);
        UserEmailEdt.setText(email);
        UserPhoneEdt.setText(tel);
        UserBirthEdt.setText(((birthday == null) || (birthday.length()==0) ? "" : dateController.convertDateFormat7To1(birthday)));
        Picasso.with(_activity)
                .load(ApiService.url_facebook + face_book_id + ApiService.pic_facebook)
                .into(img_head);
        setGender();
    }

    private void setGender(){
        if(sex.length()>0){
            if(sex.equalsIgnoreCase("female")){
                flagSex = false;
            }else if(sex.equalsIgnoreCase("male")){
                flagSex = true;
            }
        }
    }

    private void setMemberType(){
        if(member_type == 0){
            linear_face_book_head.setVisibility(View.GONE);
            linear_user_name_head.setVisibility(View.VISIBLE);
            lay_group_password.setVisibility(View.VISIBLE);
        }else if(member_type == 1){
            linear_face_book_head.setVisibility(View.VISIBLE);
            linear_user_name_head.setVisibility(View.GONE);
            lay_group_password.setVisibility(View.GONE);
            getDataFromString();

        }

    }

    private void setOnTount(){
        try {
            UserUsernameEdt.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                        linear_tel.setBackgroundResource(R.drawable.buttonshape_find_location_search_white);
                        linear_pass.setBackgroundResource(R.drawable.buttonshape_find_location_search_white);
                        linear_confirm_pass.setBackgroundResource(R.drawable.buttonshape_find_location_search_white);
                        linear_user_name.setBackgroundResource(R.drawable.buttonshape_white_border_orange);
                        UserUsernameEdt.requestFocus();

                        return true;
                    }
                    return false;

                }
            });

            UserPhoneEdt.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                        linear_user_name.setBackgroundResource(R.drawable.buttonshape_find_location_search_white);
                        linear_pass.setBackgroundResource(R.drawable.buttonshape_find_location_search_white);
                        linear_confirm_pass.setBackgroundResource(R.drawable.buttonshape_find_location_search_white);
                        linear_tel.setBackgroundResource(R.drawable.buttonshape_white_border_orange);
                        UserPhoneEdt.requestFocus();

                        return true;
                    }
                    return false;
                }
            });

            UserPasswordEdt.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                        linear_user_name.setBackgroundResource(R.drawable.buttonshape_find_location_search_white);
                        linear_tel.setBackgroundResource(R.drawable.buttonshape_find_location_search_white);
                        linear_confirm_pass.setBackgroundResource(R.drawable.buttonshape_find_location_search_white);
                        linear_pass.setBackgroundResource(R.drawable.buttonshape_white_border_orange);
                        UserPasswordEdt.requestFocus();

                        return true;
                    }
                    return false;
                }
            });

            UserConfirmPasswordEdt.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                        linear_user_name.setBackgroundResource(R.drawable.buttonshape_find_location_search_white);
                        linear_tel.setBackgroundResource(R.drawable.buttonshape_find_location_search_white);
                        linear_pass.setBackgroundResource(R.drawable.buttonshape_find_location_search_white);
                        linear_confirm_pass.setBackgroundResource(R.drawable.buttonshape_white_border_orange);
                        UserConfirmPasswordEdt.requestFocus();

                        return true;
                    }
                    return false;
                }
            });

//            Pattern ps = Pattern.compile("[a-zA-Z ]");
//            Matcher ms = ps.matcher(UserUsernameEdt.getText().toString());
//            boolean bs = ms.matches();
//            if (bs == false) {
//                if (ErrorMessage.contains("invalid"))
//                    ErrorMessage = ErrorMessage + "state,";
//                else
//                    ErrorMessage = ErrorMessage + "invalid state,";
//
//            }
            //UserUsernameEdt.setKeyListener(DigitsKeyListener.getInstance("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setConfirmRegister(){
        try {
            boolean cancel = false;
            View focusView = null;

            member = new TblMember();
            member.setFirst_name("");
            member.setLast_name("");
            member.setEmail("");
            member.setUser_name(UserUsernameEdt.getText().toString().trim());
            member.setTel(UserPhoneEdt.getText().toString().trim());
            member.setBirth_date(UserBirthEdt.getText().toString().trim().length()==0 ? "" : dateController.convertDateFormat1To2(UserBirthEdt.getText().toString().trim()));
            member.setSex((flagSex == true) ? "M" : "W");
            member.setPassword(UserPasswordEdt.getText().toString().trim());
            member.setAuthority(getString(R.string.txtUser));
            member.setDevice_id(GlobalVar.getDeviceID(UserRegisterActivity.this));
            member.setMember_type(member_type);
            member.setFace_book_id(face_book_id);
            member.setTime_wait(10);
            member.setLanguage("en");

            if(member.getUser_name().length()==0){
                UserUsernameEdt.setError(getString(R.string.error_invalid_useer_name));
                focusView = UserUsernameEdt;
                cancel = true;
            }else if(!GlobalVar.isPhoneNumberValid(member.getTel())){
                UserPhoneEdt.setError(getString(R.string.error_invalid_tel));
                focusView = UserPhoneEdt;
                cancel = true;
            }else if(!GlobalVar.isPassWordValid(member.getPassword())){
                UserPasswordEdt.setError(getString(R.string.error_invalid_pass));
                focusView = UserPasswordEdt;
                cancel = true;
            }else if(!GlobalVar.isConfirmPassWordValid(member.getPassword(),UserConfirmPasswordEdt.getText().toString().trim())){
                UserConfirmPasswordEdt.setError(getString(R.string.error_invalid_pass));
                focusView = UserConfirmPasswordEdt;
                cancel = true;
            }

            if(cancel) {
                focusView.requestFocus();
            }else {
                if(isDateBirthDay(member.getBirth_date())){
                    if(defaultPicProfile){
                        dialogController.dialogNolmal(_activity, getResources().getString(R.string.txtWarning), getResources().getString(R.string.error_invalid_image));
                    }else {
                        String new_path = GlobalVar.saveImage(bitmapProfileDefault,pathDefault);
                        member.setName_pic_path(GlobalVar.findPicName(new_path));
                        mApiService = new ApiService();
                        mRegisterPresenter = new UserRegisterPresenter(this,mApiService);
                        mRegisterPresenter.loadRegister();
                        final String result = download_image.SendImageNode(UserRegisterActivity.this, new_path);
                        Log.i("Upload Image",result);
                    }

                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setConfirmRegisterWhitFacebook(){
        try {
            boolean cancel = false;
            View focusView = null;
            member = new TblMember();
            member.setFirst_name(UserFirstnameEdt.getText().toString().replace("'"," "));
            member.setLast_name(UserLastnameEdt.getText().toString().replace("'"," "));
            member.setEmail(UserEmailEdt.getText().toString().trim());
            member.setTel(UserPhoneEdt.getText().toString().trim());
            member.setBirth_date(UserBirthEdt.getText().toString().trim().length()==0 ? "" : dateController.convertDateFormat1To2(UserBirthEdt.getText().toString().trim()));
            member.setSex((flagSex == true) ? "M" : "W");
            member.setAuthority(str_authen);
            member.setDevice_id(GlobalVar.getDeviceID(UserRegisterActivity.this));
            member.setMember_type(member_type);
            member.setFace_book_id(face_book_id);
            member.setName_pic_path(name_pic_path);
            member.setTime_wait(10);
            member.setLanguage("en");

            if(member.getFirst_name().length()==0){
                UserFirstnameEdt.setError(getString(R.string.error_invalid_first_name));
                focusView = UserFirstnameEdt;
                cancel = true;
            }else if(member.getLast_name().length()==0){
                UserLastnameEdt.setError(getString(R.string.error_invalid_last_name));
                focusView = UserLastnameEdt;
                cancel = true;
            }else if(!GlobalVar.isEmailValid(member.getEmail().toString().trim())){
                UserEmailEdt.setError(getString(R.string.error_invalid_email));
                focusView = UserEmailEdt;
                cancel = true;
            }else if(!GlobalVar.isPhoneNumberValid(member.getTel())){
                UserPhoneEdt.setError(getString(R.string.error_invalid_tel));
                focusView = UserPhoneEdt;
                cancel = true;
            }

            if(cancel) {
                focusView.requestFocus();
            }else {
                if(isDateBirthDay(member.getBirth_date())){
//                    if(defaultPicProfile){
//                        dialogController.dialogNolmal(_activity, "Warning", getResources().getString(R.string.error_invalid_image));
//                    }else {
                        mApiService = new ApiService();
                        mRegisterPresenter = new UserRegisterPresenter(this,mApiService);
                        mRegisterPresenter.loadRegister();
                        if(!defaultPicProfile){
                            final String result = download_image.SendImageNode(UserRegisterActivity.this, pathDefault);
                            Log.i("Upload Image",result);
                        }

//                    }

                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean isDateBirthDay(String date){
        boolean success = true;
        if(date.length()==0) {
            dialogController.dialogNolmal(_activity, getResources().getString(R.string.txtWarning), getResources().getString(R.string.error_invalid_birth_date));
            success = false;
        }
        return success;
    }

    private void backButton(){
        AccessToken.setCurrentAccessToken(null);
        Intent i = new Intent(_activity,LoginSecondActivity.class);
        i.putExtra("authen",getString(R.string.txtUser));
        startActivity(i);
        finish();
    }

    private void setDateBirthDay() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(_activity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {

                String aa = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                UserBirthEdt.setText(dateController.convertDateFormat2To1(aa));
            }
        }, GlobalVar.mYear, GlobalVar.mMonth, GlobalVar.mDay);
        datePickerDialog.show();
    }

    private void setSelectImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent
                , "Select Picture"), PROFILE_PIC);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lay_female:
                img_female.setImageResource(R.drawable.ic_female_black);
                img_male.setImageResource(R.drawable.ic_men_gray);
                flagSex = false;
                break;
            case R.id.lay_male:
                img_female.setImageResource(R.drawable.ic_female_gray);
                img_male.setImageResource(R.drawable.ic_men_black);
                flagSex = true;
                break;
            case R.id.UserBirthEdt:
                setDateBirthDay();
                break;
            case R.id.img_head:
                setSelectImage();
                break;
            case R.id.UserConfirmRegisterBtn:
                if(member_type == 0)
                    setConfirmRegister();
                else
                    setConfirmRegisterWhitFacebook();
//                mApiService = new ApiService();
//                mRegisterPresenter = new UserRegisterPresenter(UserRegisterActivity.this,mApiService);
//                mRegisterPresenter.loadRegister();
                break;
        }

    }

    String pathDefault="";
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        InputStream imageStream = null;
        if (resultCode == RESULT_OK) {
            if (requestCode == PROFILE_PIC) {
                uriProfile = data.getData();
                try {
                    imageStream = getContentResolver().openInputStream(uriProfile);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                bitmapProfileDefault = _appController.getResizedBitmap(BitmapFactory.decodeStream(imageStream), 450 , 450);
                img_head.setImageBitmap(bitmapProfileDefault);

                defaultPicProfile = false;

                pathDefault = GlobalVar.getPath(UserRegisterActivity.this, uriProfile);

            }

        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            backButton();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}
