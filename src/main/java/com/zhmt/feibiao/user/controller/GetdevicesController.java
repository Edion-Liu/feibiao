package com.zhmt.feibiao.user.controller;







import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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
 * Created by Administrator on 2016/10/11 0011.
 */
public class GetdevicesController implements Controller {

    private static final Logger logger = LoggerFactory.getLogger(GetdevicesController.class);


    @Autowired
    private DevicesService devicesService;



    public Modle handleRequest(Request request) throws Exception {
        String userName = request.getParameter("username");
        System.out.println("获取设备：设备ID："+userName);


        Devices devices=new Devices();
        devices.setControllerid(userName);

        List<Devices>  dev;

        JSONObject resJSONObj = new JSONObject();
        try{
                //查询设备是否存在
                resJSONObj.put("code", "200");
                resJSONObj.put("message", "OK");
                if((dev=devicesService.getdevicesid(devices))!=null)
                    if (dev.size() != 0) {
                        //获取用户设备成功
                        resJSONObj.put("status", "Get_devices_success");
                        //发送设备信息
                        //resJSONObj.put("deviceslist",JSON.toJSONString(dev));

                        resJSONObj.put("deviceslist",dev);

                    }
                else {
                    //设备不存在
                    resJSONObj.put("status", "devices_nonexistent");

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

