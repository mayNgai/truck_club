package com.dtc.sevice.truckclub.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.dtc.sevice.truckclub.R;
import com.dtc.sevice.truckclub.model.TblCarGroup;
import com.dtc.sevice.truckclub.view.user.activity.UserMainActivity2;

import java.util.List;

/**
 * Created by May on 10/4/2017.
 */

public class TypeCarAdapter extends RecyclerView.Adapter<TypeCarAdapter.ViewHolder>{
    private static Context mcontext;
    private List<TblCarGroup> arrayList;
    private UserMainActivity2 mView;

    public TypeCarAdapter(Context context,List<TblCarGroup> _arrayList){
        this.arrayList = _arrayList;
        this.mcontext = context;
        mView = new UserMainActivity2();
    }

    @Override
    public void onBindViewHolder(final TypeCarAdapter.ViewHolder holder, final int i) {

        holder.txt_name.setText(arrayList.get(i).getName_group());
        holder.linear_background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mView.setSelectCar(i);
            }
        });
        if(arrayList.get(i).getIsSelect() == 1){
            holder.linear_background.setBackgroundResource(R.color.colorPrimary);
        }else {
            holder.linear_background.setBackgroundResource(R.color.WhiteSmoke);
        }
    }

    @Override
    public TypeCarAdapter.ViewHolder onCreateViewHolder(ViewGroup vGroup, int i) {

        View view = LayoutInflater.from(vGroup.getContext()).inflate(R.layout.list_type_car, vGroup, false);
        return new TypeCarAdapter.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_name;
        private LinearLayout linear_background;

        public ViewHolder(View v) {
            super(v);
            txt_name = (TextView) v.findViewById(R.id.txt_name);
            linear_background = (LinearLayout)v.findViewById(R.id.linear_background);
        }
    }
}
