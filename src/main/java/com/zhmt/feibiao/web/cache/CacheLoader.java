package com.zhmt.feibiao.web.cache;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.zhmt.feibiao.user.bean.User;
import com.zhmt.feibiao.user.service.UserService;

/**
 * 数据库中读取数据载入本地缓存，也可自己配置redis缓存等
 * 
 *
 */
public class CacheLoader {
	
	@Autowired
	private UserService userService;

	public void init() {
		loadUserCache();
	}

	private void loadUserCache() {
		List<User> userList = userService.getUserList();
		if (userList != null) {
			Map<String, User> userMap = UserMap.getUserMap();
			for (User user : userList) {
				userMap.put(user.getId().toString(), user);
			}
		}
	}
}
