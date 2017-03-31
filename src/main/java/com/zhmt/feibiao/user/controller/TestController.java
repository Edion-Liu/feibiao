package com.zhmt.feibiao.user.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhmt.feibiao.httpserver.netty.Request;
import com.zhmt.feibiao.httpserver.netty.bean.Modle;
import com.zhmt.feibiao.httpserver.netty.mvc.Controller;
import com.zhmt.feibiao.user.bean.Devices;
import com.zhmt.feibiao.user.bean.User;
import com.zhmt.feibiao.user.service.DevicesService;
import com.zhmt.feibiao.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Created by Administrator on 2016/10/29 0029.
 */
public class TestController implements Controller{

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);


    @Autowired
    private DevicesService devicesService;
    private JSONObject all;
    List<Devices> devlist;
    String limit=null;
    String offset=null;
    String onlinestatus=null;
    String outlinestatus=null;
    String search=null;


    public Modle handleRequest(Request request) throws Exception {

         limit=null;
         offset=null;
         onlinestatus=null;
         outlinestatus=null;
         search=null;
        all=new JSONObject();


            String callback = request.getParameter("callback");
            System.out.println("回调函数名："+callback);


            if(callback.equals("sontable"))
            {
                    devlist=devicesService.getalldevices();
                    all.put("total",devlist.size());
                    all.put("rows",devlist);

            }else {

                    limit=request.getParameter("limit");
                    offset=request.getParameter("offset");
                    onlinestatus=request.getParameter("onlinestatus");
                    outlinestatus=request.getParameter("outlinestatus");
                    search=request.getParameter("search");

                    if(search.equals(""))
                    {
                        search="搜索";
                    }

                    fartherTable();

            }



            Modle modle = new Modle();


            modle.setContent(callback+"("+ JSON.toJSONString(all)+")");
            //  modle.setContent(callback+"("+ JSONArray.toJSONString(jsonlist)+")");
            /* modle.setContent(callback+"("+JSON.toJSONString(resJSONObj)+")");*/
            //modle.setContent(callback+"("+JSON.toJSONString(resJSONObj)+");");
            modle.setContentType(Modle.CONTENT_TYPE_JSON);

            return modle;
    }

    private void fartherTable()
    {


        if((limit!=null)&&(offset!=null)&&(onlinestatus!=null)&&(outlinestatus!=null)&&(search!=null))
        {

        }else {
            return;
        }

          if(onlinestatus.equals("true")&&outlinestatus.equals("true"))
        {
            //获取所有设备
            if(search.equals("搜索"))
            {
                devlist=devicesService.getalldevices();

            }else {

                devlist=devicesService.getalldevicesbylike(search);


            }


        }else if(onlinestatus.equals("true")){
            //获取在线设备
            if(search.equals("搜索"))
            {

                devlist=devicesService.finddevicesbylinestatus("在线");


            }else {
                devlist=devicesService.searchonline("在线",search);

            }


        }else if(outlinestatus.equals("true"))
        {
            //获取掉线设备
            if(search.equals("搜索"))
            {
                devlist=devicesService.finddevicesbylinestatus("掉线");


            }else {
                devlist=devicesService.searchonline("掉线",search);


            }

        }else {
              all.put("total",0);
              devlist=null;
              all.put("rows",devlist);
              return;

          }

        all.put("total",devlist.size());
        all.put("rows",devlist);

    }
}
