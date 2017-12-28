package com.dtc.sevice.truckclub.view.user.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dtc.sevice.truckclub.R;
import com.dtc.sevice.truckclub.view.driver.activity.DriverRegisterActivity;
import com.dtc.sevice.truckclub.view.user.activity.UserBookActivity;

/**
 * Created by May on 10/3/2017.
 */

public class UserBookFragmentSecond extends Fragment {
    private Toolbar toolbar;
    private View rootView;
    private UserBookActivity mView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_user_book_2, container, false);
        init();
        return rootView;
    }

    private void init(){
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.txtCarDetail);
        mView = new UserBookActivity();
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getActivity().onBackPressed();
                mView.setPre(1);
            }
        });

    }
}
