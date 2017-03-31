package com.zhmt.feibiao.user.service;

import java.util.List;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.zhmt.feibiao.user.bean.Devices;

import com.zhmt.feibiao.user.bean.DevicesCustom;
import com.zhmt.feibiao.user.dao.DevicesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by Administrator on 2016/10/5 0005.
 */

@Service
public class DevicesService {
    @Autowired
    public DevicesDao devicesDao;

    public Devices getcontrollerid(String devicesid)
    {

        Devices dev= null;
        try {
            dev = devicesDao.findControllBydevicesID(devicesid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(dev !=null && dev.getControllerid()!=null)
        {

            return  dev;
        }


        return null;
    }

    //通过控制ID获取设备
/*    public Devices getdevicesid(Devices devices)
    {

        Devices  dev= devicesDao.findDeviceBycontrollerID(devices.getControllerid());
        if(dev !=null && dev.getDeviceid()!=null)
        {


            return  dev;
        }


        return null;
    }*/

    public List<Devices> getdevicesid(Devices devices)
    {


        try {
            List<Devices> dev= devicesDao.findDeviceBycontrollerID(devices.getControllerid());

            if(dev.size()!=0)
            {

                return dev;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }



        return null;
    }

    public Devices adddevices(Devices devices){


        Devices  dev= null;
        try {
            dev = devicesDao.findDeviceByID(devices.getDeviceid());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(dev !=null && dev.getDeviceid()!=null)
        {


            return  dev;
        }

        return  null;

    }

    public boolean updevices(Devices devices)
    {

        try {
            devicesDao.updateDeviceByID(devices);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return  true;

    }

    public boolean insert(Devices devices){
        try {
            devicesDao.insertNewDevice(devices);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public List<Devices> getalldevices()
    {
        try {
            List<Devices> dev= devicesDao.findalldevices();

            if(dev.size()!=0)
            {

                return dev;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }



        return null;

    }

     //在所有在线 掉线设备中模糊查找
    public List<Devices> getalldevicesbylike(String seach)
    {
        try {
            DevicesCustom devicesCustom = new DevicesCustom();
            devicesCustom.setSearch(seach);
            List<Devices> dev= devicesDao.findalldevicesbylike(devicesCustom);

            if(dev.size()!=0)
            {

                return dev;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }



        return null;

    }


    public List<Devices> finddevicesbylinestatus(String statu)
    {
        try {
            List<Devices> dev= devicesDao.finddevicesbylinestatus(statu);

            if(dev.size()!=0)
            {

                return dev;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }



        return null;

    }

    public List<Devices> searchonline(String statu,String search)
    {


        try {
            DevicesCustom devicesCustom =new DevicesCustom();
            devicesCustom.setSearch(search);
            devicesCustom.setOnlinestatus(statu);
            List<Devices> dev= devicesDao.finddevicesbylinestatusandsearch(devicesCustom);

            if(dev.size()!=0)
            {

                return dev;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }



        return null;

    }





}
