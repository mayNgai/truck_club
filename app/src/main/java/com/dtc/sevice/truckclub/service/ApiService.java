package com.dtc.sevice.truckclub.service;

import com.dtc.sevice.truckclub.model.TblCarDetail;
import com.dtc.sevice.truckclub.model.TblCarGroup;
import com.dtc.sevice.truckclub.model.TblMember;
import com.dtc.sevice.truckclub.model.TblProvince;
import com.dtc.sevice.truckclub.model.TblTask;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by admin on 9/20/2017 AD.
 */

public class ApiService {
    public static final String FORUM_SERVER_URL = "http://10.255.248.63:3100/";//10.255.248.63//127.0.0.1
    //public static final String FORUM_SERVER_URL = "http://wisasoft.com:8994/truck_club/";
    //public static final String FORUM_SERVER_URL = "http://10.255.248.63:80/truck_club/";
    public static final String url_facebook = "https://graph.facebook.com/";
    public static final String pic_facebook = "/picture?type=large";

    private ForumApi mForumApi;
    public ApiService() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient okHttpClient = builder.build();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        //RequestBody data = RequestBody.create(MediaType.parse("application/json"), stringJson);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(FORUM_SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        mForumApi = retrofit.create(ForumApi.class);
    }

    public ForumApi getApi() {

        return mForumApi;
    }

    public interface ForumApi {

        @POST("register_member")//create_member_and_car.php
        public Observable<TblMember> createMemberAndCar(@Body TblMember member);

        @POST("create_member_and_car.php")//create_member_and_car.php
        public Observable<TblMember> createMemberAndCarTest(@Body TblMember member);

        @FormUrlEncoded
        @POST("member_register.php")
        public Observable<TblMember> createMember(@Field("first_name") String first_name, @Field("last_name") String last_name, @Field("email") String email,
                                                  @Field("user_name") String user_name, @Field("tel") String tel, @Field("birth_date") String birth_date, @Field("sex") String sex,
                                                  @Field("password") String password, @Field("member_type") int member_type, @Field("authority") String authority, @Field("device_id") String device_id,
                                                  @Field("face_book_id") String face_book_id, @Field("name_pic_path") String name_pic_path);


        @POST("get_login")//@POST("user_login")
        public Observable<TblMember> getLogin(@Body TblMember member);

        @POST("get_car_detail")//get_car_detail.php
        public Observable<TblCarDetail> getCarDetail(@Body TblMember member);

        @POST("update_status_member.php")
        public Observable<TblMember> updateStatusMember(@Body TblMember member);

        @POST("get_province")//get_province.php
        public Observable<List<TblProvince>> getProvince();

        @POST("get_car_group")//@POST("loadCarGroup")
        public Observable<List<TblCarGroup>> getCarGroup();

        @POST("user_search_driver_in_scope.php")
        public Observable<List<TblMember>> getDriverInScope(@Body TblMember member);

        @POST("update_profile.php")
        public Observable<TblMember> updateProfile(@Body TblMember member);

        @POST("clear_member")
        public Observable<TblMember> logOut(@Body TblMember member);

        @POST("sent_create_task.php")
        public Observable<TblTask> sentCreateTask(@Body TblTask task);

        @POST("get_task.php")
        public Observable<List<TblTask>> getTask(@Body TblTask task);

        @POST("get_task_by_id.php")
        public Observable<TblTask> getTaskByID(@Body TblTask task);

        @POST("get_schedules_driver.php")
        public Observable<List<TblTask>> getSchedulesDriver(@Body TblMember member);

        @POST("get_history.php")
        public Observable<List<TblTask>> getHistory(@Body TblMember member);

        @POST("sent_offer_price.php")
        public Observable<TblTask> sentOfferPrice(@Body TblTask task);

        @POST("update_task.php")
        public Observable<TblTask> sentUpdateTask(@Body TblTask task);

        @POST("update_driver.php")
        public Observable<TblTask> sentUpdateDriver(@Body TblTask task);

        @POST("update_lat_lon.php")
        public Observable<TblMember> sentUpdateLatLon(@Body TblMember member);

        @POST("get_data_member.php")
        public Observable<TblMember> getDataMember(@Body TblMember member);

        @POST("update_setting.php")
        public Observable<TblMember> updateSetting(@Body TblMember member);
    }
}
