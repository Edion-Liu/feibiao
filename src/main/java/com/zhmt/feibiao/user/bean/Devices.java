package com.zhmt.feibiao.user.bean;

public class Devices {
    private Integer id;

    private String deviceid;

    private String controllerid;

    private String devicepassword;

    private String devicebinding;

    private String onlinestatus;

    private String company;

    private String cardnumber;

    private String activate;

    private String gps;

    private String controllerid2;

    private String devicebinding2;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid == null ? null : deviceid.trim();
    }

    public String getControllerid() {
        return controllerid;
    }

    public void setControllerid(String controllerid) {
        this.controllerid = controllerid == null ? null : controllerid.trim();
    }

    public String getDevicepassword() {
        return devicepassword;
    }

    public void setDevicepassword(String devicepassword) {
        this.devicepassword = devicepassword == null ? null : devicepassword.trim();
    }

    public String getDevicebinding() {
        return devicebinding;
    }

    public void setDevicebinding(String devicebinding) {
        this.devicebinding = devicebinding == null ? null : devicebinding.trim();
    }

    public String getOnlinestatus() {
        return onlinestatus;
    }

    public void setOnlinestatus(String onlinestatus) {
        this.onlinestatus = onlinestatus;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCardnumber() {
        return cardnumber;
    }

    public void setCardnumber(String cardnumber) {
        this.cardnumber = cardnumber;
    }

    public String getActivate() {
        return activate;
    }

    public void setActivate(String activate) {
        this.activate = activate;
    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    public String getControllerid2() {
        return controllerid2;
    }

    public void setControllerid2(String controllerid2) {
        this.controllerid2 = controllerid2;
    }

    public String getDevicebinding2() {
        return devicebinding2;
    }

    public void setDevicebinding2(String devicebinding2) {
        this.devicebinding2 = devicebinding2;
    }
}