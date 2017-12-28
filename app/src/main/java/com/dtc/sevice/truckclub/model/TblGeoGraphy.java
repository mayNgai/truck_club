package com.dtc.sevice.truckclub.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by May on 9/22/2017.
 */

public class TblGeoGraphy implements Serializable {
    private String success;
    private String message;
    @SerializedName("geo_id")
    @Expose
    private String geo_id;
    @SerializedName("geo_name")
    @Expose
    private String geo_name;

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

    public String getGeo_id() {
        return geo_id;
    }

    public void setGeo_id(String geo_id) {
        this.geo_id = geo_id;
    }

    public String getGeo_name() {
        return geo_name;
    }

    public void setGeo_name(String geo_name) {
        this.geo_name = geo_name;
    }
}
