package com.zhmt.feibiao.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhmt.feibiao.httpserver.netty.Request;
import com.zhmt.feibiao.httpserver.netty.bean.Modle;
import com.zhmt.feibiao.httpserver.netty.mvc.Controller;
import com.zhmt.feibiao.user.bean.User;
import com.zhmt.feibiao.user.service.UserService;

public class UserController implements Controller {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	public Modle handleRequest(Request request) throws Exception {
		String userId = request.getParameter("userId");
		User user = null;

		JSONObject resJSONObj = new JSONObject();
		try{
			user = userService.getUserInfo(userId);
			resJSONObj.put("code", "200");
			resJSONObj.put("message", "OK");
			resJSONObj.put("user", user);
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