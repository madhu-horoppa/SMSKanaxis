package com.kanaxis.sms.services.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.kanaxis.sms.dao.LoginDao;
import com.kanaxis.sms.services.LoginService;
import com.kanaxis.sms.util.UserDetails;

public class LoginServiceImpl implements LoginService{
	
	@Autowired
	LoginDao loginDao;

	@Override
	public UserDetails login(String userName, String password) throws Exception {
		// TODO Auto-generated method stub
		return loginDao.login(userName,password);
	}

}
