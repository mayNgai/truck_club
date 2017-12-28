package com.dtc.sevice.truckclub.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by May on 9/22/2017.
 */

public class TblProvince implements Serializable {
    private String success;
    private String message;
    @SerializedName("province_id")
    @Expose
    private String province_id;
    @SerializedName("province_code")
    @Expose
    private String province_code;
    @SerializedName("province_name")
    @Expose
    private String province_name;
    @SerializedName("province_name_eng")
    @Expose
    private String province_name_eng;
    @SerializedName("geo_id")
    @Expose
    private String geo_id;

    private boolean selected;

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

    public String getProvince_id() {
        return province_id;
    }

    public void setProvince_id(String province_id) {
        this.province_id = province_id;
    }

    public String getProvince_code() {
        return province_code;
    }

    public void setProvince_code(String province_code) {
        this.province_code = province_code;
    }

    public String getProvince_name() {
        return province_name;
    }

    public void setProvince_name(String province_name) {
        this.province_name = province_name;
    }

    public String getProvince_name_eng() {
        return province_name_eng;
    }

    public void setProvince_name_eng(String province_name_eng) {
        this.province_name_eng = province_name_eng;
    }

    public String getGeo_id() {
        return geo_id;
    }

    public void setGeo_id(String geo_id) {
        this.geo_id = geo_id;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
