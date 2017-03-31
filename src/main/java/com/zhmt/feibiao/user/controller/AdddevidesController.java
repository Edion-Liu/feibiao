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

public class AdddevidesController implements Controller {

    private static final Logger logger = LoggerFactory.getLogger(AdddevidesController.class);


    @Autowired
    private DevicesService devicesService;

    public Modle handleRequest(Request request) throws Exception {
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        String controllerid=request.getParameter("controllerid");
        System.out.println("添加设备：设备ID："+userName+",密码："+password+",控制id:"+controllerid);


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
                    if(devices.getDevicebinding().equals("false"))
                    {//


                            //密码校验
                            if(devices.getDevicepassword().equals(password))
                            {

                                //设备添加成功
                                resJSONObj.put("status", "devices_add_success");

                                //修改数据库绑定
                                //绑定的控制ID
                                //绑定状态
                                devices.setControllerid(controllerid);
                                devices.setDevicebinding("true");
                                devicesService.updevices(devices);

                            }else {
                                //设备密码错误
                                resJSONObj.put("status", "devices_password_error");

                            }



                    }else {

                        if(devices.getDevicebinding2().equals("false")&&(!devices.getControllerid().equals(controllerid)))
                        {//

                            //密码校验
                            if(devices.getDevicepassword().equals(password))
                            {

                                //设备添加成功
                                resJSONObj.put("status", "devices_add_success");

                                //修改数据库绑定
                                //绑定的控制ID
                                //绑定状态
                                devices.setControllerid2(controllerid);
                                devices.setDevicebinding2("true");

                                devicesService.updevices(devices);

                            }else {
                                //设备密码错误
                                resJSONObj.put("status", "devices_password_error");

                            }

                        }else {
                            //设备已经绑定两个控制端
                            resJSONObj.put("status", "devices_binged");
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
