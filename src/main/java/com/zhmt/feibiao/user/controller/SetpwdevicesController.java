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

public class SetpwdevicesController implements Controller {

    private static final Logger logger = LoggerFactory.getLogger(SetpwdevicesController.class);


    @Autowired
    private DevicesService devicesService;

    public Modle handleRequest(Request request) throws Exception {
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        String passwordnew = request.getParameter("passwordnew");



        System.out.println("修改设备绑定密码：设备ID："+userName+",密码："+password);


        Devices devices=new Devices();
        devices.setDeviceid(userName);

        JSONObject resJSONObj = new JSONObject();
        try{
            if((userName!=null)&&(password !=null))
            {
                //查询设备是否存在
                resJSONObj.put("code", "200");
                resJSONObj.put("message", "OK");
                if((devices=devicesService.adddevices(devices))!=null)
                {


                        //密码校验
                        if(devices.getDevicepassword().equals(password))
                        {


                            resJSONObj.put("status", "devices_setpw_success");

                            devices.setDevicepassword(passwordnew);

                            devicesService.updevices(devices);

                        }else {
                            //设备密码错误
                            resJSONObj.put("status", "devices_password_error");

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
