package com.dtc.sevice.truckclub.view.driver.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.dtc.sevice.truckclub.R;
import com.dtc.sevice.truckclub.adapter.AddImageAdapter;
import com.dtc.sevice.truckclub.adapter.ItemOffsetDecoration;
import com.dtc.sevice.truckclub.helper.GlobalVar;
import com.dtc.sevice.truckclub.model.TblCarDetail;
import com.dtc.sevice.truckclub.model.TblCarGroup;
import com.dtc.sevice.truckclub.model.TblMember;
import com.dtc.sevice.truckclub.presenters.driver.DriverProfilePresenter;
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

public class DriverProfileActivity extends AppCompatActivity implements View.OnClickListener{
    private Toolbar toolbar;
    private Button btn_info,btn_vehicle;
    private ImageView img_profile,img_first_name,img_last_name,img_mail,img_tel;
    private EditText edt_first_name,edt_last_name,edt_mail,edt_tel,edt_car_brand,edt_car_model,edt_license_plate,edt_license_province,edt_sum1,edt_sum2;
    private TextView txt_birth;
    private Spinner spn_sex,spn_car_group,spn_tow;
    private CheckBox chk_weight,chk_option;
    private LinearLayout linear_tow;
    private ScrollView scroll_profile,scroll_vehicle;
    private static RecyclerView recycler_view;
    private MenuItem save,edit;
    private boolean flagEdit = false;
    public static List<TblMember> listMembers;
    private TaskController taskController;
    private Updown_Image download_image;
    private DateController dateController;
    private Activity _activity;
    private static List<TblCarGroup> listCarGroups;
    private String[] arrCarGroup,arrSex,arrCarTow;
    private DialogController dialog;

    private DriverProfilePresenter driverProfilePresenter;
    private ApiService apiService;
    private static TblCarDetail carDetail;
    private static AddImageAdapter mAdapter;
    public static int positionOptionCar = 0,positionCarTow = 0,positionSex;
    private boolean flagSelectCarTow = true;
    private boolean defaultPicProfile = true;
    private Bitmap bitmapProfileDefault;
    private int PROFILE_PIC = 1;
    private Uri uriProfile;
    private ApplicationController _appController;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_profile);
        init();
        initListeners();
    }

    private void init(){
        dialog = new DialogController();
        taskController = new TaskController();
        download_image = new Updown_Image();
        dateController = new DateController();
        _appController = new ApplicationController();
        _activity = new DriverProfileActivity();
        btn_info = (Button)findViewById(R.id.btn_info);
        btn_vehicle = (Button)findViewById(R.id.btn_vehicle);
        img_profile = (ImageView)findViewById(R.id.img_profile);
        img_first_name = (ImageView)findViewById(R.id.img_first_name);
        img_last_name = (ImageView)findViewById(R.id.img_last_name);
        img_mail = (ImageView)findViewById(R.id.img_mail);
        img_tel = (ImageView)findViewById(R.id.img_tel);
        edt_first_name = (EditText)findViewById(R.id.edt_first_name);
        edt_last_name = (EditText)findViewById(R.id.edt_last_name);
        edt_mail = (EditText)findViewById(R.id.edt_mail);
        edt_tel = (EditText)findViewById(R.id.edt_tel);
        edt_car_brand = (EditText)findViewById(R.id.edt_car_brand);
        edt_car_model = (EditText)findViewById(R.id.edt_car_model);
        edt_license_plate = (EditText)findViewById(R.id.edt_license_plate);
        edt_license_province = (EditText)findViewById(R.id.edt_license_province);
        edt_sum1 = (EditText)findViewById(R.id.edt_sum1);
        edt_sum2 = (EditText)findViewById(R.id.edt_sum2);
        spn_car_group = (Spinner) findViewById(R.id.spn_car_group);
        spn_tow = (Spinner)findViewById(R.id.spn_tow);
        txt_birth = (TextView)findViewById(R.id.txt_birth);
        spn_sex = (Spinner)findViewById(R.id.spn_sex);
        chk_weight = (CheckBox) findViewById(R.id.chk_weight);
        chk_option = (CheckBox)findViewById(R.id.chk_option);
        scroll_profile = (ScrollView)findViewById(R.id.scroll_profile);
        scroll_vehicle = (ScrollView)findViewById(R.id.scroll_vehicle);
        recycler_view = (RecyclerView)findViewById(R.id.recycler_view);
        linear_tow = (LinearLayout)findViewById(R.id.linear_tow);
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

        getMember();
        if(listMembers != null && listMembers.size()>0){
            setCarDetail();
            setText();
        }
        recycler_view.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 3);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.addItemDecoration(new ItemOffsetDecoration(this, R.dimen.item_offset));

    }

    private void initListeners(){
        btn_info.setOnClickListener(this);
        btn_vehicle.setOnClickListener(this);
        txt_birth.setOnClickListener(this);
    }

    private void getMember(){
        try {
            listMembers = new ArrayList<TblMember>();
            listMembers = taskController.getMember();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setText(){
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

            edt_first_name.setText(listMembers.get(0).getFirst_name());
            edt_last_name.setText(listMembers.get(0).getLast_name());
            edt_mail.setText(listMembers.get(0).getEmail());
            txt_birth.setText((listMembers.get(0).getBirth_date() ==null) ? "":dateController.convertDateFormat2To1(listMembers.get(0).getBirth_date()));
            edt_tel.setText(listMembers.get(0).getTel());
            setSpinnerSex();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setCarDetail(){
        try {
            apiService = new ApiService();
            driverProfilePresenter = new DriverProfilePresenter(DriverProfileActivity.this,apiService);
            driverProfilePresenter.loadCarDetail();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateCarDetail(TblCarDetail detail){
        try {
            carDetail = new TblCarDetail();
            carDetail = detail;
            edt_car_brand.setText(carDetail.getCar_brand());
            edt_car_model.setText(carDetail.getCar_model());
            edt_license_plate.setText(carDetail.getLicense_plate());
            edt_license_province.setText(carDetail.getProvince());
            edt_sum1.setText(String.valueOf(carDetail.getCar_wheels()));
            edt_sum2.setText(String.valueOf(carDetail.getCar_tons()));
            if(carDetail.getGroup_id()==3)
                linear_tow.setVisibility(View.VISIBLE);
            else{
                linear_tow.setVisibility(View.GONE);
                if(carDetail.getGroup_id()==1){
                    chk_option.setVisibility(View.VISIBLE);
                    if(carDetail.getOption_trailer()==0)
                        chk_option.setChecked(false);
                    else
                        chk_option.setChecked(true);
                }else {
                    chk_option.setVisibility(View.GONE);
                }
            }

            if(carDetail.getSum_weight()==0)
                chk_weight.setChecked(false);
            else
                chk_weight.setChecked(true);
            setCarGroup();
            setAdapter();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setAdapter(){
        try {
            mAdapter = new AddImageAdapter(this,carDetail.getPicture());
            recycler_view.setAdapter(mAdapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setCarGroup(){
        try {
            listCarGroups = taskController.getCarGroup();
            List<String> list = new ArrayList<String>();
            for(TblCarGroup c: listCarGroups){
                list.add(c.getName_group());
            }
            arrCarGroup = list.toArray(new String[0]);
            setSpinnerCarGroup();
            setSpinnerCarTow();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void setSpinnerSex(){
        try {
            arrSex = getResources().getStringArray(R.array.SexArray);
            ArrayAdapter<String> sexArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, arrSex);
            spn_sex.setAdapter(sexArrayAdapter);
            spn_sex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if(!flagEdit){
                        if(listMembers.get(0).getSex().equalsIgnoreCase("M")){
                            positionSex = 0;
                            spn_sex.setSelection(0);
                        }else if(listMembers.get(0).getSex().equalsIgnoreCase("W")){
                            spn_sex.setSelection(1);
                            positionSex = 1;
                        }

                    }else {
                        positionSex = i;
                        spn_sex.setSelection(positionSex);
//                        setOption(i);
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

    private void setSpinnerCarGroup(){
        try {
            ArrayAdapter<String> groupArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, arrCarGroup);
            spn_car_group.setAdapter(groupArrayAdapter);
            spn_car_group.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if(!flagEdit){
                        positionOptionCar = carDetail.getGroup_id()-1;
                        spn_car_group.setSelection(carDetail.getGroup_id()-1);
                    }else {
                        positionOptionCar = i;
                        spn_car_group.setSelection(positionOptionCar);
                        setOption(i);
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

    private void setOption(int i){
        try {
//            flagSelectOption = true;
//            linear_group_option.setVisibility(View.VISIBLE);
            if(i == 0){
                linear_tow.setVisibility(View.GONE);
                chk_option.setVisibility(View.VISIBLE);
            }else if(i == 2){
                linear_tow.setVisibility(View.VISIBLE);
                chk_option.setVisibility(View.GONE);
            }else {
                linear_tow.setVisibility(View.GONE);
                chk_option.setVisibility(View.GONE);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setSpinnerCarTow(){
        try {
            arrCarTow = getResources().getStringArray(R.array.car_tow);
            ArrayAdapter<String> towArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arrCarTow);
            spn_tow.setAdapter(towArrayAdapter);
            spn_tow.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    positionCarTow = i;

//                    if(positionCarTow == 0){
//                        flagSelectCarTow = false;
//                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_info:
                //if(!flagEdit){
                    btn_info.setBackgroundResource(R.drawable.buttonshape_orange);
                    btn_vehicle.setBackgroundResource(R.drawable.buttonshape_find_location_search_white);
                    scroll_vehicle.setVisibility(View.GONE);
                    scroll_profile.setVisibility(View.VISIBLE);
                //}
                break;
            case R.id.btn_vehicle:
                //if(!flagEdit){
                    btn_vehicle.setBackgroundResource(R.drawable.buttonshape_orange);
                    btn_info.setBackgroundResource(R.drawable.buttonshape_find_location_search_white);
                    scroll_profile.setVisibility(View.GONE);
                    scroll_vehicle.setVisibility(View.VISIBLE);
                //}

                break;
            case R.id.txt_birth:
                setDateBirthDay();
                break;

        }
    }

    private void setEdit(){
        try {
            edt_first_name.setEnabled(true);
            edt_last_name.setEnabled(true);
            edt_mail.setEnabled(true);
            txt_birth.setEnabled(true);
            edt_tel.setEnabled(true);

            ////car
            edt_car_brand.setEnabled(true);
            edt_car_model.setEnabled(true);
            edt_license_plate.setEnabled(true);
            edt_license_province.setEnabled(true);
            edt_sum1.setEnabled(true);
            edt_sum2.setEnabled(true);
            chk_weight.setEnabled(true);
            chk_option.setEnabled(true);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setConfirmSave(){
        try {
            boolean cancel = false;
            View focusView = null;
            if(edt_first_name.getText().toString().length()==0) {
                edt_first_name.setError(getString(R.string.error_invalid_first_name));
                focusView = edt_first_name;
                cancel = true;
            }else if(edt_last_name.getText().toString().length()==0){
                edt_last_name.setError(getString(R.string.error_invalid_first_name));
                focusView = edt_last_name;
                cancel = true;
            }else if(!GlobalVar.isEmailValid(edt_mail.getText().toString())){
                edt_mail.setError(getString(R.string.error_invalid_first_name));
                focusView = edt_mail;
                cancel = true;
            }else if(!GlobalVar.isPhoneNumberValid(edt_tel.getText().toString())){
                edt_tel.setError(getString(R.string.error_invalid_tel));
                focusView = edt_tel;
                cancel = true;
            }else if(edt_car_brand.getText().toString().length()==0) {
                edt_car_brand.setError(getString(R.string.error_invalid_car_brands));
                focusView = edt_car_brand;
                cancel = true;
            }else if(edt_car_model.getText().toString().length()==0) {
                edt_car_model.setError(getString(R.string.error_invalid_car_model));
                focusView = edt_car_model;
                cancel = true;
            }else if(edt_license_plate.getText().toString().length()==0) {
                edt_license_plate.setError(getString(R.string.error_invalid_license_plate));
                focusView = edt_license_plate;
                cancel = true;
            }else if(edt_license_province.getText().toString().length()==0) {
                edt_license_province.setError(getString(R.string.error_invalid_license_province));
                focusView = edt_license_province;
                cancel = true;
            }else if(edt_sum1.getText().toString().length()==0) {
                edt_sum1.setError(getString(R.string.error_invalid_car_wheels));
                focusView = edt_sum1;
                cancel = true;
            }else if(edt_sum2.getText().toString().length()==0) {
                edt_sum2.setError(getString(R.string.error_invalid_car_tons));
                focusView = edt_sum2;
                cancel = true;
            }
            if(cancel) {
                focusView.requestFocus();
            }else {
                if(isDateBirthDay(txt_birth.getText().toString().trim())){
                    if(positionOptionCar == 2){
                        if(positionCarTow ==0){
                            flagSelectCarTow = false;
                        }else {
                            flagSelectCarTow = true;
                        }
                    }
                    if(flagSelectCarTow){
                        listMembers.get(0).setFirst_name(edt_first_name.getText().toString());
                        listMembers.get(0).setLast_name(edt_last_name.getText().toString());
                        listMembers.get(0).setEmail(edt_mail.getText().toString());
                        listMembers.get(0).setBirth_date(txt_birth.getText().toString().trim().length()==0 ? "" : dateController.convertDateFormat1To2(txt_birth.getText().toString().trim()));
                        listMembers.get(0).setTel(edt_tel.getText().toString());
                        if(positionSex == 0){
                            listMembers.get(0).setSex("M");
                        }else if(positionSex == 1){
                            listMembers.get(0).setSex("W");
                        }

                        carDetail.setCar_brand(edt_car_brand.getText().toString());
                        carDetail.setCar_model(edt_car_model.getText().toString());
                        carDetail.setLicense_plate(edt_license_plate.getText().toString());
                        carDetail.setProvince(edt_license_province.getText().toString());
                        carDetail.setCar_wheels(Integer.parseInt(edt_sum1.getText().toString()));
                        carDetail.setCar_tow(Integer.parseInt(edt_sum2.getText().toString()));
                        carDetail.setGroup_id(positionOptionCar + 1);
                        if(positionOptionCar == 2){
                            carDetail.setCar_tow(positionCarTow);
                        }else {
                            carDetail.setCar_tow(0);
                        }
                        if(chk_option.isChecked()){
                            carDetail.setOption_trailer(1);
                        }else {
                            carDetail.setOption_trailer(0);
                        }
                        if(chk_weight.isChecked()){
                            carDetail.setSum_weight(1);
                        }else {
                            carDetail.setSum_weight(0);
                        }
                        if(!defaultPicProfile){
                            String new_path = GlobalVar.saveImage(bitmapProfileDefault,pathDefault);
                            listMembers.get(0).setName_pic_path(GlobalVar.findPicName(new_path));
//                            final String result = download_image.SendImageNode(DriverProfileActivity.this, new_path);
//                            Log.i("Upload Image",result);
                        }
                        flagEdit = false;
                        save.setVisible(false);
                        edit.setVisible(true);
                        setDefault();
                        List<TblCarDetail> car = new ArrayList<TblCarDetail>();
                        car.add(carDetail);
                        listMembers.get(0).setCar_detail(car);
                        apiService = new ApiService();
                        driverProfilePresenter = new DriverProfilePresenter(DriverProfileActivity.this,apiService);
                        driverProfilePresenter.updateProfile();
                    }else {
                        dialog.dialogNolmal(DriverProfileActivity.this, getResources().getString(R.string.txtWarning), getResources().getString(R.string.error_invalid_total_tow));
                    }

                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean isDateBirthDay(String date){
        boolean success = true;
        if(date.length()==0) {
            dialog.dialogNolmal(DriverProfileActivity.this, getResources().getString(R.string.txtWarning), getResources().getString(R.string.error_invalid_birth_date));
            success = false;
        }
        return success;
    }

    public void setDefault(){
        try {
            edt_first_name.setEnabled(false);
            edt_last_name.setEnabled(false);
            edt_mail.setEnabled(false);
            txt_birth.setEnabled(false);
            edt_tel.setEnabled(false);

            ////car
            edt_car_brand.setEnabled(false);
            edt_car_model.setEnabled(false);
            edt_license_plate.setEnabled(false);
            edt_license_province.setEnabled(false);
            edt_sum1.setEnabled(false);
            edt_sum2.setEnabled(false);
            chk_weight.setEnabled(false);
            chk_option.setEnabled(false);
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

                pathDefault = GlobalVar.getPath(DriverProfileActivity.this, uriProfile);
            }

        }
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
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Warning");
                alertDialogBuilder.setMessage("คุณต้องการบันทึกข้อมูลหรือไม่");
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                setConfirmSave();

                            }
                        });
                alertDialogBuilder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

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

    private void setDateBirthDay() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {

                String aa = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                txt_birth.setText(dateController.convertDateFormat2To1(aa));
                txt_birth.setTextColor(Color.BLACK);
            }
        }, GlobalVar.mYear, GlobalVar.mMonth, GlobalVar.mDay);
        datePickerDialog.show();
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
        Intent i = new Intent(DriverProfileActivity.this,DriverMainActivity2.class);
        startActivity(i);
        finish();
    }
}
