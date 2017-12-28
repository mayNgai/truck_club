package com.dtc.sevice.truckclub.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by may on 9/25/2017.
 */

@DatabaseTable(tableName="TblPicture")
public class TblPicture implements Serializable {

    @DatabaseField(useGetSet = true)
    private String success;

    @DatabaseField(useGetSet = true)
    private String message;

    @DatabaseField(id = true, useGetSet = true)
    private String guid;

    @SerializedName("picture_id")
    @Expose
    @DatabaseField(useGetSet = true)
    private String id;

    @SerializedName("name_path")
    @Expose
    @DatabaseField(useGetSet = true)
    private String name;

    @SerializedName("path")
    @Expose
    @DatabaseField(useGetSet = true)
    private String path;

    @SerializedName("detail")
    @Expose
    @DatabaseField(useGetSet = true)
    private String detail;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

}
