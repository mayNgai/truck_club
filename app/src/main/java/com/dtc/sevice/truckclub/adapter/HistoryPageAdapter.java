package com.dtc.sevice.truckclub.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dtc.sevice.truckclub.R;
import com.dtc.sevice.truckclub.model.TblTask;
import com.dtc.sevice.truckclub.until.DateController;
import com.dtc.sevice.truckclub.until.DialogController;

import java.util.List;

/**
 * Created by May on 10/24/2017.
 */

public class HistoryPageAdapter extends RecyclerView.Adapter<HistoryPageAdapter.ViewHolder>{
    private static Context mcontext;
    private List<TblTask> arrayList;
    private DateController dateController;
    private DialogController dialogController;
    public HistoryPageAdapter(Context context, List<TblTask> _arrayList){
        this.arrayList = _arrayList;
        this.mcontext = context;
        dateController = new DateController();
        dialogController = new DialogController();
    }

    @Override
    public void onBindViewHolder(final HistoryPageAdapter.ViewHolder holder, final int i) {
        holder.linear_wait.setVisibility(View.GONE);
        holder.txt_status.setVisibility(View.VISIBLE);
        holder.txt_date_start.setText(dateController.convertDateFormat2To1(arrayList.get(i).getStart_date()));
        holder.txt_time_start.setText(arrayList.get(i).getStart_date().substring(10,16));
        holder.txt_status.setText(arrayList.get(i).getComplete_status());
        //holder.txt_driver_wait.setText(String.valueOf(arrayList.get(i).getMember().size()));
        holder.txt_start_position.setText(arrayList.get(i).getStart_location());
        holder.txt_des_position.setText(arrayList.get(i).getDest_location());
        holder.linear_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDialogBottom(view,i);
            }
        });

    }

    private void setDialogBottom(View v,final int pos){
        try {
            LayoutInflater inflater = (LayoutInflater)v.getContext().getSystemService(v.getContext().LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate (R.layout.dialog_driver_offer, null);
            ImageView img_cancel = (ImageView)view.findViewById( R.id.img_cancel);
            TextView txt_id = (TextView)view.findViewById( R.id.txt_id);
            TextView txt_start_date = (TextView)view.findViewById( R.id.txt_start_date);
            TextView txt_end_date = (TextView)view.findViewById( R.id.txt_end_date);
            TextView txt_start_time = (TextView)view.findViewById( R.id.txt_start_time);
            TextView txt_end_time = (TextView)view.findViewById( R.id.txt_end_time);
            TextView txt_name = (TextView)view.findViewById( R.id.txt_driver_name);
            TextView txt_surname = (TextView)view.findViewById( R.id.txt_driver_surname);
            TextView txt_start_position = (TextView)view.findViewById( R.id.txt_start_position);
            TextView txt_des_position = (TextView)view.findViewById( R.id.txt_des_position);
            TextView txt_type_car = (TextView)view.findViewById( R.id.txt_type_car);
            final EditText edt_price = (EditText)view.findViewById( R.id.edt_price);
            ImageButton img_ok = (ImageButton)view.findViewById( R.id.img_ok);
            Button btn_travel = (Button)view.findViewById( R.id.btn_travel);
            RatingBar rating = (RatingBar)view.findViewById( R.id.rating);
            final Dialog mBottomSheetDialog = new Dialog(v.getContext(),R.style.MaterialDialogSheet);
            mBottomSheetDialog.setContentView (view);
            mBottomSheetDialog.setCancelable (true);
            mBottomSheetDialog.getWindow ().setLayout (LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            mBottomSheetDialog.getWindow ().setGravity (Gravity.BOTTOM);
            mBottomSheetDialog.show ();
            txt_id.setText(arrayList.get(pos).getId());
            txt_start_date.setText(dateController.convertDateFormat2To1(arrayList.get(pos).getStart_date()));
            txt_end_date.setText(dateController.convertDateFormat2To1(arrayList.get(pos).getEnd_date()));
            txt_start_time.setText(arrayList.get(pos).getEnd_date().substring(10,16));
            txt_end_time.setText(arrayList.get(pos).getEnd_date().substring(10,16));
            txt_start_position.setText(arrayList.get(pos).getStart_location());
            txt_des_position.setText(arrayList.get(pos).getDest_location());
            txt_type_car.setText(arrayList.get(pos).getName_group());
            edt_price.setText(arrayList.get(pos).getPrice() == null ? "" : arrayList.get(pos).getPrice());
            if(arrayList.get(pos).getMember().size()>0){
                txt_name.setText(arrayList.get(pos).getMember().get(0).getFirst_name());
                txt_surname.setText(arrayList.get(pos).getMember().get(0).getLast_name());
            }else {
                txt_name.setText("");
                txt_surname.setText("");
            }
            edt_price.setEnabled(false);
            img_ok.setVisibility(View.GONE);
            btn_travel.setVisibility(View.GONE);

            img_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBottomSheetDialog.dismiss();
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public HistoryPageAdapter.ViewHolder onCreateViewHolder(ViewGroup vGroup, int i) {

        View view = LayoutInflater.from(vGroup.getContext()).inflate(R.layout.list_wait_accept, vGroup, false);
        return new HistoryPageAdapter.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_date_start,txt_time_start,txt_driver_wait,txt_start_position,txt_des_position,txt_status;
        private LinearLayout linear_list,linear_wait;

        public ViewHolder(View v) {
            super(v);
            txt_date_start = (TextView) v.findViewById(R.id.txt_date_start);
            txt_time_start = (TextView) v.findViewById(R.id.txt_time_start);
            txt_driver_wait = (TextView) v.findViewById(R.id.txt_driver_wait);
            txt_start_position = (TextView) v.findViewById(R.id.txt_start_position);
            txt_des_position = (TextView) v.findViewById(R.id.txt_des_position);
            txt_status = (TextView) v.findViewById(R.id.txt_status);
            linear_list = (LinearLayout) v.findViewById(R.id.linear_list);
            linear_wait = (LinearLayout) v.findViewById(R.id.linear_wait);
        }
    }
}
