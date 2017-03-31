package com.zhmt.feibiao.user;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;

public class TestController {

	public static void main(String[] args) {
		// http://localhost:9010/user/login?userName=admin&passWord=admin123
		// http://localhost:9010/user/info?userId=1
		HttpClient httpClient = new HttpClient();
		String url = "http://192.168.3.12:9010/user/info";
//		String url = "http://localhost:9010/user/login";
		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestHeader("Connection", "close");
		postMethod.setParameter("userId", "1");
	//	postMethod.setParameter("userName", "admin");
	//	postMethod.setParameter("passWord", "admin123");
		httpClient.getParams().setContentCharset("utf-8");
		try {
			httpClient.executeMethod(postMethod);
			System.out.println(postMethod.getStatusLine());
			System.out.println(postMethod.getResponseBodyAsString());
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			postMethod.releaseConnection();
		}
	}
	
}
