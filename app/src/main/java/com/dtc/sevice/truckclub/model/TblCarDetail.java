package com.dtc.sevice.truckclub.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by May on 9/29/2017.
 */
@DatabaseTable(tableName="TblCarDetail")
public class TblCarDetail implements Serializable {
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
    @SerializedName("car_id")
    @Expose
    @DatabaseField( useGetSet = true)
    private int car_id;
    @SerializedName("license_plate")
    @Expose
    @DatabaseField( useGetSet = true)
    private String license_plate;
    @SerializedName("province")
    @Expose
    @DatabaseField( useGetSet = true)
    private String province;
    @SerializedName("car_brand")
    @Expose
    @DatabaseField( useGetSet = true)
    private String car_brand;
    @SerializedName("car_model")
    @Expose
    @DatabaseField( useGetSet = true)
    private String car_model;
    @SerializedName("group_id")
    @Expose
    @DatabaseField( useGetSet = true)
    private int group_id;
    @SerializedName("price_per_trip")
    @Expose
    @DatabaseField( useGetSet = true)
    private String price_per_trip;
    @SerializedName("date_car_last_edit")
    @Expose
    @DatabaseField( useGetSet = true)
    private String date_car_last_edit;
    @SerializedName("date_car_create")
    @Expose
    @DatabaseField( useGetSet = true)
    private String date_car_create;
    @SerializedName("car_tow")
    @Expose
    @DatabaseField( useGetSet = true)
    private int car_tow;
    @SerializedName("car_wheels")
    @Expose
    @DatabaseField( useGetSet = true)
    private int car_wheels;
    @SerializedName("car_tons")
    @Expose
    @DatabaseField( useGetSet = true)
    private int car_tons;
    @SerializedName("option_trailer")
    @Expose
    @DatabaseField( useGetSet = true)
    private int option_trailer;
    @SerializedName("sum_weight")
    @Expose
    @DatabaseField( useGetSet = true)
    private int sum_weight;
    @SerializedName("group_name")
    @Expose
    @DatabaseField( useGetSet = true)
    private String group_name;
    @SerializedName("picture")
    @Expose
    private List<TblPicture> picture = null;

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

    public int getCar_id() {
        return car_id;
    }

    public void setCar_id(int car_id) {
        this.car_id = car_id;
    }

    public String getLicense_plate() {
        return license_plate;
    }

    public void setLicense_plate(String license_plate) {
        this.license_plate = license_plate;
    }

    public String getCar_brand() {
        return car_brand;
    }

    public void setCar_brand(String car_brand) {
        this.car_brand = car_brand;
    }

    public String getCar_model() {
        return car_model;
    }

    public void setCar_model(String car_model) {
        this.car_model = car_model;
    }


    public String getPrice_per_trip() {
        return price_per_trip;
    }

    public void setPrice_per_trip(String price_per_trip) {
        this.price_per_trip = price_per_trip;
    }

    public String getDate_car_last_edit() {
        return date_car_last_edit;
    }

    public void setDate_car_last_edit(String date_car_last_edit) {
        this.date_car_last_edit = date_car_last_edit;
    }

    public String getDate_car_create() {
        return date_car_create;
    }

    public void setDate_car_create(String date_car_create) {
        this.date_car_create = date_car_create;
    }

    public int getCar_tow() {
        return car_tow;
    }

    public void setCar_tow(int car_tow) {
        this.car_tow = car_tow;
    }

    public int getCar_wheels() {
        return car_wheels;
    }

    public void setCar_wheels(int car_wheels) {
        this.car_wheels = car_wheels;
    }

    public int getCar_tons() {
        return car_tons;
    }

    public void setCar_tons(int car_tons) {
        this.car_tons = car_tons;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public int getOption_trailer() {
        return option_trailer;
    }

    public void setOption_trailer(int option_trailer) {
        this.option_trailer = option_trailer;
    }

    public int getSum_weight() {
        return sum_weight;
    }

    public void setSum_weight(int sum_weight) {
        this.sum_weight = sum_weight;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public List<TblPicture> getPicture() {
        return picture;
    }

    public void setPicture(List<TblPicture> picture) {
        this.picture = picture;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }
}
