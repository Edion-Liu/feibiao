package com.zhmt.feibiao.user.dao;


import com.zhmt.feibiao.user.bean.Devices;
import com.zhmt.feibiao.user.bean.DevicesCustom;

import java.util.List;


/**
 * Created by Administrator on 2016/10/5 0005.
 */
public interface DevicesDao {

    public Devices findControllBydevicesID(String devicesid) throws Exception;

    public List<Devices> findDeviceBycontrollerID(String controllerid) throws Exception;

    public Devices findDeviceByID(String devicesid) throws Exception;

    public void updateDeviceByID(Devices devices) throws Exception;

    public void insertNewDevice(Devices devices) throws Exception;

    public List<Devices> findalldevices() throws Exception;

    public List<Devices> findalldevicesbylike(DevicesCustom devicesCustom) throws Exception;

    public List<Devices> finddevicesbylinestatus(String linestatus) throws Exception;
    public List<Devices> finddevicesbylinestatusandsearch(DevicesCustom devicesCustom) throws Exception;




   /* public List<Devices> findUserList();

    public Devices findUserByName(Devices devices);

    public Devices findUserByNameANDpassword(Devices devices);

    public int add(Devices devices);

    public int update(Devices devices);*/
}
