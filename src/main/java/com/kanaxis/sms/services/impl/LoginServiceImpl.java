package com.kanaxis.sms.services.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.kanaxis.sms.dao.LoginDao;
import com.kanaxis.sms.services.LoginService;
import com.kanaxis.sms.util.ResultData;
import com.kanaxis.sms.util.UserDetails;

public class LoginServiceImpl implements LoginService{
	
	@Autowired
	LoginDao loginDao;

	@Override
	public ResultData login(String userName, String password) throws Exception {
		// TODO Auto-generated method stub
		return loginDao.login(userName,password);
	}

	@Override
	public ResultData addParentCredentials(String class_id, String section_id,
			String student_id, String userName) {
		return loginDao.addParentCredentials(class_id, section_id, student_id, userName);
	}

	@Override
	public ResultData getAllParentsCredentials(String class_id,
			String section_id) {
		return loginDao.getAllParentsCredentials(class_id, section_id);
	}

	@Override
	public ResultData getCredentialDetails(String user_id) {
		return loginDao.getCredentialDetails(user_id);
	}

	@Override
	public ResultData addTeacherCredentials(String teacher_id, String userName) {
		return loginDao.addTeacherCredentials(teacher_id, userName);
	}

	@Override
	public ResultData getAllTeachersCredentials() {
		return loginDao.getAllTeachersCredentials();
	}

	@Override
	public ResultData deactivateUser(String user_id, String status) {
		return loginDao.deactivateUser(user_id, status);
	}

	@Override
	public ResultData changePassword(String userName, String password) {
		return loginDao.changePassword(userName,password);
	}

}
