package com.dtc.sevice.truckclub.view.user.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dtc.sevice.truckclub.R;
import com.dtc.sevice.truckclub.helper.GlobalVar;
import com.dtc.sevice.truckclub.model.TblMember;
import com.dtc.sevice.truckclub.presenters.user.UserProfilePresenter;
import com.dtc.sevice.truckclub.service.ApiService;
import com.dtc.sevice.truckclub.until.ApplicationController;
import com.dtc.sevice.truckclub.until.DateController;
import com.dtc.sevice.truckclub.until.DialogController;
import com.dtc.sevice.truckclub.until.TaskController;
import com.dtc.sevice.truckclub.until.Updown_Image;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by May on 9/27/2017.
 */

public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener{
    private Toolbar toolbar;
    private static EditText edt_first_name,edt_last_name,edt_email,edt_tel,edt_birth;
    private ImageView img_profile,img_female,img_male;
    private static LinearLayout lay_female,lay_male,linear_first_name,linear_last_name,linear_email,linear_tel;
    private static MenuItem save,edit;
    private static boolean flagEdit = false;
    private int PROFILE_PIC = 1;
    private Activity _activity;
    private DialogController dialog;
    private DateController dateController;
    private ApplicationController _appController;
    private Updown_Image download_image;
    private Uri uriProfile;
    private Bitmap bitmapProfileDefault;
    private boolean defaultPicProfile = true;
    private boolean flagSex = false;
    private TaskController taskController;
    public static List<TblMember> listMembers;
    private ApiService mApiService;
    private UserProfilePresenter mUserProfilePresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        init();
        initListeners();
    }

    private void init(){
        _activity = UserProfileActivity.this;
        dialog = new DialogController();
        dateController = new DateController();
        _appController = new ApplicationController();
        download_image = new Updown_Image();
        taskController = new TaskController();
        edt_first_name = (EditText)findViewById(R.id.edt_first_name);
        edt_last_name = (EditText)findViewById(R.id.edt_last_name);
        edt_email = (EditText)findViewById(R.id.edt_email);
        edt_tel = (EditText)findViewById(R.id.edt_tel);
        edt_birth = (EditText)findViewById(R.id.edt_birth);
        img_profile = (ImageView)findViewById(R.id.img_profile);
        img_female = (ImageView)findViewById(R.id.img_female);
        img_male = (ImageView)findViewById(R.id.img_male);
        lay_female = (LinearLayout)findViewById(R.id.lay_female);
        lay_male = (LinearLayout)findViewById(R.id.lay_male);
        linear_first_name = (LinearLayout)findViewById(R.id.linear_first_name);
        linear_last_name = (LinearLayout)findViewById(R.id.linear_last_name);
        linear_email = (LinearLayout)findViewById(R.id.linear_email);
        linear_tel = (LinearLayout)findViewById(R.id.linear_tel);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backButton();
            }
        });
        setOnTouch();

        getInfo();
    }

    private void initListeners(){
        lay_female.setOnClickListener(this);
        lay_male.setOnClickListener(this);
        img_profile.setOnClickListener(this);
    }

    private void getInfo(){
        try {
            listMembers = new ArrayList<TblMember>();
            listMembers = taskController.getMember();
            if(listMembers.size()>0){
                setInfo();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setInfo(){
        try {
            if(listMembers.get(0).getMember_type() == 0){
                Picasso.with(_activity).load(GlobalVar.url_up_pic + listMembers.get(0).getName_pic_path())
                        .placeholder( R.drawable.progress_animation )
                        .fit().centerCrop().error( R.drawable.no_images ).into(img_profile);
            }else if(listMembers.get(0).getMember_type() == 1){
                Picasso.with(_activity).load(ApiService.url_facebook + listMembers.get(0).getFace_book_id() + ApiService.pic_facebook)
                        .placeholder( R.drawable.progress_animation )
                        .fit().centerCrop().error( R.drawable.no_images ).into(img_profile);
            }

            edt_first_name.setText((listMembers.get(0).getFirst_name() ==null) ? "":listMembers.get(0).getFirst_name());
            edt_last_name.setText((listMembers.get(0).getLast_name() ==null) ? "":listMembers.get(0).getLast_name());
            edt_email.setText((listMembers.get(0).getEmail() ==null) ? "":listMembers.get(0).getEmail());
            edt_tel.setText((listMembers.get(0).getTel() ==null) ? "":listMembers.get(0).getTel());
            edt_birth.setText((listMembers.get(0).getBirth_date() ==null) ? "":dateController.convertDateFormat2To1(listMembers.get(0).getBirth_date()));
            if(listMembers.get(0).getSex().equalsIgnoreCase("W")){
                flagSex = false;
            }else {
                flagSex = true;
            }

            setSex();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setSex(){
        if(flagSex){
            img_female.setImageResource(R.drawable.ic_female_gray);
            img_male.setImageResource(R.drawable.ic_men_black);
        }else {
            img_female.setImageResource(R.drawable.ic_female_black);
            img_male.setImageResource(R.drawable.ic_men_gray);
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lay_female:
                if(flagEdit){
                    img_female.setImageResource(R.drawable.ic_female_black);
                    img_male.setImageResource(R.drawable.ic_men_gray);
                    flagSex = false;
                }
                break;
            case R.id.lay_male:
                if(flagEdit){
                    img_female.setImageResource(R.drawable.ic_female_gray);
                    img_male.setImageResource(R.drawable.ic_men_black);
                    flagSex = true;
                }
                break;
            case R.id.img_profile:
                if(flagEdit) {
                    if(listMembers.get(0).getMember_type() == 0)
                        choosePic();
                }else
                    expandPic();
                break;

        }
    }

    private void setOnTouch(){
        try {
            edt_first_name.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                        setBackgroundLinear(1);

                        return true;
                    }
                    return false;
                }
            });
            edt_last_name.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                        setBackgroundLinear(2);

                        return true;
                    }
                    return false;
                }
            });
            edt_email.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                        setBackgroundLinear(3);

                        return true;
                    }
                    return false;
                }
            });
            edt_tel.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                        setBackgroundLinear(4);

                        return true;
                    }
                    return false;
                }
            });
            edt_birth.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                        setBackgroundLinear(5);
                        setDatePicker();

                        return true;
                    }
                    return false;
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setBackgroundLinear(int i){
        try {
            if(i == 1){
                linear_first_name.setBackgroundResource(R.drawable.buttonshape_white_border_orange);
                linear_last_name.setBackgroundResource(R.drawable.buttonshape_find_location_search_white);
                linear_email.setBackgroundResource(R.drawable.buttonshape_find_location_search_white);
                linear_tel.setBackgroundResource(R.drawable.buttonshape_find_location_search_white);
                edt_first_name.requestFocus();
            }else if(i == 2){
                linear_last_name.setBackgroundResource(R.drawable.buttonshape_white_border_orange);
                linear_first_name.setBackgroundResource(R.drawable.buttonshape_find_location_search_white);
                linear_email.setBackgroundResource(R.drawable.buttonshape_find_location_search_white);
                linear_tel.setBackgroundResource(R.drawable.buttonshape_find_location_search_white);
                edt_last_name.requestFocus();
            }else if(i == 3){
                linear_email.setBackgroundResource(R.drawable.buttonshape_white_border_orange);
                linear_last_name.setBackgroundResource(R.drawable.buttonshape_find_location_search_white);
                linear_first_name.setBackgroundResource(R.drawable.buttonshape_find_location_search_white);
                linear_tel.setBackgroundResource(R.drawable.buttonshape_find_location_search_white);
                edt_email.requestFocus();
            }else if(i == 4){
                linear_tel.setBackgroundResource(R.drawable.buttonshape_white_border_orange);
                linear_last_name.setBackgroundResource(R.drawable.buttonshape_find_location_search_white);
                linear_email.setBackgroundResource(R.drawable.buttonshape_find_location_search_white);
                linear_first_name.setBackgroundResource(R.drawable.buttonshape_find_location_search_white);
                edt_tel.requestFocus();
            }else if(i == 5){
                linear_first_name.setBackgroundResource(R.drawable.buttonshape_find_location_search_white);
                linear_last_name.setBackgroundResource(R.drawable.buttonshape_find_location_search_white);
                linear_email.setBackgroundResource(R.drawable.buttonshape_find_location_search_white);
                linear_tel.setBackgroundResource(R.drawable.buttonshape_find_location_search_white);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void choosePic() {
        img_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent
                        , "Select Picture"), PROFILE_PIC);
            }
        });

        img_profile.setTag("profile_user");

    }

    private void expandPic(){
        final Dialog dialog = new Dialog(UserProfileActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.image_view);
        dialog.setCancelable(true);
        dialog.setTitle("Profile");

        ImageView imProfileZoom = (ImageView) dialog.findViewById(R.id.im);

        if(listMembers.get(0).getMember_type() == 0){
            Picasso.with(_activity).load(GlobalVar.url_up_pic + listMembers.get(0).getName_pic_path())
                    .placeholder( R.drawable.progress_animation ).error( R.drawable.no_images ).into(imProfileZoom);
        }else if(listMembers.get(0).getMember_type() == 1){
            Picasso.with(_activity).load(ApiService.url_facebook + listMembers.get(0).getFace_book_id() + ApiService.pic_facebook)
                    .placeholder( R.drawable.progress_animation ).error( R.drawable.no_images ).into(imProfileZoom);
        }

        dialog.show();
    }

    private void setDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(UserProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {

                String aa = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                edt_birth.setText(dateController.convertDateFormat2To1(aa));
            }
        }, GlobalVar.mYear, GlobalVar.mMonth, GlobalVar.mDay);
        datePickerDialog.show();
    }

    private void setEdit(){
        try {
            edt_first_name.setEnabled(true);
            int pos = edt_first_name.getText().length();
            edt_first_name.setSelection(pos);
            edt_first_name.findFocus();
            edt_last_name.setEnabled(true);
            edt_email.setEnabled(true);
            edt_tel.setEnabled(true);
            edt_birth.setEnabled(true);
            lay_female.setEnabled(true);
            lay_male.setEnabled(true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setBackgroundLinearDefault(){
        try {
            linear_first_name.setBackgroundResource(R.drawable.buttonshape_find_location_search_white);
            linear_last_name.setBackgroundResource(R.drawable.buttonshape_find_location_search_white);
            linear_email.setBackgroundResource(R.drawable.buttonshape_find_location_search_white);
            linear_tel.setBackgroundResource(R.drawable.buttonshape_find_location_search_white);
            edt_first_name.requestFocus();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setDefault(){
        try {
            flagEdit = false;
            save.setVisible(false);
            edit.setVisible(true);
            edt_first_name.setEnabled(false);
            edt_last_name.setEnabled(false);
            edt_email.setEnabled(false);
            edt_tel.setEnabled(false);
            edt_birth.setEnabled(false);
            lay_female.setEnabled(false);
            lay_male.setEnabled(false);
            setBackgroundLinearDefault();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private String pathDefault="";
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
                img_profile.setImageBitmap(bitmapProfileDefault);

                defaultPicProfile = false;

                pathDefault = GlobalVar.getPath(UserProfileActivity.this, uriProfile);
            }

        }
    }

    private void setConfirmUpdateProfile(){
        try {
            boolean cancel = false;
            View focusView = null;
            listMembers.get(0).setFirst_name(edt_first_name.getText().toString().trim());
            listMembers.get(0).setLast_name(edt_last_name.getText().toString().trim());
            listMembers.get(0).setEmail(edt_email.getText().toString().trim());
            listMembers.get(0).setTel(edt_tel.getText().toString().trim());
            listMembers.get(0).setBirth_date(edt_birth.getText().toString().trim().length()==0 ? "" : dateController.convertDateFormat1To2(edt_birth.getText().toString().trim()));
            listMembers.get(0).setSex((flagSex == true) ? "M" : "W");

            if(edt_first_name.getText().toString().length()==0) {
                edt_first_name.setError(getString(R.string.error_invalid_first_name));
                focusView = edt_first_name;
                cancel = true;
            }else if(edt_last_name.getText().toString().length()==0){
                edt_last_name.setError(getString(R.string.error_invalid_first_name));
                focusView = edt_last_name;
                cancel = true;
            }else if(!GlobalVar.isEmailValid(edt_email.getText().toString())){
                edt_email.setError(getString(R.string.error_invalid_first_name));
                focusView = edt_email;
                cancel = true;
            }else if(!GlobalVar.isPhoneNumberValid(edt_tel.getText().toString())){
                edt_tel.setError(getString(R.string.error_invalid_tel));
                focusView = edt_tel;
                cancel = true;
            }

            if(cancel) {
                focusView.requestFocus();
            }else {
                if(isDateBirthDay(edt_birth.getText().toString().trim())){
                    if(!defaultPicProfile){
                        String new_path = GlobalVar.saveImage(bitmapProfileDefault,pathDefault);
                        listMembers.get(0).setName_pic_path(GlobalVar.findPicName(new_path));
                        final String result = download_image.SendImageNode(UserProfileActivity.this, new_path);
                        Log.i("Upload Image",result);
                    }
                    mApiService = new ApiService();
                    mUserProfilePresenter = new UserProfilePresenter(this,mApiService);
                    mUserProfilePresenter.updateProfile();

                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean isDateBirthDay(String date){
        boolean success = true;
        if(date.length()==0) {
            dialog.dialogNolmal(_activity, getResources().getString(R.string.txtWarning), getResources().getString(R.string.error_invalid_birth_date));
            success = false;
        }
        return success;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit, menu);
        save = menu.findItem(R.id.action_save);
        edit = menu.findItem(R.id.action_edit);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                setConfirmUpdateProfile();
                //setDefault();
                break;
            case R.id.action_edit:
                flagEdit = true;
                save.setVisible(true);
                edit.setVisible(false);
                setEdit();
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            backButton();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    private void backButton(){
        Intent i = new Intent(UserProfileActivity.this,UserMainActivity2.class);
        startActivity(i);
        finish();
    }
}
