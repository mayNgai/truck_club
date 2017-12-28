package com.dtc.sevice.truckclub.view.user.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dtc.sevice.truckclub.R;
import com.dtc.sevice.truckclub.view.user.activity.UserMainActivity;
import com.dtc.sevice.truckclub.view.user.activity.UserMainActivity2;

/**
 * Created by May on 10/3/2017.
 */

public class UserBookFragmentFirst extends Fragment {
    private Toolbar toolbar;
    private View rootView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_user_book, container, false);
        init();
        return rootView;
    }
    private void init(){
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        toolbar.setTitle("Booking");
        toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getActivity().onBackPressed();
                Intent i = new Intent(getActivity(), UserMainActivity2.class);
                getActivity().startActivity(i);
                getActivity().finish();
            }
        });
    }
}
