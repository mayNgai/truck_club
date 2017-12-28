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

import java.util.List;

/**
 * Created by May on 10/12/2017.
 */

public class AboutAdapter extends RecyclerView.Adapter<AboutAdapter.ViewHolder>{
    private Context mcontext;
    private List<String> arrayList;
    public AboutAdapter(Context context, List<String> _arrayList){
        this.arrayList = _arrayList;
        this.mcontext = context;
    }

    @Override
    public void onBindViewHolder(final AboutAdapter.ViewHolder holder, final int i) {
        holder.txt_name.setText(arrayList.get(i));
        if(i==0){
            holder.img_icon.setImageResource(R.drawable.ic_explore_black_24dp);
        }else if(i==1){
            holder.img_icon.setImageResource(R.drawable.ic_language_black_24dp);
        }
        holder.linear_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(i ==0){
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mcontext.getResources().getString(R.string.playStore)));
                    mcontext.startActivity(browserIntent);
                }else if(i==1){
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mcontext.getResources().getString(R.string.urlwwwdtc)));
                    mcontext.startActivity(browserIntent);
                }
            }
        });
    }

    @Override
    public AboutAdapter.ViewHolder onCreateViewHolder(ViewGroup vGroup, int i) {

        View view = LayoutInflater.from(vGroup.getContext()).inflate(R.layout.list_about, vGroup, false);
        return new AboutAdapter.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_name;
        private LinearLayout linear_list;
        private ImageView img_icon;

        public ViewHolder(View v) {
            super(v);
            txt_name = (TextView) v.findViewById(R.id.txt_name);
            linear_list = (LinearLayout) v.findViewById(R.id.linear_list);
            img_icon = (ImageView)v.findViewById(R.id.img_icon);
        }
    }
}
