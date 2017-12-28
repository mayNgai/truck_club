package com.dtc.sevice.truckclub.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.LayoutRes;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dtc.sevice.truckclub.R;
import com.dtc.sevice.truckclub.adapter.AboutAdapter;
import com.dtc.sevice.truckclub.adapter.TaskListWaitAdapter;
import com.dtc.sevice.truckclub.adapter.UserBookingMathedAdapter;
import com.dtc.sevice.truckclub.helper.GlobalVar;
import com.dtc.sevice.truckclub.model.TblMember;
import com.dtc.sevice.truckclub.model.TblTask;
import com.dtc.sevice.truckclub.presenters.BasePresenter;
import com.dtc.sevice.truckclub.service.ApiService;
import com.dtc.sevice.truckclub.until.ApplicationController;
import com.dtc.sevice.truckclub.until.DialogController;
import com.dtc.sevice.truckclub.until.TaskController;
import com.dtc.sevice.truckclub.view.driver.activity.DriverBookingActivity;
import com.dtc.sevice.truckclub.view.driver.activity.DriverHistoryActivity;
import com.dtc.sevice.truckclub.view.driver.activity.DriverMainActivity2;
import com.dtc.sevice.truckclub.view.driver.activity.DriverProfileActivity;
import com.dtc.sevice.truckclub.view.user.activity.UserProfileActivity;
import com.facebook.AccessToken;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by May on 9/26/2017.
 */

public class BaseActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener{
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;
    private int selectedNavItemId;
    private static TaskController taskController;
    public static List<TblMember> listMembers;
    public static List<TblTask> tblTasks;
    private Activity _activity;
    private MenuItem status,txt_status,booking;
    private static DriverMainActivity2 driverMainActivity2;
    public static TblTask tblTask;
    private ApiService mForum;
    private BasePresenter basePresenter;
    private DialogController dialogController;
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        drawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout activityContainer = (FrameLayout) drawerLayout.findViewById(R.id.activity_content);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);
        super.setContentView(drawerLayout);
        taskController = new TaskController();
        dialogController = new DialogController();
        _activity = BaseActivity.this;
        driverMainActivity2 = new DriverMainActivity2();
        ApplicationController.setAppActivity(_activity);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        View header=navigationView.getHeaderView(0);
        Menu nav_Menu = navigationView.getMenu();
        getMember();
        loadDataMember();
        if(listMembers != null && listMembers.get(0).getAuthority().equalsIgnoreCase(GlobalVar.chooseServiceDriver)){
            nav_Menu.findItem(R.id.action_wait_accept).setVisible(false);
            nav_Menu.findItem(R.id.action_books).setTitle(_activity.getString(R.string.txt_schedules));
        }else if(listMembers != null && listMembers.get(0).getAuthority().equalsIgnoreCase(GlobalVar.chooseServiceUser)){
            nav_Menu.findItem(R.id.action_books).setVisible(false);
        }
        TextView name = (TextView)header.findViewById(R.id.username);
        ImageView profile_image = (ImageView) header.findViewById(R.id.profile_image);
        name.setText(listMembers.get(0).getFirst_name());
        if(listMembers != null &&listMembers.get(0).getMember_type() == 0){
            Picasso.with(_activity).load(GlobalVar.url_up_pic + listMembers.get(0).getName_pic_path())
                    .placeholder( R.drawable.progress_animation )
                    .fit().centerCrop().error( R.drawable.no_images ).into(profile_image);
        }else if(listMembers != null &&listMembers.get(0).getMember_type() == 1){
            Picasso.with(_activity).load(ApiService.url_facebook + listMembers.get(0).getFace_book_id() + ApiService.pic_facebook)
                    .placeholder( R.drawable.progress_animation )
                    .fit().centerCrop().error( R.drawable.no_images ).into(profile_image);
        }

        if (useToolbar()) {
            setSupportActionBar(toolbar);
        } else {
            toolbar.setVisibility(View.GONE);
        }
        setUpNavView();
    }

    protected void setUpNavView() {
        navigationView.setNavigationItemSelectedListener(this);

        if (useDrawerToggle()) { // use the hamburger menu
            drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                    R.string.drawer_open,
                    R.string.drawer_close);

            drawerLayout.addDrawerListener(drawerToggle);
            drawerToggle.syncState();

        } else if (useToolbar() && getSupportActionBar() != null) {
            // Use home/back button instead
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(getResources()
                    .getDrawable(R.drawable.vans));
        }
    }

    protected boolean useDrawerToggle() {
        return true;
    }

    protected boolean useToolbar() {
        return true;
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        drawerLayout.closeDrawer(GravityCompat.START);
        selectedNavItemId = menuItem.getItemId();

        return onOptionsItemSelected(menuItem);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        status = menu.findItem(R.id.status);
        txt_status = menu.findItem(R.id.txt_status);
        booking = menu.findItem(R.id.booking);
        if(listMembers==null || listMembers.size()==0){
            getMember();
        }
        if(listMembers!=null && listMembers.size()>0){
            if(listMembers.get(0).getAuthority().equalsIgnoreCase(GlobalVar.chooseServiceDriver)){
                status.setVisible(true);
                txt_status.setVisible(true);
                booking.setVisible(false);
                if(listMembers.get(0).getStatus_id()==1){
                    status.setIcon(R.drawable.driver_off);
                    txt_status.setTitle("ไม่พร้อมใช้งาน");

                }else if(listMembers.get(0).getStatus_id()==2){
                    status.setIcon(R.drawable.driver_on);
                    txt_status.setTitle("พร้อมใช้งาน");
                }else {
                    status.setIcon(R.drawable.driver_off);
                    txt_status.setTitle("ไม่พร้อมใช้งาน");
                    status.setEnabled(false);
                    booking.setVisible(true);
                }
            }else {
                txt_status.setVisible(false);
                status.setVisible(false);
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_profile:
                if(listMembers==null || listMembers.size()==0){
                    getMember();
                }
                getMember();
                profilePage();

                return true;
            case R.id.action_books:
                if(listMembers==null || listMembers.size()==0){
                    getMember();
                }
                driverBookingPage();
                return true;
            case R.id.action_wait_accept:
                getTaskWait();
                return true;
            case R.id.action_report:
                //setDialogBottom("Report");

                return true;
            case R.id.action_history:
                historyPage();
                return true;
            case R.id.action_setting:
                Intent intent = new Intent(_activity,SettingActivity.class);
                intent.putExtra("authen",listMembers.get(0).getAuthority());
                startActivity(intent);
                return true;
            case R.id.action_about:
                setDialogAbout("About");
                return true;
            case R.id.status:
                if(listMembers==null || listMembers.size()==0){
                    getMember();
                }
                setStatus();
                return true;
            case R.id.booking:
                if(listMembers==null || listMembers.size()==0){
                    getMember();
                }
                if(listMembers.size()>0) {
                    if (listMembers.get(0).getAuthority().equalsIgnoreCase("User")) {
                        getTaskBooking();
                    } else if (listMembers.get(0).getAuthority().equalsIgnoreCase("Driver")) {
                        getTaskByID();
                    }
                }
                return true;
            case R.id.action_logout:
                if(listMembers==null || listMembers.size()==0){
                    getMember();
                }
                if(listMembers.size()>0){
                    if(listMembers.get(0).getAuthority().equalsIgnoreCase("User")){
                        if(listMembers.get(0).getStatus_id()<=3){
                            if(listMembers.get(0).getMember_type() == 1)
                                AccessToken.setCurrentAccessToken(null);

                            setLogOut();
//                            taskController.deleteMember(listMembers);
//                            Intent i = new Intent(_activity,LoginFirstActivity.class);
//                            startActivity(i);
//                            finish();
                        }else {
                            dialogController.dialogNolmal(this,"Warning","อยู่ระหว่างการใช้งานไม่สามารถออกจากระบบได้");
                        }
                    }else if(listMembers.get(0).getAuthority().equalsIgnoreCase("Driver")){
                        if(listMembers.get(0).getStatus_id()<3){
                            if(listMembers.get(0).getMember_type() == 1)
                                AccessToken.setCurrentAccessToken(null);

                            setLogOut();
//                            taskController.deleteMember(listMembers);
//                            Intent i = new Intent(_activity,LoginFirstActivity.class);
//                            startActivity(i);
//                            finish();
                        }else {
                            dialogController.dialogNolmal(this,"Warning","อยู่ระหว่างการใช้งานไม่สามารถออกจากระบบได้");
                        }
                    }
                }

                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public List<TblMember> getMember(){
        try {
            listMembers = new ArrayList<TblMember>();
            listMembers = taskController.getMember();
        }catch (Exception e){
            e.printStackTrace();
        }
        return listMembers;
    }
    public void loadDataMember(){
        try {
            mForum = new ApiService();
            basePresenter = new BasePresenter(this,mForum);
            basePresenter.loadDataMember();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void getTask(){
        try {
            tblTasks = new ArrayList<TblTask>();
            tblTasks = taskController.getTask();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void profilePage(){
        if(listMembers.get(0).getAuthority().equalsIgnoreCase(_activity.getString(R.string.txtUser))){
            Intent i = new Intent(this , UserProfileActivity.class);
            startActivity(i);
            finish();
        }else if(listMembers.get(0).getAuthority().equalsIgnoreCase(_activity.getString(R.string.txtDriver))){
            Intent i = new Intent(this , DriverProfileActivity.class);
            startActivity(i);
            finish();
        }
    }

    private void driverBookingPage(){
        if(listMembers.get(0).getAuthority().equalsIgnoreCase(_activity.getString(R.string.txtDriver))){
            Intent i = new Intent(this , DriverBookingActivity.class);
            startActivity(i);
        }
    }

    private void historyPage(){
//        if(listMembers.get(0).getAuthority().equalsIgnoreCase(_activity.getString(R.string.txtUser))){
            Intent i = new Intent(this , HistoryActivity.class);
            startActivity(i);
//        }else if(listMembers.get(0).getAuthority().equalsIgnoreCase(_activity.getString(R.string.txtDriver))){
//            Intent i = new Intent(this , DriverHistoryActivity.class);
//            startActivity(i);
//        }
    }
    /////////////////User Task Wait /////////////////
    private void getTaskWait(){
        try {
            tblTask = new TblTask();
            tblTask.setService_type("Booking");
            tblTask.setTask_status(1);
            tblTask.setMember(listMembers);
            mForum = new ApiService();
            basePresenter = new BasePresenter(this,mForum);
            basePresenter.loadTask();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    List<TblTask> listTask = new ArrayList<TblTask>();
    public void updateTaskWait(List<TblTask> tblTasks){
        try {
            listTask = new ArrayList<TblTask>();
            listTask = tblTasks;
            setDialogBottom("Wait accept");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void callServiceUserAccept(int id , TblMember list,String service_type){
        try {
            List<TblMember> tblMembers = new ArrayList<TblMember>();
            tblMembers.add(list);
            TblTask tblTask = new TblTask();
            tblTask.setId(String.valueOf(id));
            tblTask.setTask_status(3);
            tblTask.setService_type(service_type);
            tblTask.setMember(tblMembers);
            mForum = new ApiService();
            basePresenter = new BasePresenter(this,mForum);
            basePresenter.callUpdateTask(tblTask);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void callCancel(int id ){
        try {
            TblTask tblTask = new TblTask();
            tblTask.setId(String.valueOf(id));
            tblTask.setTask_status(-1);
            tblTask.setService_type("Booking");
            tblTask.setMember(listMembers);
            mForum = new ApiService();
            basePresenter = new BasePresenter(this,mForum);
            basePresenter.callUpdateTask(tblTask);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /////////////////User Task Booking /////////////////
    private void getTaskBooking(){
        try {
            tblTask = new TblTask();
            tblTask.setService_type("Booking");
            tblTask.setTask_status(3);
            tblTask.setMember(listMembers);
            mForum = new ApiService();
            basePresenter = new BasePresenter(this,mForum);
            basePresenter.loadTask();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getTaskByID(){
        try {
            tblTask = new TblTask();
            tblTask.setId(listMembers.get(0).getTask_id());
            tblTask.setMember(listMembers);
            mForum = new ApiService();
            basePresenter = new BasePresenter(this,mForum);
            basePresenter.loadTaskByID();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateTaskBooking(List<TblTask> tblTasks){
        try {
            listTask = new ArrayList<TblTask>();
            listTask = tblTasks;
            setDialogBottom("Booking");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setDialogBottom(String txtHead){
        try {
            LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate (R.layout.bottom_dialog_recycle, null);
            TextView txt_head = (TextView)view.findViewById( R.id.txt_head);
            ImageView img_down = (ImageView)view.findViewById( R.id.img_down);
            RecyclerView recycler_view = (RecyclerView)view.findViewById( R.id.recycler_view);
            final Dialog mBottomSheetDialog = new Dialog (this,R.style.MaterialDialogSheet);
            mBottomSheetDialog.setContentView (view);
            mBottomSheetDialog.setCancelable (false);
            recycler_view.setHasFixedSize(true);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
            recycler_view.setLayoutManager(mLayoutManager);
            if(txtHead.equalsIgnoreCase("Wait accept")){
                TaskListWaitAdapter adapter = new TaskListWaitAdapter(BaseActivity.this,listTask);
                recycler_view.setAdapter(adapter);
            }else {
                UserBookingMathedAdapter adapter = new UserBookingMathedAdapter(BaseActivity.this,listTask);
                recycler_view.setAdapter(adapter);
            }

            mBottomSheetDialog.getWindow ().setLayout (LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
            mBottomSheetDialog.getWindow ().setGravity (Gravity.BOTTOM);
            mBottomSheetDialog.show ();
            txt_head.setText(txtHead);
            img_down.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBottomSheetDialog.dismiss();

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    ///////////////////////////////////////////////////////

    private void setDialogAbout(String txtHead){
        try {
            LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate (R.layout.bottom_dialog_recycle, null);
            TextView txt_head = (TextView)view.findViewById( R.id.txt_head);
            ImageView img_down = (ImageView)view.findViewById( R.id.img_down);
            RecyclerView recycler_view = (RecyclerView)view.findViewById( R.id.recycler_view);
            final Dialog mBottomSheetDialog = new Dialog (this,R.style.MaterialDialogSheet);
            mBottomSheetDialog.setContentView (view);
            mBottomSheetDialog.setCancelable (true);
            recycler_view.setHasFixedSize(true);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
            recycler_view.setLayoutManager(mLayoutManager);
            List<String> aboutArray = new ArrayList<String>();
            PackageManager manager = BaseActivity.this.getPackageManager();
            PackageInfo info = null;
            info = manager.getPackageInfo(BaseActivity.this.getPackageName(), 0);
            String version = info.versionName;
            String www = getResources().getString(R.string.wwwdtc);
            aboutArray.add(getResources().getString(R.string.version) + " " + version + " " + getResources().getString(R.string.checkUpdate));
            aboutArray.add(www);
            AboutAdapter adapter = new AboutAdapter(BaseActivity.this,aboutArray);
            recycler_view.setAdapter(adapter);
            mBottomSheetDialog.getWindow ().setLayout (LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
            mBottomSheetDialog.getWindow ().setGravity (Gravity.BOTTOM);
            mBottomSheetDialog.show ();
            txt_head.setText(txtHead);
            img_down.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBottomSheetDialog.dismiss();

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setStatus(){
        try {
            getTask();
            if(tblTasks == null || tblTasks.size()==0){
                if(listMembers.get(0).getStatus_id()==1){
                    listMembers.get(0).setStatus_id(2);
                    listMembers.get(0).setStatus("searching");
                    status.setIcon(R.drawable.driver_on);
                    txt_status.setTitle("พร้อมใช้งาน");
                    taskController.updateMember(listMembers.get(0));
                }else if(listMembers.get(0).getStatus_id()==2){
                    listMembers.get(0).setStatus_id(1);
                    listMembers.get(0).setStatus("log in");
                    status.setIcon(R.drawable.driver_off);
                    txt_status.setTitle("ไม่พร้อมใช้งาน");
                    taskController.updateMember(listMembers.get(0));
                }
                mForum = new ApiService();
                basePresenter = new BasePresenter(this,mForum);
                basePresenter.updateStatus();
                driverMainActivity2.setShowSelectList();
            }else if(tblTasks != null || tblTasks.size()>0){
                if(GlobalVar.checkSchedulesDriver(this,tblTasks)){
                    if(listMembers.get(0).getStatus_id()==1){
                        listMembers.get(0).setStatus_id(2);
                        listMembers.get(0).setStatus("searching");
                        status.setIcon(R.drawable.driver_on);
                        txt_status.setTitle("พร้อมใช้งาน");
                        taskController.updateMember(listMembers.get(0));
                    }else if(listMembers.get(0).getStatus_id()==2){
                        listMembers.get(0).setStatus_id(1);
                        listMembers.get(0).setStatus("log in");
                        status.setIcon(R.drawable.driver_off);
                        txt_status.setTitle("ไม่พร้อมใช้งาน");
                        taskController.updateMember(listMembers.get(0));
                    }
                    mForum = new ApiService();
                    basePresenter = new BasePresenter(this,mForum);
                    basePresenter.updateStatus();
                    driverMainActivity2.setShowSelectList();
                }else {
                    dialogController.dialogNolmal(this,"Warning","มีตารางงานตรงกับวันนี้");
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setLogOut(){
        try {
            mForum = new ApiService();
            basePresenter = new BasePresenter(this,mForum);
            basePresenter.logOut();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
