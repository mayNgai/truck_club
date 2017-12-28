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
@DatabaseTable(tableName="TblTask")
public class TblTask implements Serializable {
    @DatabaseField( useGetSet = true)
    private String success;
    @DatabaseField( useGetSet = true)
    private String message;
    @DatabaseField(id = true, useGetSet = true)
    private String guid;

    @SerializedName("id")
    @Expose
    @DatabaseField( useGetSet = true)
    private String id;

    @SerializedName("task_id")
    @Expose
    @DatabaseField( useGetSet = true)
    private String task_id;

    @SerializedName("user_id")
    @Expose
    @DatabaseField( useGetSet = true)
    private int user_id;

    @SerializedName("type_task")
    @Expose
    @DatabaseField( useGetSet = true)
    private String type_task;

    @SerializedName("start_date")
    @Expose
    @DatabaseField( useGetSet = true)
    private String start_date;

    @SerializedName("end_date")
    @Expose
    @DatabaseField( useGetSet = true)
    private String end_date;

    @SerializedName("start_lat")
    @Expose
    @DatabaseField( useGetSet = true)
    private float start_lat;

    @SerializedName("start_lon")
    @Expose
    @DatabaseField( useGetSet = true)
    private float start_lon;

    @SerializedName("start_location")
    @Expose
    @DatabaseField( useGetSet = true)
    private String start_location;

    @SerializedName("start_province")
    @Expose
    @DatabaseField( useGetSet = true)
    private String start_province;

    @SerializedName("date_count")
    @Expose
    @DatabaseField( useGetSet = true)
    private int date_count;

    @SerializedName("dest_location")
    @Expose
    @DatabaseField( useGetSet = true)
    private String dest_location;

    @SerializedName("dest_province")
    @Expose
    @DatabaseField( useGetSet = true)
    private String dest_province;

    @SerializedName("des_lat")
    @Expose
    @DatabaseField( useGetSet = true)
    private float des_lat;

    @SerializedName("des_lon")
    @Expose
    @DatabaseField( useGetSet = true)
    private float des_lon;

    @SerializedName("price")
    @Expose
    @DatabaseField( useGetSet = true)
    private String price;

    @SerializedName("group_id")
    @Expose
    @DatabaseField( useGetSet = true)
    private int group_id;

    @SerializedName("task_status")
    @Expose
    @DatabaseField( useGetSet = true)
    private int task_status;

    @SerializedName("service_type")
    @Expose
    @DatabaseField( useGetSet = true)
    private String service_type;

    @SerializedName("date_task_create")
    @Expose
    @DatabaseField( useGetSet = true)
    private String date_task_create;

    @SerializedName("date_task_last")
    @Expose
    @DatabaseField( useGetSet = true)
    private String date_task_last;

    @SerializedName("type_create")
    @Expose
    @DatabaseField( useGetSet = true)
    private String type_create;

    @SerializedName("identify")
    @Expose
    @DatabaseField( useGetSet = true)
    private String identify;

    @SerializedName("time_wait")
    @Expose
    @DatabaseField( useGetSet = true)
    private int time_wait;

    @SerializedName("last_name")
    @Expose
    @DatabaseField( useGetSet = true)
    private String last_name;

    @SerializedName("first_name")
    @Expose
    @DatabaseField( useGetSet = true)
    private String first_name;

    @SerializedName("name_group")
    @Expose
    @DatabaseField( useGetSet = true)
    private String name_group;

    @SerializedName("driver_id")
    @Expose
    @DatabaseField( useGetSet = true)
    private String driver_id;

    @SerializedName("price_offer")
    @Expose
    @DatabaseField( useGetSet = true)
    private String price_offer;

    @SerializedName("complete_status")
    @Expose
    @DatabaseField( useGetSet = true)
    private String complete_status;

    @SerializedName("rating")
    @Expose
    @DatabaseField( useGetSet = true)
    private float rating;

    @SerializedName("member")
    @Expose
    private List<TblMember> member;


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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getType_task() {
        return type_task;
    }

    public void setType_task(String type_task) {
        this.type_task = type_task;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public float getStart_lat() {
        return start_lat;
    }

    public void setStart_lat(float start_lat) {
        this.start_lat = start_lat;
    }

    public float getStart_lon() {
        return start_lon;
    }

    public void setStart_lon(float start_lon) {
        this.start_lon = start_lon;
    }

    public String getStart_location() {
        return start_location;
    }

    public void setStart_location(String start_location) {
        this.start_location = start_location;
    }

    public String getStart_province() {
        return start_province;
    }

    public void setStart_province(String start_province) {
        this.start_province = start_province;
    }

    public int getDate_count() {
        return date_count;
    }

    public void setDate_count(int date_count) {
        this.date_count = date_count;
    }

    public String getDest_location() {
        return dest_location;
    }

    public void setDest_location(String dest_location) {
        this.dest_location = dest_location;
    }

    public String getDest_province() {
        return dest_province;
    }

    public void setDest_province(String dest_province) {
        this.dest_province = dest_province;
    }

    public float getDes_lat() {
        return des_lat;
    }

    public void setDes_lat(float des_lat) {
        this.des_lat = des_lat;
    }

    public float getDes_lon() {
        return des_lon;
    }

    public void setDes_lon(float des_lon) {
        this.des_lon = des_lon;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public int getTask_status() {
        return task_status;
    }

    public void setTask_status(int task_status) {
        this.task_status = task_status;
    }

    public String getService_type() {
        return service_type;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
    }

    public String getDate_task_create() {
        return date_task_create;
    }

    public void setDate_task_create(String date_task_create) {
        this.date_task_create = date_task_create;
    }

    public String getType_create() {
        return type_create;
    }

    public void setType_create(String type_create) {
        this.type_create = type_create;
    }

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    public int getTime_wait() {
        return time_wait;
    }

    public void setTime_wait(int time_wait) {
        this.time_wait = time_wait;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public List<TblMember> getMember() {
        return member;
    }

    public void setMember(List<TblMember> member) {
        this.member = member;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getName_group() {
        return name_group;
    }

    public void setName_group(String name_group) {
        this.name_group = name_group;
    }

    public String getDate_task_last() {
        return date_task_last;
    }

    public void setDate_task_last(String date_task_last) {
        this.date_task_last = date_task_last;
    }

    public String getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(String driver_id) {
        this.driver_id = driver_id;
    }

    public String getPrice_offer() {
        return price_offer;
    }

    public void setPrice_offer(String price_offer) {
        this.price_offer = price_offer;
    }

    public String getComplete_status() {
        return complete_status;
    }

    public void setComplete_status(String complete_status) {
        this.complete_status = complete_status;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
