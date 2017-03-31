package com.zhmt.feibiao.user.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhmt.feibiao.httpserver.netty.Request;
import com.zhmt.feibiao.httpserver.netty.bean.Modle;
import com.zhmt.feibiao.httpserver.netty.mvc.Controller;
import com.zhmt.feibiao.user.bean.Devices;
import com.zhmt.feibiao.user.service.DevicesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;



/**
 * Created by Administrator on 2016/10/29 0029.
 */
public class Resetdevices implements Controller {

    private static final Logger logger = LoggerFactory.getLogger(Resetdevices.class);
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



    @Autowired
    private DevicesService devicesService;
    private JSONObject all;
    List<Devices> devlist;

    public Modle handleRequest(Request request) throws Exception {

        all=new JSONObject();
        String callback = request.getParameter("callback");
        System.out.println("回调函数名："+callback);


        if(callback.equals("updateDevices"))
        {//更新设备信息
                deviceid=request.getParameter("devicesid");

                controllerid=request.getParameter("controllerid");
                devicepassword=request.getParameter("devicepassword");
                devicebinding=request.getParameter("devicebinding");
                onlinestatus=request.getParameter("onlinestatus");
                company=request.getParameter("company");
                cardnumber=request.getParameter("cardnumber");
                activate=request.getParameter("activate");

            gps=request.getParameter("gps");
            controllerid2=request.getParameter("controllerid2");
            devicebinding2=request.getParameter("devicebinding2");

                Devices devices=new Devices();
                devices.setDeviceid(deviceid);
                devices.setControllerid(controllerid);
                devices.setDevicepassword(devicepassword);
                devices.setDevicebinding(devicebinding);
                devices.setOnlinestatus(onlinestatus);
                devices.setCompany(company);
                devices.setCardnumber(cardnumber);
                devices.setActivate(activate);
            devices.setGps(gps);
            devices.setControllerid2(controllerid2);
            devices.setDevicebinding2(devicebinding2);

             Devices dev= devicesService.getcontrollerid(deviceid);
            boolean  status=false;
               if(dev !=null)
               {
                   status=devicesService.updevices(devices);

               }else {
                   status=devicesService.insert(devices);

               }

            if(status)
            {
                all.put("updataup","upsucceed");
            }else {
                all.put("updataup","uperror");
            }






        }else {

            devlist=devicesService.getalldevices();
            System.out.println("设备更新，设备数量："+devlist.size());
            all.put("total",devlist.size());
            all.put("rows",devlist);

        }

        Modle modle = new Modle();
        modle.setContent(callback+"("+ JSON.toJSONString(all)+")");
        modle.setContentType(Modle.CONTENT_TYPE_JSON);

        return modle;
    }


}

