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

/**
 * Created by Administrator on 2016/10/10 0010.
 */

public class RemovedevidesController implements Controller {

    private static final Logger logger = LoggerFactory.getLogger(RemovedevidesController.class);


    @Autowired
    private DevicesService devicesService;

    public Modle handleRequest(Request request) throws Exception {
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        String devicesid = request.getParameter("devicesid");

        System.out.println("解除设备绑定：设备ID："+devicesid+",密码："+password);


        Devices devices=new Devices();
        devices.setDeviceid(devicesid);

        JSONObject resJSONObj = new JSONObject();
        try{
            if((userName!=null)&&(password !=null))
            {
                //查询设备是否存在
                resJSONObj.put("code", "200");
                resJSONObj.put("message", "OK");
                if((devices=devicesService.adddevices(devices))!=null)
                {
                    if(devices.getDevicebinding().equals("true")&&devices.getControllerid().equals(userName))
                    {
                        //密码校验
                        if(devices.getDevicepassword().equals(password))
                        {

                            //设备添加成功
                            resJSONObj.put("status", "devices_remove_success");

                            //修改数据库绑定
                            //绑定的控制ID
                            //绑定状态
                            devices.setControllerid("00");
                            devices.setDevicebinding("false");
                            devicesService.updevices(devices);

                        }else {
                            //设备密码错误
                            resJSONObj.put("status", "devices_password_error");

                        }






                    }else {


                        if(devices.getDevicebinding2().equals("true")&&devices.getControllerid2().equals(userName))
                        {

                            //密码校验
                            if(devices.getDevicepassword().equals(password))
                            {
                                //设备添加成功
                                resJSONObj.put("status", "devices_remove_success");

                                //修改数据库绑定
                                //绑定的控制ID
                                //绑定状态
                                devices.setControllerid2("00");
                                devices.setDevicebinding2("false");
                                devicesService.updevices(devices);

                            }else {
                                //设备密码错误
                                resJSONObj.put("status", "devices_password_error");

                            }


                        }else {
                            resJSONObj.put("status", "devices_bing");
                        }



                    }

                }else {
                    //设备不存在
                    resJSONObj.put("status", "devices_nonexistent");



                }

            }else{
                resJSONObj.put("status", "error");
            }

        } catch(Exception e){
            resJSONObj.put("code", "500");
            resJSONObj.put("message", "NOK");

            logger.error(e.getMessage(), e);
        }

        Modle modle = new Modle();
        modle.setContent(JSON.toJSONString(resJSONObj));
        modle.setContentType(Modle.CONTENT_TYPE_JSON);
        return modle;

    }

}
