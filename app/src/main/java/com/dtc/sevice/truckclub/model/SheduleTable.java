package com.dtc.sevice.truckclub.model;

/**
 * Created by May on 10/10/2017.
 */

public class SheduleTable {
    private String startDate;
    private String endDate;
    private int userReserveId;
    private String startLocation;
    private String startProvicne;
    private String destLocation;
    private String destPorvice;
    private String customerName;
    private String customerTel;
    private String goBackOneTurn;
    private int dateCount;

    public SheduleTable() {
        startDate = "";
        endDate = "";
        userReserveId = 0;
        startLocation = "";
        startProvicne = "";
        destLocation = "";
        destPorvice = "";
        customerName = "";
        customerTel = "";
        dateCount = 0;
        goBackOneTurn = "";
    }

    public String getStartDate() {
        return this.startDate;
    }

    public void setStartDate(String startDate) {this.startDate= startDate;}

    public String getEndDate() {
        return this.endDate;
    }

    public void setEndDate(String endDate) {this.endDate = endDate;}

    public int getDateCount() {
        return this.dateCount;
    }

    public void setDateCount(int count) {this.dateCount = count;}

    public void setGobackOrOneTurn(String goBackOneTurn) {this.goBackOneTurn = goBackOneTurn;}

    public String getgoBackOneTurn() {return this.goBackOneTurn;}

    public int getUserReserveId() {return this.userReserveId;}

    public void setUserReserveId(int userReserveId) {
        this.userReserveId = userReserveId;
    }

    public String getStartLocation() {
        return this.startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getStartProvicne() {
        return this.startProvicne;
    }

    public void setStartProvicne(String startProvicne) {
        this.startProvicne = startProvicne;
    }

    public String getDestLocation() {
        return this.destLocation;
    }

    public void setDestLocation(String destLocation) {
        this.destLocation = destLocation;
    }

    public String getDestPorvice() {
        return this.destPorvice;
    }

    public void setDestPorvice(String destPorvice) {
        this.destPorvice = destPorvice;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerTel() {
        return this.customerTel;
    }

    public void setCustomerTel(String customerTel) {
        this.customerTel = customerTel;
    }
}
