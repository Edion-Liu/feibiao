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

public class LoginController implements Controller {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private UserService userService;

	public Modle handleRequest(Request request) throws Exception {
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		System.out.println("登录：用户名："+userName+",密码："+password);
		User user = new User();
		user.setLoginName(userName);
		user.setPassword(password);
		
		boolean boo = false;
		boolean bm=false;
		JSONObject resJSONObj = new JSONObject();
		try{
			if((userName!=null)&&(password !=null))
			{
                //查询用户是否注册
				boo = userService.login(user);

				resJSONObj.put("code", "200");
				resJSONObj.put("message", "OK");
				resJSONObj.put("loginStatus", boo);
				bm=userService.check(user);
				if(bm)
				{//校验密码用户名成功
					resJSONObj.put("status", "success");

				}else
				{
					resJSONObj.put("status", "usernameOrpassworderror");
				}



			}else{
				resJSONObj.put("status", "error");
			}

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
		// return new
		// Modle().setContent(JSON.toJSONString(resJSONObj)).setContentType(Modle.CONTENT_TYPE_JSON);
	}

}
