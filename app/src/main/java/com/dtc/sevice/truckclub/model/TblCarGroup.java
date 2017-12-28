package com.dtc.sevice.truckclub.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by May on 9/28/2017.
 */
@DatabaseTable(tableName="TblCarGroup")
public class TblCarGroup implements Serializable {
    @DatabaseField( useGetSet = true)
    private String success;
    @DatabaseField( useGetSet = true)
    private String message;
    @DatabaseField(id = true, useGetSet = true)
    private String guid;
    @SerializedName("group_id")
    @Expose
    @DatabaseField( useGetSet = true)
    private String group_id;
    @DatabaseField( useGetSet = true)
    private String name_group;
    @DatabaseField( useGetSet = true)
    private int isSelect;

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

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getName_group() {
        return name_group;
    }

    public void setName_group(String name_group) {
        this.name_group = name_group;
    }

    public int getIsSelect() {
        return isSelect;
    }

    public void setIsSelect(int isSelect) {
        this.isSelect = isSelect;
    }
}
