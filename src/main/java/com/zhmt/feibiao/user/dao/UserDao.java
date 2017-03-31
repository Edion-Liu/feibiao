package com.zhmt.feibiao.user.dao;

import java.util.List;

import com.zhmt.feibiao.user.bean.User;

public interface UserDao {
	
	public User findUserByID(String userid);
	
	public List<User> findUserList();
	
	public User findUserByName(User user);

	public User findUserByNameANDpassword(User user);
	
	public int add(User user);
	
	public int update(User user);

}
