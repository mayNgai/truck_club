package com.dtc.sevice.truckclub.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dtc.sevice.truckclub.R;
import com.dtc.sevice.truckclub.fragment.SettingFragmentFirst;
import com.dtc.sevice.truckclub.model.TblSetting;

import java.util.List;

/**
 * Created by May on 10/31/2017.
 */

public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.ViewHolder>{
    private Context mcontext;
    private List<TblSetting> arrayList;
    private SettingFragmentFirst fragmentFirst;
    public SettingAdapter(Context context, List<TblSetting> _arrayList){
        this.arrayList = _arrayList;
        this.mcontext = context;
        fragmentFirst = new SettingFragmentFirst();
    }

    @Override
    public void onBindViewHolder(final SettingAdapter.ViewHolder holder, final int i) {
        holder.txt_name.setText(arrayList.get(i).getName());
        holder.txt_value1.setText(arrayList.get(i).getValue1());
        holder.txt_value2.setText(arrayList.get(i).getValue2());
        if(i==0){
            holder.img_icon.setImageResource(R.drawable.ic_language_black_24dp);
        }else if(i == 1){
            holder.img_icon.setImageResource(R.drawable.ic_query_builder_black_24dp);
        }
        holder.linear_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(i ==0){

                }else if(i == 1){
                    fragmentFirst.showDialog(view,arrayList.get(i));
                }
            }
        });

    }

    @Override
    public SettingAdapter.ViewHolder onCreateViewHolder(ViewGroup vGroup, int i) {

        View view = LayoutInflater.from(vGroup.getContext()).inflate(R.layout.list_setting, vGroup, false);
        return new SettingAdapter.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_name,txt_value1,txt_value2;
        private LinearLayout linear_list;
        private ImageView img_icon;

        public ViewHolder(View v) {
            super(v);
            txt_name = (TextView) v.findViewById(R.id.txt_name);
            txt_value1 = (TextView) v.findViewById(R.id.txt_value1);
            txt_value2 = (TextView) v.findViewById(R.id.txt_value2);
            linear_list = (LinearLayout) v.findViewById(R.id.linear_list);
            img_icon = (ImageView)v.findViewById(R.id.img_icon);
        }
    }
}
