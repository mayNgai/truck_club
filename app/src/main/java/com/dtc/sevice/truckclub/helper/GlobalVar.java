package com.dtc.sevice.truckclub.helper;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dtc.sevice.truckclub.R;
import com.dtc.sevice.truckclub.model.TblTask;
import com.dtc.sevice.truckclub.until.DateController;
import com.dtc.sevice.truckclub.until.TaskController;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 9/20/2017 AD.
 */

public class GlobalVar {

    public static boolean TimeWaitStop = false;

    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    public static File SDCardRoot = Environment.getExternalStorageDirectory();
    public static String DeviceID = null;
    public static final String file_db = "/WSData/CPBMobile.db";
    public static final String img_direct = "/WSData/ImgCPBMobile";

    //public static String karaoke = "100";

    public static int periotTimeUserBookDriverClickTravel_min = 6;
    public static int periotTimeUserBookDriverClickTravel_max = 12;
    public static int user_refresh_time_find_driver_before_click_travel = 20; //6 ??.  21600
    public static int user_refresh_time_find_driver_click_travel = 10; //15 ????  900
    public static int user_refresh_time_realtime_driver = 3; // 2 ?????? 2

    public static String status_driver_book_when_click_travel = "BookDriverPickingUp";
    public static String status_driver_book_on_under_hour = "BookWaitDriverPickingUp";
    public static String status_driver_book_find_booking = "BookFindBooking";
    public static String status_driver_book_no = "no";

    public static ArrayList<String> optionIDDriver = new ArrayList<String>();
    public static ArrayList<String> optionNameDriver = new ArrayList<String>();

    public static long timeUserWaitRemaining = 0; //ko01032017
    public static long timeDriverWaitRemaining = 0; //ko01032017

    public static long endTimeUserWait = 0;


    //User Cancel Driver
    public static boolean user_status_cancelAll;

    public static String serViceID = null;

    // --- Data User --- //
    public static int user_id = 0;
    public static String user_name_login = null;
    public static String user_name = null;
    public static String user_age = null;
    public static String user_birth_date = null;
    public static String user_pass = null;
    public static String user_status = null;
    public static String name_employee = null;
    public static String user_tel = null;
    public static String user_latitude_scope = null;
    public static String user_longitude_scope = null;
    public static float user_radius_scope = 10700;//5350
    public static double user_radius_scope_latlng = 0.05;
    public static String user_pay_type = null;
    public static String user_identify = null;
    public static int user_time_wait_for_driver_offer_min = 10;
    public static int user_time_wait_for_driver_offer = user_time_wait_for_driver_offer_min * 60; // 7 ????
    public static int user_time_wait_refresh_every_for_driver_offer = 10; // ????????? 7 ??
    public static int refresh_time_all_service = 20;
    public static String user_now_or_book = null;
    public static int picStep = 1;
    public static int mYear = 1993, mMonth = 9, mDay = 27, mHour = 7, mMinute = 5;
    public static String UserBookStartDate = null;

    // --- Data Driver --- //

    public static String driver_latitude_scope = null;
    public static String driver_longitude_scope = null;
    public static int driver_time_wait_for_user_online = 86400; // 1 ???
    public static int driver_time_wait_refresh_every_for_user_online = 10; //3 ??????
    public static int pull_driver_real_time = 5;
    public static int driver_time_wait_user_accept_or_cancel = 60; //60 ??????
    public static float driver_radius_scope = 5350; // 5.35 ??.
    public static double driver_radius_scope_latlng = 0.05; // 5 km
    public static int driverRefreshMarkerMyself = 5; //1 sec
    public static int timeUserWaitDriverChoose = 10; //1 min
    public static String driver_now_or_book = null;
    public static String driver_status_wait_accept_NotYet = "NotYet";
    public static String driver_status_wait_accept_Paired = "Paired";
    public static String driver_status_wait_accept_Cancel = "Cancel";

    //Report User
    public static String[] user_usernameDriverOfUser = new String[1000];
    public static String[] user_price = new String[1000];
    public static String[] user_timeStart = new String[1000];
    public static String[] user_timeEnd = new String[1000];
    public static String[] user_payType = new String[1000];
    public static String[] user_latitudeUserStart = new String[1000];
    public static String[] user_longitudeUserStart = new String[1000];
    public static String[] user_completeStatus = new String[1000];

    //Report Driver
    public static String[] driver_usernameUserOfDriver = new String[1000];
    public static String[] driver_price = new String[1000];
    public static String[] driver_timeStart = new String[1000];
    public static String[] driver_timeEnd = new String[1000];
    public static String[] driver_payType = new String[1000];
    public static String[] driver_latitudeUserStart = new String[1000];
    public static String[] driver_longitudeUserStart = new String[1000];
    public static String[] driver_completeStatus = new String[1000];
    public static double driver_sent_price;

    //User SearchScope
    public static String[] driver_name_userSearchScope = new String[1000];
    public static String[] driver_Username_userSearchScope = new String[1000];
    public static String[] driver_latitude_userSearchScope = new String[1000];
    public static String[] driver_longitude_userSearchScope = new String[1000];
    public static String status_user_now_WaitConfirmPickUp = "Wait Confirm Pick Up";
    public static String status_user_now_ConfirmPickUp = "Confirm Pick Up";
    public static String status_user_now_GoingToDestination = "Going To Destination";
    public static String status_user_now_new_log_in = "Login";

    //driver Status
    public static String status_driver_now_ArrivedPickUp = "ArrivedPickUp";
    public static String status_driver_now_Offering = "Offering";
    public static boolean status_driver_book_travel = false;

    //User Pull Driver Offer
    public static ArrayList<String> driver_name_userPullDriverOffering = new ArrayList<String>();
    public static ArrayList<String> driver_Username_userPullDriverOffering = new ArrayList<String>();
    public static ArrayList<Double> driver_price_userPullDriverOffering = new ArrayList<Double>();
    public static int size_driver_userPullDriverOffering;
    public static String user_picked_usernameDriver;
    public static List<TblTask> listTaskDriverOffer = new ArrayList<TblTask>();

    //Driver Pull User Online
    public static ArrayList<String> user_name_driverPullUserOnline = new ArrayList<String>();
    public static ArrayList<String> user_username_Login_driverPullUserOnline = new ArrayList<String>();
    public static ArrayList<String> user_position_driverPullUserOnline = new ArrayList<String>();
    public static ArrayList<String> user_destination_driverPullUserOnline = new ArrayList<String>();
    public static int size_user_driverPullUserOnline;
    public static String driver_picked_usernameUser;
    public static List<TblTask> listTask = new ArrayList<TblTask>();

    //Driver Book pull all jobs

    public static ArrayList<Integer> driver_book_pull_all_jobs_wage_offer = new ArrayList<Integer>();
    public static ArrayList<String> driver_book_pull_offered_deal_status = new ArrayList<String>();

    //////// user_book_complete
    public static int driverIDPicked = -1;
    public static String driverStartDatePicked = "";
    public static String driverEndDatePicked = "";
    public static int reserviceIdUser = -1;

    public static int user_status_book_accepted = 2;  //t_temp_offer
    public static boolean user_book_have_q = false;
    public static int clicked_complete_book = 0; // no_one


    public static String driver_book_status_on_Db = "Y";

    public static ArrayList<String> a = new ArrayList<String>();
    public static ArrayList<String> a2 = new ArrayList<String>();
    public static ArrayList<String> a3 = new ArrayList<String>();


    //Other
    public static String chooseServiceDriver = "Driver";
    public static String chooseServiceUser = "User";
    public static int IDFail_Null = 0;


    //User Book Pull Myself

    public static ArrayList<String> start_date = new ArrayList<String>();
    public static ArrayList<String> start_Location = new ArrayList<String>();
    public static ArrayList<String> start_province = new ArrayList<String>();
    public static ArrayList<String> start_lat = new ArrayList<String>();
    public static ArrayList<String> start_lon = new ArrayList<String>();
    public static ArrayList<Integer> reserve_date_count = new ArrayList<Integer>();
    public static ArrayList<String> dest_location = new ArrayList<String>();
    public static ArrayList<String> dest_province = new ArrayList<String>();
    public static ArrayList<String> dest_lat = new ArrayList<String>();
    public static ArrayList<String> dest_lon = new ArrayList<String>();
    public static ArrayList<String> arve_wage = new ArrayList<String>();
    public static ArrayList<String> fuel_type = new ArrayList<String>();
    public static ArrayList<String> van_option = new ArrayList<String>();
    public static ArrayList<String> reservice_status = new ArrayList<String>();
    public static ArrayList<String> pick_up_lat = new ArrayList<String>();
    public static ArrayList<String> pick_up_lng = new ArrayList<String>();
    public static ArrayList<String> pick_up_location = new ArrayList<String>();
    public static ArrayList<String> pick_up_province = new ArrayList<String>();
    public static ArrayList<String> one_way_or_one_round = new ArrayList<String>();
    public static ArrayList<Bitmap> pic_profile = new ArrayList<Bitmap>();
    public static ArrayList<Bitmap> pic_full_van = new ArrayList<Bitmap>();
    public static ArrayList<Bitmap> pic_inside_van = new ArrayList<Bitmap>();
    public static ArrayList<Bitmap> pic_profile_acc = new ArrayList<Bitmap>();
    public static ArrayList<Bitmap> pic_full_van_acc = new ArrayList<Bitmap>();
    public static ArrayList<Bitmap> pic_inside_van_acc = new ArrayList<Bitmap>();
    public static ArrayList<Integer> Birthdate = new ArrayList<Integer>();

    ////////////////////////////////////////////////////////////////////

    public static double latDriverInsideOnLocationChang = 0;
    public static double lngDriverInsideOnLocationChang = 0;

    ///////////////////////////////////////////////////////////////////

    public static String date_Start_User_Book;
    public static String date_End_User_Book;

    //////////////////////////////////////////////////////////////////

    public static ArrayList<String> user_book_start_date_paired = new ArrayList<>();
    public static ArrayList<String> user_book_end_date_paired = new ArrayList<>();
    public static ArrayList<Integer> user_book_reserve_date_count_paired = new ArrayList<>();

    /////////////////////////////////////////////////////////////////

    public static boolean _driver_AlertBookToday = true;

    ///////////////////////////////////////////////////////////////////

    public static ArrayList<Integer> Rating_Cleaning = new ArrayList<Integer>();
    public static ArrayList<Integer> Rating_Gentle = new ArrayList<Integer>();
    public static ArrayList<Integer> Rating_Ontime = new ArrayList<Integer>();
    public static ArrayList<Integer> Rating_Speeding = new ArrayList<Integer>();
    public static ArrayList<Integer> Count_All_Service = new ArrayList<Integer>();

    public static String dateServer = "";
    public static boolean _user_AlertBookToday = true;

    //////////////////////////////////////////////////////////////////

    // ===upload Node image ===
    //public static String url_NodeImage = "http://wisasoft.com:5555";
    public static String url_NodeImage = "http://127.0.0.1:5555/";
//    public static String url_NodeImage = "http://58.181.246.58:5555";

    // Save Image
    public static String saveImageToExternalStorage(Bitmap image, String type, String user_id) {
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String fullPath = SDCardRoot.getAbsolutePath() + img_direct;
        String PathFile = null;
        Date PICTURE_NAME = null;
        try {
            File dir = new File(fullPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            if (PICTURE_NAME == null) {
                PICTURE_NAME = new Date();
            }

            PathFile = "CPBMobile_" + type + "_" + user_id + "_" + df.format(PICTURE_NAME) + ".png";
            //here  Somsak Edit
            //	compressImage(PathFile);
            FileOutputStream fOut = null;
            File file = new File(fullPath, PathFile);
            file.createNewFile();
            PathFile = String.valueOf(file);
            fOut = new FileOutputStream(file);

            image.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();

            android.provider.MediaStore.Images.Media.insertImage(null, file.getAbsolutePath(), PathFile, null);
        } catch (Exception e) {
            Log.e("saveToExternalStorage()", e.getMessage());
        }
        return PathFile;


    }//== url image ===

    public static final String ftp_address = "203.151.20.103";
    public static final int ftp_port = 21;
    public static final String ftp_uid = "php";
    public static final String ftp_pwd = "1234";
    public static final String Ftp_dir = "/CPB/pad_upload_image";

    //    public static String url_up_pic = "http://58.181.246.58:5556/dhub/uploads/";
//    public static String url_up_pic = "http://wisasoft.com:5555/uploads/";
    public static String url_up_pic = "http://127.0.0.1:5555/uploads/";
    private static String url = "http://wisasoft.com:8994/"; //http://wisasoft.com:8994/
    //    private static String url = "10.255.248.63/";
    public static String url_user_login = url + "koh/User_login_test.php";
    public static String url_user_register = url + "koh/User_Register.php";
    public static String url_driver_login = url + "koh/Driver_login.php";
    public static String url_driver_register = url + "koh/Driver_Register_Mod_1.1.php";
    public static String url_user_pull_register = url + "koh/User_Pull_Register.php";
    public static String url_driver_pull_register = url + "koh/Driver_Pull_Register.php";
    public static String url_user_pull_report = url + "koh/User_Pull_Report.php";
    public static String url_driver_pull_report = url + "koh/Driver_Pull_Report.php";
    public static String url_user_search_scope = url + "koh/User_Search_Scope.php";
    public static String url_driver_search_scope = url + "koh/Driver_Search_Scope.php";
    public static String url_user_status = url + "koh/User_Status.php";
    public static String url_driver_status = url + "koh/Driver_Status.php";
    public static String url_driver_sent_price = url + "koh/Driver_Sent_Price.php";
    public static String url_user_sent_latLngPosDes = url + "koh/User_Sent_LatLngPosDes.php";

    public static String url_driver_pull_time_to_wait = url + "koh/Driver_Now_Pull_Time_To_Wait.php";

    public static String url_user_pull_driver_offer = url + "koh/User_Pull_Driver_Offer.php";
    public static String url_driver_pull_user_online = url + "koh/Driver_Pull_User_Online_In_Area.php";
    public static String url_driver_sent_lat_lng_now_user_online = url + "koh/Driver_Sent_Lat_Lng_Now.php";
    public static String url_user_picked_driver = url + "koh/User_Picked_Driver.php";

    public static String url_user_change_status = url + "koh/User_Change_Status.php";
    public static String url_user_clear_pick_driver = url + "koh/User_Clear_Pick_Driver.php";
    public static String url_driver_clear_pick_user_and_price = url + "koh/Driver_Clear_Pick_User_And_Price.php";

    public static String url_cancel_driver_offer = url + "koh/User_Cancel_Driver_And_Change_Status.php";
    public static String url_driver_pull_user_paired = url + "koh/Driver_Pull_User_Paired.php";

    public static String url_clearTable = url + "koh/Clear_Table.php";
    public static String url_realTime = url + "koh/Real_Time.php";
    public static String url_clearTable_LogOut = url + "koh/Clear_Table_LogOut.php";


    public static String url_user_pull_driver_real_time = url + "koh/User_Pull_Driver_Real_Time.php";
    public static String url_pull_driver_option = url + "koh/pull_driver_option.php";
    public static String url_register_van = url + "koh/Driver_Register_Van_Mod_1.1.php";
    public static String url_pull_driver_group = url + "koh/pull_driver_group.php";
    public static String url_user_book = url + "koh/User_Book.php";
    public static String url_driver_pull_infor_himself = url + "koh/Driver_pull_all_information_himself.php";

    public static String url_driver_book_pull_all_jobs = url + "koh/Driver_Book_Pull_User_All.php";

    public static String url_driver_book_pull_all_jobs2 = url + "koh/Driver_Pull_Schedule_Myself.php";

    public static String url_driver_book_sent_user_picked = url + "koh/Driver_book_sent_user_picked.php";
    public static String url_user_book_pull_driver_offer = url + "koh/User_Book_Pull_Driver_Offer.php";

    public static String url_user_book_pull_driver_offer_and_still_no = url + "koh/User_Book_pull_Driver_Offer_And_Still_No.php";

    public static String url_driver_book_pull_request_himself = url + "koh/Driver_Book_Pull_Request_HImself.php";
    public static String url_driver_book_pull_accept_from_user = url + "koh/Driver_Book_Pull_Accept_From_User.php";

    public static String url_driver_book_pull_cancel_from_user = url + "koh/Driver_Book_Pull_Cancel_User.php";
    public static String url_user_book_sent_accept_driver = url + "koh/User_Accept_Driver.php";

    public static String url_user_book_check_q = url + "koh/User_Check_Q.php";
    public static String url_driver_edit_van = url + "koh/Driver_Edit_Van.php";
    public static String url_driver_edit_profile = url + "koh/Driver_Edit_Profile.php";

    public static String url_user_edit_profile = url + "koh/User_Edit_Profile.php";

    public static String url_user_pull_status_driver_picked = url + "koh/User_Pull_Status_Driver_Picked.php";

    public static String url_user_sent_status = url + "koh/User_Sent_Status.php";
    public static String url_book_end_job = url + "koh/User_Book_End_Job.php";


    public static String url_driver_report_of_driver = url + "koh/Driver_Report_Of_Driver.php";

    public static String url_driver_report_of_driver_book = url + "koh/Driver_Report_Of_Driver_Book.php";
    public static String url_user_report_of_user_book = url + "koh/User_Report_Of_User_Book.php";
    public static String url_user_report_of_user = url + "koh/User_Report_Of_User.php";

    public static String url_user_update_driver_reported = url + "koh/User_Update_Driver_Reported.php";

    public static String url_driver_update_user_reported = url + "koh/Driver_Update_User_Reported.php";
    public static String url_user_give_rating_driver = url + "koh/User_Give_Rating_Driver.php";
    public static String url_driver_give_rating_user = url + "koh/Driver_Give_Rating_User.php";
    public static String url_driver_now_clear_table = url + "koh/Driver_Now_Clear.php";
    public static String url_driver_pull_user_picked_status = url + "koh/Driver_Pull_User_Status.php";

    public static String url_user_now_pull_detail_driver_paired = url + "koh/User_Now_Pull_Detail_Driver_Paired.php";
    public static String url_driver_now_check_user_still_online = url + "koh/Driver_Now_Check_User_Still_Online.php";
    public static String url_serverTime = url + "/koh/serverTime.php";
    public static String url_user_pull_date_start_date_end = url + "/koh/User_Book_Pull_Start_Date_End_Date.php";
    public static String url_driver_pull_date_start_date_end = url + "/koh/Driver_Book_Pull_Start_Date_End_Date.php";
    public static String url_driver_book_pull_dest_user = url + "koh/Driver_Book_Pull_Dest_User_ComingSoon_Day.php";

    public static String url_user_book_pull_myself_paired = url + "koh/User_Book_Pull_Myself_Paired.php";

    public static String url_user_book_pull_closest_date_and_my_driver = url + "koh/User_Book_Pull_Start_Date_Nearest_Current_Date.php";

    public static String url_user_now_see_detail_driver = url + "koh/User_See_Detail_Driver.php";

    public static String url_user_now_insert_and_update_history = url + "koh/User_Now_Insert_And_Update_History.php";
    public static String url_driver_now_insert_and_update_history = url + "koh/Driver_Now_Insert_And_Update_History.php";
    public static String url_user_now_get_history = url + "koh/User_Now_History.php";
    public static String url_driver_now_get_history = url + "koh/Driver_Now_History.php";
    public static String url_user_book_get_history = url + "koh/User_Book_History.php";
    public static String url_driver_book_get_history = url + "koh/Driver_Book_History.php";

    public static String url_driver_book_end_job = url + "koh/Driver_Book_End_Job.php";
    public static String url_user_book_end_job = url + "koh/User_Book_End_Job.php";

    public static String url_user_login_dup = url + "koh/User_Login_Dup.php";


    public static String User_Book_Pull_Jobs_Oferred_From_Driver = url + "koh/User_Book_Pull_Jobs_Oferred_From_Driver.php";
    public static String Driver_Book_Pull_Pired_And_Cancel = url + "koh/Driver_Book_Pull_Pired_And_Cancel.php";


    public static String url_driver_pull_new_jobs_and_free_time_driver = url + "koh/Driver_Pull_New_Jobs_And_Free_Time_Driver.php";

    public static String url_driver_book_save_free_time = url + "koh/Driver_Book_Save_Free_Time.php";
    public static String url_driver_book_delete_his_offer = url + "koh/Driver_Book_Delete_His_Offer.php";

    public static String url_driver_book_click_travel = url + "koh/Driver_Book_Click_Travel.php";




    //=== iNueng ===
    public static String url_Driver_Schedule = url + "koh/Driver_Pull_Schedule_mod_1.1.php";

    public static String url_pull_Profile = "";
    public static String url_login = "";
    public static String url_register = "";

    public static String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "MyFolder/Images");
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        return uriSting;

    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    public static Bundle takeScreenshot(View view) {
        Bundle screenshot = new Bundle();
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
        String fullPath = SDCardRoot.getAbsolutePath() + img_direct;

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = "CP_Screenshot" + now + ".jpg";

            // create bitmap screen capture
//			View v1 = getWindow().getDecorView().getRootView();
            view.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);

            File imageFile = new File(fullPath, mPath);
            imageFile.createNewFile();

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();
            String image = String.valueOf(imageFile);
            String pat = String.valueOf(mPath);

            screenshot.putString("IMAGE", image);
            screenshot.putString("PATH", fullPath);
            return screenshot;

//			openScreenshot(imageFile);
        } catch (Throwable e) {
            // Several error may come out with file handling or OOM
            e.printStackTrace();
            return screenshot;
        }

    }

    public static int findAge(String dateBirth) {

        GregorianCalendar cal = new GregorianCalendar();
        int y, m, d, a;

        y = cal.get(Calendar.YEAR);
        m = cal.get(Calendar.MONTH);
        d = cal.get(Calendar.DAY_OF_MONTH);
        cal.set(Integer.parseInt(dateBirth.substring(0, 4)), Integer.parseInt(dateBirth.substring(5, 7)), Integer.parseInt(dateBirth.substring(8, 10)));
        a = y - cal.get(Calendar.YEAR);
        if ((m < cal.get(Calendar.MONTH))
                || ((m == cal.get(Calendar.MONTH)) && (d < cal
                .get(Calendar.DAY_OF_MONTH)))) {
            --a;
        }
        if (a < 0)
            throw new IllegalArgumentException("Age < 0");
        return a;

    }

    public static String DateToBack(String dateStart, int dateCount) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        String endDate = "000";
        try {
            c.setTime(sdf.parse(dateStart));
            c.add(Calendar.DATE, dateCount - 1);
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            endDate = sdf1.format(c.getTime());

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return endDate;
    }

    public static String getPath(final Context context, final Uri uri) {
        String path="";

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
                // ExternalStorageProvider
                if (isExternalStorageDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    if ("primary".equalsIgnoreCase(type)) {
                        path = Environment.getExternalStorageDirectory() + "/" + split[1];
                        return path;
                    }

                    // TODO handle non-primary volumes
                }
                // DownloadsProvider
                else if (isDownloadsDocument(uri)) {

                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                    path = getDataColumn(context, contentUri, null, null);
                    return path;
                }
                // MediaProvider
                else if (isMediaDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }

                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{
                            split[1]
                    };
                    path = getDataColumn(context, contentUri, selection, selectionArgs);
                    return path;
                }
            }
            // MediaStore (and general)
            else if ("content".equalsIgnoreCase(uri.getScheme())) {
                path = getDataColumn(context, uri, null, null);
                return path;
            }
            // File
            else if ("file".equalsIgnoreCase(uri.getScheme())) {
                path = uri.getPath();
                return path;
            }
        }

        return path;
    }

    public static void renameImage(String filePath, String newName){
        try {
            File photo = new File(filePath);//get file
            String fileName = photo.getName();//file name
            File newFile = new File(newName);//resave file with new name
            photo.renameTo(newFile);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static boolean checkDeviceTimeAutoUpdate(final Context mcontext) {
        boolean check = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (Settings.Global.getInt(mcontext.getContentResolver(), Settings.Global.AUTO_TIME, 0) == 0) {
                new AlertDialog.Builder(mcontext)
                        .setTitle("Please check your device time")
                        .setMessage("You must turn on automatic date,time and timezone")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent myIntent = new Intent(Settings.ACTION_DATE_SETTINGS);
                                mcontext.startActivity(myIntent);
                                //finish();
                                System.exit(0);
                            }
                        }).create().show();
            } else {
                check = true;
            }
        }
        return check;
    }

    public static String getSystemTime(Context mContext) {
        long time;
        String timeStr = "1993-10-27 07:05";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (Settings.Global.getInt(mContext.getContentResolver(), Settings.Global.AUTO_TIME, 0) == 0)
                mContext.startActivity(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS));
            else {
                time = System.currentTimeMillis();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date resultDate = new Date(time);
                timeStr = sdf.format(resultDate);
            }
        }

        return timeStr;
    }

    public static String getSystemDateOnly(Context mContext) {
        long time;
        String timeStr = "1993-10-27 07:05";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (Settings.Global.getInt(mContext.getContentResolver(), Settings.Global.AUTO_TIME, 0) == 0)
                mContext.startActivity(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS));
            else {
                time = System.currentTimeMillis();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                Date resultDate = new Date(time);
                timeStr = sdf.format(resultDate);
            }
        }

        return timeStr;
    }

    public static String getSystemDateOnlyformat(Context mContext) {
        long time;
        String timeStr = "1993-10-27 07:05";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (Settings.Global.getInt(mContext.getContentResolver(), Settings.Global.AUTO_TIME, 0) == 0)
                mContext.startActivity(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS));
            else {
                time = System.currentTimeMillis();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date resultDate = new Date(time);
                timeStr = sdf.format(resultDate);
            }
        }

        return timeStr;
    }

    public static String getSystemTimeOnly(Context mContext) {
        long time;
        String timeStr = "07:05";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (Settings.Global.getInt(mContext.getContentResolver(), Settings.Global.AUTO_TIME, 0) == 0)
                mContext.startActivity(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS));
            else {
                time = System.currentTimeMillis();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                Date resultDate = new Date(time);
                timeStr = sdf.format(resultDate);
            }
        }

        return timeStr;
    }


    public static int getHoursDistance(String time1, String time2) {

        int dateFormatted = 0;
        String[] date1, date2;
        int dateUtil1, dateUtil2, dateUtil3, dateUtil4;

        if (time1 != null && !"".equals(time1) && time2 != null && !"".equals(time2)) {
            try {
                date1 = time1.split(":");
                date2 = time2.split(":");
                dateUtil1 = Integer.valueOf(date1[0]);
                dateUtil2 = Integer.valueOf(date1[1]);
                dateUtil3 = Integer.valueOf(date2[0]);
                dateUtil4 = Integer.valueOf(date2[1]);

                int intTimes = Integer.valueOf(dateUtil1) - Integer.valueOf(dateUtil3);
                int intSec = Integer.valueOf(dateUtil2) + Integer.valueOf(dateUtil4);

                if (intSec >= 60) {
                    intSec = intSec - 60;
                    intTimes = intTimes + 1;
                }
                dateFormatted = (intTimes * 60) + intSec;
            } catch (Exception e) {
                dateFormatted = 0;
                e.printStackTrace();
            }
        }
        return dateFormatted;
    }

    public static long dateTimeFormatTolong(String date) {
        SimpleDateFormat dateFormat6 = new SimpleDateFormat("yyyy-MM-dd");
        String string_date = date;
        Date d = null;
        try {
            d = dateFormat6.parse(string_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long milliseconds = d.getTime();
        return milliseconds;
    }

    public static long dateTimeFormatTolongAll(String date) {
        SimpleDateFormat dateFormat6 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String string_date = date;
        Date d = null;
        try {
            d = dateFormat6.parse(string_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long milliseconds = d.getTime();
        return milliseconds;
    }

    //////////////////////////////////////////////////////////// permission
    public static boolean subFullPermission(final Context context, final Activity activity) {

        boolean pass = false;
        boolean gps_enabled = false, phoneCall_enabled = false, network_enabled = false;
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if ((checkDeviceTimeAutoUpdate(context)))
            if (CheckPermission.checkAndRequestPermissions(context, activity))
                if (new ConnectionDetector(context).isConnectingToInternet()) {

                    try {
                        gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
                    } catch (Exception ex) {
                    }

                    try {
                        phoneCall_enabled = ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CALL_PHONE);

                    } catch (Exception ex) {
                    }

                    try {
                        network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                    } catch (Exception ex) {
                    }
                    if (!gps_enabled && !network_enabled && !phoneCall_enabled) {
                        // notify user
                        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                        dialog.setMessage(context.getResources().getString(R.string.LocationFail));
                        dialog.setPositiveButton(context.getResources().getString(R.string.OpenGPS), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                // TODO Auto-generated method stub
                                Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                context.startActivity(myIntent);
                                Log.d("kohhh", "0");
                                activity.finish();
                                System.exit(0);
                                //get gps
                            }
                        });
                        dialog.setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                // TODO Auto-generated method stub
                            }
                        });
                        dialog.show();
                    } else {

                        pass = true;
                    }

                } else
                    new AlertDialog.Builder(context)
                            .setTitle("Internet Problem")
                            .setMessage("Please Check your internet connection")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    activity.finish();
                                    System.exit(0);
                                }
                            }).create().show();
            else {
                CheckPermission.checkAndRequestPermissions(context, activity);  //checkAndRequestPermissions()
            }

        return pass;
    }

    public static boolean CheckFullPermission(int requestCode, String permissions[], int[] grantResults, final Context context, final Activity activity) {

        boolean pass = false;

        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {

                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                perms.put(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(android.Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(android.Manifest.permission.READ_PHONE_STATE, PackageManager.PERMISSION_GRANTED);
                perms.put(android.Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);

                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);

                    if (perms.get(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(android.Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                            ) {

                    } else {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                || ActivityCompat.shouldShowRequestPermissionRationale(activity, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                                || ActivityCompat.shouldShowRequestPermissionRationale(activity, android.Manifest.permission.READ_PHONE_STATE)
                                || ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                                || ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)

                                ) {

                            new AlertDialog.Builder(context)
                                    .setMessage("?????????????????????????")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    CheckPermission.checkAndRequestPermissions(context, activity);
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    Toast.makeText(context, "Go cto settings and enable permissions", Toast.LENGTH_LONG).show();
                                                    activity.finish();
                                                    System.exit(0);
                                                    break;
                                            }
                                        }
                                    })
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    CheckPermission.checkAndRequestPermissions(context, activity);
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    Toast.makeText(context, "Go cto settings and enable permissions", Toast.LENGTH_LONG).show();
                                                    activity.finish();
                                                    System.exit(0);
                                                    break;
                                            }
                                        }
                                    })
                                    .create()
                                    .show();
                        }
                        //permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                        else {
                            pass = true;
                        }
                    }
                }
            }
        }
        return pass;
    }

    ////////////////////////////////////////////^ permission

    ///////////////////////////////////////////

    public static int countBetweenSecond(String dateStart, String dateEnd) {

        int countDate = 100;
        Date d1, d2;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            d1 = sdf.parse(dateStart);
            d2 = sdf.parse(dateEnd);
            long diff = d2.getTime() - d1.getTime();
            //Date resultDate = new Date(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
            //OnlyDate = sdf.format(resultDate);
            countDate = (int) (diff / 86400000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return countDate;
    }

    public static int countBetweenDate(String dateStart, String dateEnd) {

        int countDate = 100;
        Date d1, d2;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            d1 = sdf.parse(dateStart);
            d2 = sdf.parse(dateEnd);
            long diff = d2.getTime() - d1.getTime();
            //Date resultDate = new Date(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
            //OnlyDate = sdf.format(resultDate);
            countDate = (int) (diff / 86400000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return countDate;
    }

    public static boolean checkSchedulesDriver(Context context , List<TblTask> taskList) {
        boolean success = false;
        Date dateobj = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(sdf.format(dateobj));
        String strCurrent = sdf.format(dateobj);
        DateController dateController = new DateController();
        long current = dateController.dateFormat2Tolong(strCurrent);
        for(TblTask t : taskList){
            long start = dateController.dateFormat2Tolong(t.getStart_date());
            long end = dateController.dateFormat2Tolong(t.getEnd_date());
            if(current>=start &&current<=end){
                success = false;
                break;
            }else{
                success = true;
            }
        }
        return success;
    }

    public static boolean checkNotiTaskBooking(String str_start_date){
        boolean success = false;
        try {
            TaskController taskController = new TaskController();
            DateController dateController = new DateController();
            List<TblTask> taskList = new ArrayList<TblTask>();
            taskList = taskController.getTask();
            long current = dateController.dateFormat2Tolong(str_start_date);
            if(taskList!= null || taskList.size()>0){
                for(TblTask t : taskList){
                    long start = dateController.dateFormat2Tolong(t.getStart_date());
                    long end = dateController.dateFormat2Tolong(t.getEnd_date());
                    if(current>=start &&current<=end){
                        success = false;
                        break;
                    }else{
                        success = true;
                    }
                }
            }else {
                success = true;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return success;
    }

    public static String checkStatusTask(int status){
        String str = "";
        try {
            if(status == 3){
                str = "คนขับยังไม่ออกเดินทาง";
            }else if(status == 4){
                str = "รถกำลังเดินทางมา";
            }else if(status == 5){
                str = "กำลังใช้งาน";
            }else if(status == 6){
                str = "จบงาน";
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return str;
    }

    public static boolean GPSEnable(Context context) {
        boolean enable = false;
        final LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            enable = true;
        return enable;
    }

    public static boolean isOnline(Context _context) {
        ConnectivityManager cm =
                (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static int GPSEnableAndisOnline(Context context) {
        int enable = 0; //0= openAll , 1= netOpen and GPSnotOpen , 2=netnotOpen and GPSOpen , 3= netnot and GPSnot
        try {
            if(isOnline(context)){
                if(GPSEnable(context))
                    enable = 0;
                else
                    enable = 1;

            }else {
                if(GPSEnable(context))
                    enable = 2;
                else
                    enable = 3;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return enable;
    }

    public static String ChangeformatDateYMD_To_DMY(String oldDate) {
        String newDate;
        if (oldDate.length() > 10)
            newDate = oldDate.substring(8, 10) + "-" + oldDate.substring(5, 7) + "-" + oldDate.substring(0, 4) + " " + oldDate.substring(11, oldDate.length() - 1);
        else
            newDate = oldDate.substring(8, 10) + "-" + oldDate.substring(5, 7) + "-" + oldDate.substring(0, 4);
        return newDate;
    }

    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize, boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
    }

    public static String saveImage(Bitmap finalBitmap,String pathDefault) {
        String path_new = "";
        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        //System.out.println(root +" Root value in saveImage Function");
        File myDir = new File(root + "/TruckClub");

        if (!myDir.exists()) {
            myDir.mkdirs();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyymmsshhmmss");
        String date = simpleDateFormat.format(new Date());
//        File file = new File(myDir, date + "truck_club");
        File file = new File(myDir, date + findPicName(pathDefault).replace("'",""));
        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        path_new = file.getPath();
        return path_new;
    }

    public static String findPicName(String namePath) {
        String namePic = "";
        int save = 0;
        boolean stop = false;
        for (int i = namePath.length(); i >= 0 && !stop; i--) {

            if (namePath.substring(i - 1, i).equals("/")) {
                save = i;
                stop = true;
            }
        }
        namePic = namePath.substring(save, namePath.length());
        return namePic;
    }

    public static String getDeviceID(Context context) {
        String android_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return android_id;
    }

    public static String ChangWordOptionToKey(String word) {
        String key = "";
        if (word.equalsIgnoreCase("VIP 14 ที่นั่ง") || word.equalsIgnoreCase("14 ที่นั่ง") || word.equalsIgnoreCase("14ที่นั่ง") || word.equalsIgnoreCase("VIP 14 place") || word.equalsIgnoreCase("14 place"))
            key = "100";
        if (word.equalsIgnoreCase("VIP 10 ที่นั่ง") || word.equalsIgnoreCase("10 ที่นั่ง") || word.equalsIgnoreCase("10ที่นั่ง") || word.equalsIgnoreCase("VIP 10 place") || word.equalsIgnoreCase("10 place"))
            key = "101";
        if (word.equalsIgnoreCase("VIP 8 ที่นั่ง") || word.equalsIgnoreCase("8 ที่นั่ง") || word.equalsIgnoreCase("8ที่นั่ง") || word.equalsIgnoreCase("VIP 8 place") || word.equalsIgnoreCase("8 place"))
            key = "102";
        return key;
    }

    public static Boolean SetCheckEditVehicleOption(int thatOption, String fullString) {

        boolean check = false;

        if (thatOption == 0)
            if (fullString.substring(0, 1).equalsIgnoreCase("T"))
                check = true;
            else
                check = false;

        int count = 0;
        for (int i = 4; i <= (fullString.length() - 1) - 4; i++)
            if (fullString.substring(i, i + 1).equalsIgnoreCase("/")) {
                count = count + 1;
                if (thatOption == count)
                    if (fullString.substring(i + 1, i + 2).equalsIgnoreCase("T"))
                        check = true;
                    else
                        check = false;
            }

        return check;
    }

    public static boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    public static boolean isPhoneNumberValid(String phone) {
        //TODO: Replace this with your own logic
        return phone.length()==10;
    }

    public static boolean isPassWordValid(String pass) {
        //TODO: Replace this with your own logic
        return (pass.length()>7&&pass.length()<16);
    }

    public static boolean isConfirmPassWordValid(String pass , String confirm) {
        //TODO: Replace this with your own logic
        return (pass.equals(confirm));
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
}
