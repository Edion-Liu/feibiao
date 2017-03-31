package com.zhmt.feibiao.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhmt.feibiao.httpserver.netty.Request;
import com.zhmt.feibiao.httpserver.netty.bean.Modle;
import com.zhmt.feibiao.httpserver.netty.mvc.Controller;
import com.zhmt.feibiao.user.service.UserService;

public class ThirdPartyLoginController implements Controller {
	
	private static final Logger logger = LoggerFactory.getLogger(ThirdPartyLoginController.class);

	@Autowired
	private UserService userService;

	public Modle handleRequest(Request request) throws Exception {
		String openId = request.getParameter("openId");
		
		boolean boo = false;
		JSONObject resJSONObj = new JSONObject();
		try{
		//	boo = userService.thirdPartyLogin(openId);

			resJSONObj.put("code", "200");
			resJSONObj.put("message", "OK");
			resJSONObj.put("loginStatus", boo);
		} catch(Exception e){
			resJSONObj.put("code", "500");
			resJSONObj.put("message", "NOK");
			resJSONObj.put("loginStatus", boo);
			logger.error(e.getMessage(), e);
		}
		
		Modle modle = new Modle();
		modle.setContent(JSON.toJSONString(resJSONObj));
		modle.setContentType(Modle.CONTENT_TYPE_JSON);
		return modle;
	}
}