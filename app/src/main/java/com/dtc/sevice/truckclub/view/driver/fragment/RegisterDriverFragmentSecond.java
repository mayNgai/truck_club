package com.dtc.sevice.truckclub.view.driver.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.dtc.sevice.truckclub.R;
import com.dtc.sevice.truckclub.adapter.SelectProviderAdapter;
import com.dtc.sevice.truckclub.model.TblCarDetail;
import com.dtc.sevice.truckclub.model.TblProvince;
import com.dtc.sevice.truckclub.view.driver.activity.DriverRegisterActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by May on 9/21/2017.
 */

public class RegisterDriverFragmentSecond extends Fragment {
    private DriverRegisterActivity mView;
    public static EditText edt_car_brand,edt_car_model,edt_license_plate,edt_license_province,edt_sum1,edt_sum2;
    private static Spinner spn_car_group,spn_sum1,spn_tow,spn_weight,spn_geo,spn_province;
    private static CheckBox chk_weight,chk_option;
    private LinearLayout linear_option,linear_tow,linear_group_option;
    private View rootView;
    private Toolbar toolbar;
    private String[] arrCarGroup,arrGeo,arrProvince,arrCarTow;
    private ArrayList<String> arrPro = new ArrayList<String>();
    private static List<TblProvince> provinces;
    public static boolean flagSelectOption = false,flagSelectCarTow = false;
    public static int positionOptionCar = 0,positionCarTow = 0;
    public static boolean weight = false ,option = false;
    public static TblCarDetail carDetail;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_register_driver_second, container, false);
        init();
        return rootView;
    }

    private void init(){
        try {
            edt_car_brand = (EditText)rootView.findViewById(R.id.edt_car_brand);
            edt_car_model = (EditText)rootView.findViewById(R.id.edt_car_model);
            edt_license_plate = (EditText)rootView.findViewById(R.id.edt_license_plate);
            edt_license_province = (EditText)rootView.findViewById(R.id.edt_license_province);
            edt_sum1 = (EditText)rootView.findViewById(R.id.edt_sum1);
            edt_sum2 = (EditText)rootView.findViewById(R.id.edt_sum2);
            spn_car_group = (Spinner)rootView.findViewById(R.id.spn_car_group);
            spn_sum1 = (Spinner)rootView.findViewById(R.id.spn_sum1);
            spn_tow = (Spinner)rootView.findViewById(R.id.spn_tow);
            spn_weight = (Spinner)rootView.findViewById(R.id.spn_weight);
            spn_geo = (Spinner)rootView.findViewById(R.id.spn_geo);
            spn_province = (Spinner)rootView.findViewById(R.id.spn_province);
            chk_weight = (CheckBox)rootView.findViewById(R.id.chk_weight);
            chk_option = (CheckBox)rootView.findViewById(R.id.chk_option);
            linear_option = (LinearLayout)rootView.findViewById(R.id.linear_option);
            linear_tow = (LinearLayout)rootView.findViewById(R.id.linear_tow);
            linear_group_option = (LinearLayout)rootView.findViewById(R.id.linear_group_option);
            toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
            toolbar.setTitle(R.string.txtCarDetail);
            mView = new DriverRegisterActivity();
            toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_white_24dp);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //getActivity().onBackPressed();
                    mView.setPre(1);
                }
            });
            setSpinnerCarGroup();
            setSpinnerCarTow();
//            setSpinnerGeo();
//            setSpinnerProvinceDefualt();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setSpinnerCarGroup(){
        try {
            arrCarGroup = getResources().getStringArray(R.array.car_group);
            ArrayAdapter<String> groupArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, arrCarGroup);
            spn_car_group.setAdapter(groupArrayAdapter);
            spn_car_group.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    positionOptionCar = i;
                    setOption(i);
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
            flagSelectOption = true;
            linear_group_option.setVisibility(View.VISIBLE);
            if(i == 0){
                flagSelectOption = false;
                linear_group_option.setVisibility(View.GONE);
            }else if(i == 1){
                linear_tow.setVisibility(View.GONE);
                chk_option.setVisibility(View.VISIBLE);
            }else if(i == 3){
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

    public boolean setCompletePageSecond(Activity activity){
        boolean success = false;
        try {
            boolean cancel = false;
            View focusView = null;
            if(edt_car_brand.getText().toString().trim().length()==0){
                edt_car_brand.setError(activity.getResources().getString(R.string.error_invalid_car_brands));
                focusView = edt_car_brand;
                cancel = true;
            }else if(edt_car_model.getText().toString().trim().length()==0){
                edt_car_model.setError(activity.getResources().getString(R.string.error_invalid_car_model));
                focusView = edt_car_model;
                cancel = true;
            }else if(edt_license_plate.getText().toString().trim().length()==0){
                edt_license_plate.setError(activity.getResources().getString(R.string.error_invalid_license_plate));
                focusView = edt_license_plate;
                cancel = true;
            }else if(edt_license_province.getText().toString().trim().length()==0){
                edt_license_province.setError(activity.getResources().getString(R.string.error_invalid_license_province));
                focusView = edt_license_province;
                cancel = true;

            }

            if(cancel) {
                focusView.requestFocus();
            }else {
                success = true;
                carDetail = new TblCarDetail();
                carDetail.setCar_brand(edt_car_brand.getText().toString());
                carDetail.setCar_model(edt_car_model.getText().toString());
                carDetail.setLicense_plate(edt_license_plate.getText().toString());
                carDetail.setProvince(edt_license_province.getText().toString());

            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return success;
    }

    public boolean setOptionCompletePageSecond(Activity activity){
        boolean success = false;
        try {
            boolean cancel = false;
            View focusView = null;
            if(edt_sum1.getText().toString().trim().length()==0){
                edt_sum1.setError(activity.getResources().getString(R.string.error_invalid_car_wheels));
                focusView = edt_sum1;
                cancel = true;
            }else if(edt_sum2.getText().toString().trim().length()==0){
                edt_sum2.setError(activity.getResources().getString(R.string.error_invalid_car_tons));
                focusView = edt_sum2;
                cancel = true;
            }

            if(cancel) {
                focusView.requestFocus();
            }else {
                success = true;
                carDetail.setGroup_id(positionOptionCar);
                carDetail.setCar_wheels(Integer.parseInt(edt_sum1.getText().toString()));
                carDetail.setCar_tons(Integer.parseInt(edt_sum2.getText().toString()));
                carDetail.setCar_tow(positionCarTow);
                if(chk_option.isChecked())
                    carDetail.setOption_trailer(1);
                else
                    carDetail.setOption_trailer(0);
                if(chk_weight.isChecked())
                    carDetail.setSum_weight(1);
                else
                    carDetail.setSum_weight(0);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return success;
    }

    private void setSpinnerCarTow(){
        try {
            arrCarTow = getResources().getStringArray(R.array.car_tow);
            ArrayAdapter<String> towArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, arrCarTow);
            spn_tow.setAdapter(towArrayAdapter);
            spn_tow.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    positionCarTow = i;
                    flagSelectCarTow = true;
                    if(positionCarTow == 0){
                        flagSelectCarTow = false;
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

    ///////////////////// province ////////////

    private void setSpinnerGeo(){
        try {
            arrGeo = getResources().getStringArray(R.array.geo);
            ArrayAdapter<String> geoArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, arrGeo);
            spn_geo.setAdapter(geoArrayAdapter);
            spn_geo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    setSpinnerProvince(i);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setSpinnerProvinceDefualt(){
        try {
            arrProvince = getResources().getStringArray(R.array.province);
            provinceArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, arrProvince);
            spn_province.setAdapter(provinceArrayAdapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private ArrayAdapter<String> provinceArrayAdapter;
    private void setSpinnerProvince(int i){
        try {
            if(i>0){
                arrPro = new ArrayList<String>();
                TblProvince pp = new TblProvince();
                boolean flagadd = false;
                List<TblProvince> arrlist = new ArrayList<TblProvince>();
                for(TblProvince p : provinces){
                    if(i==7){
                        if(!flagadd){
                            pp.setProvince_name("กรุณาเลือกจังหวัด");
                            arrlist.add(0,pp);
                            flagadd = true;
                        }
                        arrPro.add(p.getProvince_name());
                        arrlist.add(p);
                    }else {
                        if(!flagadd){
                            pp.setProvince_name("กรุณาเลือกจังหวัด");
                            arrlist.add(0,pp);
                            flagadd = true;
                        }

                        if(p.getGeo_id().equalsIgnoreCase(String.valueOf(i))){
                            arrPro.add(p.getProvince_name());
                            arrlist.add(p);
                        }
                    }

                }

                SelectProviderAdapter myAdapter = new SelectProviderAdapter(getActivity(), 0,arrlist);
                spn_province.setAdapter(myAdapter);
            }else {
                setSpinnerProvinceDefualt();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addProvince(List<TblProvince> list){
        try {
            provinces = new ArrayList<TblProvince>();
            provinces.addAll(list);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    ///////////////////////////////////////////////////////////////////
}
