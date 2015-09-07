package com.kanaxis.sms.services;

import com.kanaxis.sms.util.UserDetails;


public interface LoginService {
	
	public UserDetails login(String userName, String password) throws Exception;

}
