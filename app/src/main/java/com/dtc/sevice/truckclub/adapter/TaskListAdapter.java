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
import com.dtc.sevice.truckclub.view.driver.activity.DriverBookingActivity;
import com.dtc.sevice.truckclub.view.driver.activity.DriverMainActivity2;

import java.util.List;

/**
 * Created by May on 10/10/2017.
 */

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder>{
    private static Context mcontext;
    private List<TblTask> arrayList;
    private DateController dateController;
    private DialogController dialogController;
    private static DriverBookingActivity mView;
    private static DriverMainActivity2 mView2;
    private int select_position;

    public TaskListAdapter(Context context, List<TblTask> _arrayList,int _select_position){
        this.arrayList = _arrayList;
        this.mcontext = context;
        this.select_position = _select_position;
        dateController = new DateController();
        dialogController = new DialogController();
        mView = new DriverBookingActivity();
    }

    @Override
    public void onBindViewHolder(final TaskListAdapter.ViewHolder holder, final int i) {
        holder.txt_start_position.setText(arrayList.get(i).getStart_location());
        holder.txt_des_position.setText(arrayList.get(i).getDest_location());
        holder.txt_date_start.setText(dateController.convertDateFormat2To1(arrayList.get(i).getStart_date()));
        holder.linear_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDialogBottom(view,i);
            }
        });

    }
    private static Dialog mBottomSheetDialog;
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
            mBottomSheetDialog = new Dialog (v.getContext(),R.style.MaterialDialogSheet);
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
            txt_name.setText(arrayList.get(pos).getMember().get(0).getFirst_name());
            txt_surname.setText(arrayList.get(pos).getMember().get(0).getLast_name());
            txt_start_position.setText(arrayList.get(pos).getStart_location());
            txt_des_position.setText(arrayList.get(pos).getDest_location());
            txt_type_car.setText(arrayList.get(pos).getName_group());
            if(select_position == 1 ||select_position == 2){
                edt_price.setText(arrayList.get(pos).getPrice_offer());
                edt_price.setEnabled(false);
                img_ok.setVisibility(View.GONE);
            }else if(select_position == 3){
                edt_price.setText(arrayList.get(pos).getPrice_offer());
                edt_price.setEnabled(false);
                img_ok.setVisibility(View.GONE);
                btn_travel.setVisibility(View.VISIBLE);
            }
            img_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBottomSheetDialog.dismiss();
                }
            });
            img_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(edt_price.getText().toString().length()>0){
                        if(arrayList.get(pos).getService_type().equalsIgnoreCase("Booking")){
                            mView = new DriverBookingActivity();
                            mView.sentOfferPrice(Integer.parseInt(arrayList.get(pos).getId()),Integer.parseInt(edt_price.getText().toString()));
                        }else {
                            mView2 = new DriverMainActivity2();
                            mView2.sentOfferPrice(Integer.parseInt(arrayList.get(pos).getId()),Integer.parseInt(edt_price.getText().toString()));
                            mBottomSheetDialog.dismiss();
                        }

                        arrayList.remove(pos);
                        notifyDataSetChanged();
                    }else {
                        dialogController.dialogNolmal(mcontext,"Warnning","กรุณาใส่ราคาก่อน..");
                    }
                }
            });

            btn_travel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mView = new DriverBookingActivity();
                    mView.sentGoing(arrayList.get(pos));
                    arrayList.remove(pos);
                    notifyDataSetChanged();
                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void closeDriverOffer(){
        try {
            mBottomSheetDialog.dismiss();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public TaskListAdapter.ViewHolder onCreateViewHolder(ViewGroup vGroup, int i) {

        View view = LayoutInflater.from(vGroup.getContext()).inflate(R.layout.list_driver_task, vGroup, false);
        return new TaskListAdapter.ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_start_position,txt_des_position,txt_date_start;
        private LinearLayout linear_list;

        public ViewHolder(View v) {
            super(v);
            txt_start_position = (TextView) v.findViewById(R.id.txt_start_position);
            txt_des_position = (TextView) v.findViewById(R.id.txt_des_position);
            txt_date_start = (TextView) v.findViewById(R.id.txt_date_start);
            linear_list = (LinearLayout) v.findViewById(R.id.linear_list);
        }
    }
}
