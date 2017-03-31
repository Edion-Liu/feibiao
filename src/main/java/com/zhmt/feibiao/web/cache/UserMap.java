package com.zhmt.feibiao.web.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.zhmt.feibiao.user.bean.User;

public class UserMap {
	
	private static Map<String, User> userMap = new ConcurrentHashMap<String, User>();

	public static Map<String, User> getUserMap() {
		return userMap;
	}

	public static void setUserMap(Map<String, User> userMap) {
		UserMap.userMap = userMap;
	}

}
