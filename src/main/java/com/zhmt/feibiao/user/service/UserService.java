package com.zhmt.feibiao.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhmt.feibiao.user.bean.User;
import com.zhmt.feibiao.user.dao.UserDao;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	public boolean login(User u) {
		User user = userDao.findUserByName(u);
		if(user != null && user.getId() != null){
			return true;
		}else{
			return false;
		}
	}

	
	public boolean register(User user) {
		User newuser=userDao.findUserByName(user);
		if(newuser !=null && newuser.getId() !=null)
		{//已经注册
			return false;
		}else
		{
			System.out.println("用户注册---用户名："+user.getLoginName()+",密码："+user.getPassword());
			userDao.add(user);
		}

		return true;
	}
	
	public boolean update(User user) {
		userDao.update(user);
		return true;
	}

	public  boolean check(User user)
    {

        User newuser=userDao.findUserByNameANDpassword(user);
        if(newuser !=null && newuser.getId() !=null)
        {//用户名 密码正确
            return true;
        }

        return false;


    }
	
	public User getUserInfo(String userId) {
		return userDao.findUserByID(userId);
	} 
	
	public List<User> getUserList() {
		return userDao.findUserList();
	} 
}
