package com.zhmt.feibiao.user.controller.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhmt.feibiao.httpserver.netty.Request;
import com.zhmt.feibiao.httpserver.netty.bean.Modle;
import com.zhmt.feibiao.httpserver.netty.mvc.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2016/12/8 0008.
 */
public class new_login implements Controller {



    private static final Logger logger = LoggerFactory.getLogger(new_login.class);


    public Modle handleRequest(Request request) throws Exception {
        String userName = request.getParameter("login_id");
        String password = request.getParameter("password");



        JSONObject resJSONObj = new JSONObject();

        resJSONObj.put("user_id", "1651");
        resJSONObj.put("name", "游行天下");
        resJSONObj.put("access_token","Xpbgs4020");
        resJSONObj.put("icon","http://oss-cn-shenzhen.aliyuncs.com/woyouzhijia/1_14539517987266");
        resJSONObj.put("wowo_count", "1");
        resJSONObj.put("attraction_count", "18");
        resJSONObj.put("stay_count", "0");
        resJSONObj.put("food_count", "0");
        resJSONObj.put("enterainment_count", "3");
        resJSONObj.put("unique_token", "w89846605");
        resJSONObj.put("is_phone_auth", "1");
        resJSONObj.put("license_auth_status", "3");
        resJSONObj.put("status", "OK");



        logger.info("添加设备：{},设备ID:{}",userName,password);


        Modle modle = new Modle();
        modle.setContent(JSON.toJSONString(resJSONObj));
        modle.setContentType(Modle.CONTENT_TYPE_JSON);
        return modle;

    }

}