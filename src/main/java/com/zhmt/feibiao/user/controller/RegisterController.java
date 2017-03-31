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

public class RegisterController implements Controller {
	
	private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

	@Autowired
	private UserService userService;

	public Modle handleRequest(Request request) throws Exception {
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		
		User user = new User();
		user.setLoginName(userName);
		user.setPassword(password);
		System.out.println("用户注册===："+",账号："+user.getLoginName()+",密码："+user.getPassword());
		boolean boo = false;
		JSONObject resJSONObj = new JSONObject();
		try{
			boo = userService.register(user);
			if((userName!=null)&&(password !=null)) {


				if(boo)
				{
					System.out.println("注册成功："+",账号："+userName+",密码："+password);
					resJSONObj.put("code", "200");
					resJSONObj.put("message", "OK");
					resJSONObj.put("registerStatus", boo);

					resJSONObj.put("status", "success");

				}else {


					resJSONObj.put("code", "200");
					resJSONObj.put("message", "OK");
					resJSONObj.put("registerStatus", boo);
					//已注册,请直接登录
					resJSONObj.put("status", "registered");



				}

			}else {
				resJSONObj.put("status", "error");
			}

		} catch(Exception e){
			resJSONObj.put("code", "500");
			resJSONObj.put("message", "NOK");
			resJSONObj.put("registerStatus", boo);
			logger.error(e.getMessage(), e);
		}
		
		Modle modle = new Modle();
		modle.setContent(JSON.toJSONString(resJSONObj));
		modle.setContentType(Modle.CONTENT_TYPE_JSON);
		return modle;
	}
}