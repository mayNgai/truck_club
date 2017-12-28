package com.dtc.sevice.truckclub.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 9/20/2017 AD.
 */
@DatabaseTable(tableName="TblMember")
public class TblMember implements Serializable {
    @DatabaseField( useGetSet = true)
    private String success;
    @DatabaseField( useGetSet = true)
    private String message;
    @DatabaseField(id = true, useGetSet = true)
    private String guid;
    @SerializedName("member_id")
    @Expose
    @DatabaseField( useGetSet = true)
    private int member_id;
    @SerializedName("user_name")
    @Expose
    @DatabaseField( useGetSet = true)
    private String user_name;
    @SerializedName("first_name")
    @Expose
    @DatabaseField( useGetSet = true)
    private String first_name;
    @SerializedName("last_name")
    @Expose
    @DatabaseField( useGetSet = true)
    private String last_name;
    @SerializedName("password")
    @Expose
    @DatabaseField( useGetSet = true)
    private String password;
    @SerializedName("email")
    @Expose
    @DatabaseField( useGetSet = true)
    private String email;
    @SerializedName("tel")
    @Expose
    @DatabaseField( useGetSet = true)
    private String tel;
    @SerializedName("member_type")
    @Expose
    @DatabaseField( useGetSet = true)
    private int member_type;
    @SerializedName("date_member_create")
    @Expose
    @DatabaseField( useGetSet = true)
    private String date_member_create;
    @SerializedName("authority")
    @Expose
    @DatabaseField( useGetSet = true)
    private String authority;
    @SerializedName("date_member_last_edit")
    @Expose
    @DatabaseField( useGetSet = true)
    private String date_member_last_edit;
    @SerializedName("birth_date")
    @Expose
    @DatabaseField( useGetSet = true)
    private String birth_date;
    @SerializedName("sex")
    @Expose
    @DatabaseField( useGetSet = true)
    private String sex;
    @SerializedName("status")
    @Expose
    @DatabaseField( useGetSet = true)
    private String status;
    @SerializedName("status_id")
    @Expose
    @DatabaseField( useGetSet = true)
    private int status_id;
    @SerializedName("lat")
    @Expose
    @DatabaseField( useGetSet = true)
    private float lat;
    @SerializedName("lon")
    @Expose
    @DatabaseField( useGetSet = true)
    private float lon;
    @SerializedName("radius")
    @Expose
    @DatabaseField( useGetSet = true)
    private double radius;
    @SerializedName("device_id")
    @Expose
    @DatabaseField( useGetSet = true)
    private String device_id;
    @SerializedName("step_on_device")
    @Expose
    @DatabaseField( useGetSet = true)
    private int step_on_device;
    @SerializedName("face_book_id")
    @Expose
    @DatabaseField( useGetSet = true)
    private String face_book_id;
    @SerializedName("name_pic_path")
    @Expose
    @DatabaseField( useGetSet = true)
    private String name_pic_path;
    @SerializedName("check_device_id")
    @Expose
    @DatabaseField( useGetSet = true)
    private String check_device_id;
    @SerializedName("token_firebase")
    @Expose
    @DatabaseField( useGetSet = true)
    private String token_firebase;

    @SerializedName("price_offer")
    @Expose
    @DatabaseField( useGetSet = true)
    private String price_offer;

    @SerializedName("date_offer_create")
    @Expose
    @DatabaseField( useGetSet = true)
    private String date_offer_create;
    @SerializedName("complete_status")
    @Expose
    @DatabaseField( useGetSet = true)
    private int complete_status;

    @SerializedName("task_id")
    @Expose
    @DatabaseField( useGetSet = true)
    private String task_id;

    @SerializedName("count")
    @Expose
    @DatabaseField( useGetSet = true)
    private int rating_count;

    @SerializedName("language")
    @Expose
    @DatabaseField( useGetSet = true)
    private String language;

    @SerializedName("time_wait")
    @Expose
    @DatabaseField( useGetSet = true)
    private int time_wait;

    @SerializedName("all_count")
    @Expose
    @DatabaseField( useGetSet = true)
    private int rating_all_count;

    @SerializedName("car_detail")
    @Expose
    private List<TblCarDetail> car_detail = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getMember_type() {
        return member_type;
    }

    public void setMember_type(int member_type) {
        this.member_type = member_type;
    }

    public String getDate_member_create() {
        return date_member_create;
    }

    public void setDate_member_create(String date_member_create) {
        this.date_member_create = date_member_create;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getDate_member_last_edit() {
        return date_member_last_edit;
    }

    public void setDate_member_last_edit(String date_member_last_edit) {
        this.date_member_last_edit = date_member_last_edit;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public int getStep_on_device() {
        return step_on_device;
    }

    public void setStep_on_device(int step_on_device) {
        this.step_on_device = step_on_device;
    }

    public String getFace_book_id() {
        return face_book_id;
    }

    public void setFace_book_id(String face_book_id) {
        this.face_book_id = face_book_id;
    }

    public String getName_pic_path() {
        return name_pic_path;
    }

    public void setName_pic_path(String name_pic_path) {
        this.name_pic_path = name_pic_path;
    }

    public String getCheck_device_id() {
        return check_device_id;
    }

    public void setCheck_device_id(String check_device_id) {
        this.check_device_id = check_device_id;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public String getToken_firebase() {
        return token_firebase;
    }

    public void setToken_firebase(String token_firebase) {
        this.token_firebase = token_firebase;
    }

    public List<TblCarDetail> getCar_detail() {
        return car_detail;
    }

    public void setCar_detail(List<TblCarDetail> car_detail) {
        this.car_detail = car_detail;
    }

    public String getPrice_offer() {
        return price_offer;
    }

    public void setPrice_offer(String price_offer) {
        this.price_offer = price_offer;
    }

    public String getDate_offer_create() {
        return date_offer_create;
    }

    public void setDate_offer_create(String date_offer_create) {
        this.date_offer_create = date_offer_create;
    }

    public int getComplete_status() {
        return complete_status;
    }

    public void setComplete_status(int complete_status) {
        this.complete_status = complete_status;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public int getRating_count() {
        return rating_count;
    }

    public void setRating_count(int rating_count) {
        this.rating_count = rating_count;
    }

    public int getRating_all_count() {
        return rating_all_count;
    }

    public void setRating_all_count(int rating_all_count) {
        this.rating_all_count = rating_all_count;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getTime_wait() {
        return time_wait;
    }

    public void setTime_wait(int time_wait) {
        this.time_wait = time_wait;
    }
}
