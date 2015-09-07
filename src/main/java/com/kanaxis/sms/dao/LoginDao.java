package com.kanaxis.sms.dao;

import com.kanaxis.sms.util.UserDetails;


public interface LoginDao {
	
	public UserDetails login(String userName, String password) throws Exception;

}
