package com.dtc.sevice.truckclub.view.driver.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dtc.sevice.truckclub.R;
import com.dtc.sevice.truckclub.adapter.AddImageAdapter;
import com.dtc.sevice.truckclub.adapter.ItemOffsetDecoration;
import com.dtc.sevice.truckclub.helper.GlobalVar;
import com.dtc.sevice.truckclub.model.TblPicture;
import com.dtc.sevice.truckclub.service.ApiService;
import com.dtc.sevice.truckclub.until.ApplicationController;
import com.dtc.sevice.truckclub.until.Updown_Image;
import com.dtc.sevice.truckclub.view.LoginSecondActivity;
import com.dtc.sevice.truckclub.view.driver.activity.DriverRegisterActivity;
import com.dtc.sevice.truckclub.view.user.activity.UserRegisterActivity;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by May on 9/21/2017.
 */

public class RegisterDriverFragmentThird extends Fragment implements View.OnClickListener{
    private static DriverRegisterActivity mView;
    private ImageView img_profile;
    private static RecyclerView mRecyclerView;
    private Button btn_confirm_register;
    private View rootView;
    private Toolbar toolbar;
    private static AddImageAdapter mAdapter;
    public static List<TblPicture> imagePaths;
    private int PROFILE_PIC = 1;
    private Uri uriProfile;
    private static Bitmap bitmapProfileDefault;
    private static boolean defaultPicProfile = true;
    private ApplicationController _appController;
    public static ArrayList<String> selectedItems;
    private Updown_Image download_image;
    private static boolean flag_select_profile= true;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_register_driver_third, container, false);
        init();
        setImageFromFacebook();
        return rootView;
    }

    private void init(){
        try {
            download_image = new Updown_Image();
            mView = new DriverRegisterActivity();
            imagePaths = new ArrayList<TblPicture>();
            _appController = new ApplicationController();
            img_profile = (ImageView)rootView.findViewById(R.id.img_profile);
            mRecyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_view);
            btn_confirm_register = (Button)rootView.findViewById(R.id.btn_confirm_register);
            toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
            toolbar.setTitle(R.string.txtPicture);
            toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_white_24dp);
            btn_confirm_register.setOnClickListener(this);
            img_profile.setOnClickListener(this);
            mRecyclerView.setOnClickListener(this);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //getActivity().onBackPressed();
                    mView.setPre(1);
                }
            });
            mRecyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 3);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.addItemDecoration(new ItemOffsetDecoration(getContext(), R.dimen.item_offset));
            TblPicture pics = new TblPicture();
            pics.setGuid(UUID.randomUUID().toString());
            pics.setPath("");
            imagePaths.add(pics);
            mAdapter = new AddImageAdapter(getContext(),imagePaths);
            mRecyclerView.setAdapter(mAdapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setImageFromFacebook(){
        try {
            mView = new DriverRegisterActivity();
            if(mView.member != null){
                if(mView.member.getMember_type()==1){
                    flag_select_profile = false;
                    defaultPicProfile = false;
                    Picasso.with(getActivity())
                            .load(ApiService.url_facebook + mView.member.getFace_book_id() + ApiService.pic_facebook)
                            .into(img_profile);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm_register:
                mView.setComplete();
                break;
            case R.id.img_profile:
                if(flag_select_profile)
                    setSelectImage();
                break;

        }

    }

    public void getSelectImageMultiple(ArrayList<String> Items,boolean flagRemove){
        try {
            //imagePaths = new ArrayList<TblPicture>();
            if(flagRemove){
                if(imagePaths != null || imagePaths.size()>0)
                    imagePaths.remove(mAdapter.getItemCount()-1);
            }

            selectedItems = Items;
            for(String s :selectedItems){
                TblPicture pics = new TblPicture();
                pics.setGuid(UUID.randomUUID().toString());
                pics.setPath(s);
                imagePaths.add(pics);
            }
            setUpdateAdapter();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void removePicture(int i){
        try {
            if(imagePaths != null || imagePaths.size()>0)
                imagePaths.remove(i);
            for(TblPicture p : imagePaths){
                if(p.getPath().length()==0){
                    imagePaths.remove(p);
                    break;
                }
            }
            setUpdateAdapter();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setUpdateAdapter(){
        try {
            if(imagePaths.size()< 5){
                TblPicture pics = new TblPicture();
                pics.setGuid(UUID.randomUUID().toString());
                pics.setPath("");
                imagePaths.add(pics);
            }
            mAdapter = new AddImageAdapter(getContext(),imagePaths);
            mRecyclerView.setAdapter(mAdapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void setSelectImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent
                , "Select Picture"), PROFILE_PIC);
    }

    private static String pathDefault="";
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        InputStream imageStream = null;
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == PROFILE_PIC) {
                uriProfile = data.getData();
                try {
                    imageStream = getActivity().getContentResolver().openInputStream(uriProfile);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                bitmapProfileDefault = _appController.getResizedBitmap(BitmapFactory.decodeStream(imageStream), 450 , 450);
                img_profile.setImageBitmap(bitmapProfileDefault);

                defaultPicProfile = false;

                pathDefault = GlobalVar.getPath(getContext(), uriProfile);
            }

        }
    }

    public boolean setCompleteThird(Activity _activity){
        boolean success = false;
        try {
            if(defaultPicProfile){
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(_activity);
                alertDialogBuilder.setTitle(_activity.getResources().getString(R.string.txtWarning));
                alertDialogBuilder.setMessage(_activity.getResources().getString(R.string.error_invalid_pic_profile));
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }else if(imagePaths.size()<5){
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(_activity);
                alertDialogBuilder.setTitle(_activity.getResources().getString(R.string.txtWarning));
                alertDialogBuilder.setMessage(_activity.getResources().getString(R.string.error_invalid_pic_car));
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }else {
                success = true;
                download_image = new Updown_Image();
                if(bitmapProfileDefault!=null){
                    String new_path = GlobalVar.saveImage(bitmapProfileDefault,pathDefault);
                    mView.member.setName_pic_path(GlobalVar.findPicName(new_path));
                    final String result = download_image.SendImageNode(_activity, new_path);
                    Log.i("Upload Image",result);
                }

                for(int i=0;i<imagePaths.size();i++){
                    if(imagePaths.get(i).getPath() == null || imagePaths.get(i).getPath().length()==0){
                        imagePaths.remove(i);
                    }else {
                        imagePaths.get(i).setName(GlobalVar.findPicName(imagePaths.get(i).getPath()));
                        final String result_img = download_image.SendImageNode(_activity, imagePaths.get(i).getPath());
                        Log.i("Upload Image",result_img);
                    }
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return success;
    }

    public void closeActivity(){
        try {
            Intent intent = new Intent(mView,LoginSecondActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            mView.finish();
            mView.startActivity(intent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
